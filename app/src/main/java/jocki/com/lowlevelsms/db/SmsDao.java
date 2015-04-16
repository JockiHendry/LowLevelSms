/*
 * Copyright 2015 Jocki Hendry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jocki.com.lowlevelsms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import jocki.com.lowlevelsms.domain.SmsInfo;

public class SmsDao {

    private SmsOpenHelper helper;

    public SmsDao(Context context) {
        this(new SmsOpenHelper(context));
    }

    public SmsDao(SmsOpenHelper smsOpenHelper) {
        helper = smsOpenHelper;
    }

    public long addSms(SmsInfo smsInfo) {
        long newId;
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            db.beginTransaction();
            try {
                // Insert to sms table
                ContentValues smsValue = new ContentValues();
                smsValue.put(SmsEntry.FORMAT, smsInfo.getFormat());
                if (smsInfo.getSubscription() != null) {
                    smsValue.put(SmsEntry.SUBSCRIPTION, smsInfo.getSubscription());
                }
                if (smsInfo.getSlot() != null) {
                    smsValue.put(SmsEntry.SLOT, smsInfo.getSlot());
                }
                if (smsInfo.getPhone() != null) {
                    smsValue.put(SmsEntry.PHONE, smsInfo.getPhone());
                }
                if (smsInfo.getErrorCode() != null) {
                    smsValue.put(SmsEntry.ERROR_CODE, smsInfo.getErrorCode());
                }
                newId = db.insert(SmsEntry.TABLE_NAME, null, smsValue);

                // Insert to pdu table
                for (int i=0; i<smsInfo.getPdus().length; i++) {
                    byte[] pdu = (byte[]) smsInfo.getPdus()[i];
                    ContentValues pduValue = new ContentValues();
                    pduValue.put(SmsPduEntry.PDU, pdu);
                    pduValue.put(SmsPduEntry.SMS_ID, newId);
                    db.insert(SmsPduEntry.TABLE_NAME, null, pduValue);
                }

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        return newId;
    }

    public List<SmsInfo> findAllSms() {
        List<SmsInfo> result = new ArrayList<>();
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            String[] projection = { SmsEntry._ID, SmsEntry.FORMAT, SmsEntry.SUBSCRIPTION, SmsEntry.SLOT, SmsEntry.PHONE, SmsEntry.ERROR_CODE };
            String sortOrder = SmsEntry._ID + " DESC";
            try (Cursor c = db.query(SmsEntry.TABLE_NAME, projection, null, null, null, null, sortOrder)) {
                while (c.moveToNext()) {
                    result.add(SmsEntry.fromCursor(c));
                }
            }
        }
        return result;
    }

    public SmsInfo findSmsById(long smsId) {
        SmsInfo result = null;
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            String[] projection = { SmsEntry._ID, SmsEntry.FORMAT, SmsEntry.SUBSCRIPTION, SmsEntry.SLOT, SmsEntry.PHONE, SmsEntry.ERROR_CODE };
            String[] whereArgs = { String.valueOf(smsId) };
            try (Cursor c = db.query(SmsEntry.TABLE_NAME, projection, SmsEntry._ID + " = ?", whereArgs, null, null, null)) {
                if (c.moveToNext()) {
                    result = SmsEntry.fromCursor(c);
                    result.setPdus(findPdusBySms(smsId).toArray());
                }
            }
        }
        return result;
    }

    public List<byte[]> findPdusBySms(long smsId) {
        List<byte[]> result = new ArrayList<>();
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            String[] projection = { SmsPduEntry.PDU };
            String[] whereArgs = { String.valueOf(smsId) };
            try (Cursor c = db.query(SmsPduEntry.TABLE_NAME, projection, SmsPduEntry.SMS_ID + " = ?", whereArgs, null, null, null)) {
                while (c.moveToNext()) {
                    result.add(c.getBlob(c.getColumnIndexOrThrow(SmsPduEntry.PDU)));
                }
            }
        }
        return result;
    }

}
