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

import android.database.Cursor;
import android.provider.BaseColumns;
import jocki.com.lowlevelsms.domain.SmsInfo;

public class SmsEntry implements BaseColumns {
    public static final String TABLE_NAME = "sms";
    public static final String FORMAT = "format";
    public static final String SUBSCRIPTION = "subscription";
    public static final String SLOT = "slot";
    public static final String PHONE = "phone";
    public static final String ERROR_CODE = "errorCode";

    public static SmsInfo fromCursor(Cursor c) {
        SmsInfo smsInfo = new SmsInfo();
        smsInfo.setId(c.getLong(c.getColumnIndexOrThrow(SmsEntry._ID)));
        smsInfo.setFormat(c.getString(c.getColumnIndexOrThrow(SmsEntry.FORMAT)));
        if (!c.isNull(c.getColumnIndexOrThrow(SmsEntry.SUBSCRIPTION))) {
            smsInfo.setSubscription(c.getLong(c.getColumnIndexOrThrow(SmsEntry.SUBSCRIPTION)));
        }
        if (!c.isNull(c.getColumnIndexOrThrow(SmsEntry.SLOT))) {
            smsInfo.setSlot(c.getInt(c.getColumnIndexOrThrow(SmsEntry.SLOT)));
        }
        if (!c.isNull(c.getColumnIndexOrThrow(SmsEntry.PHONE))) {
            smsInfo.setPhone(c.getInt(c.getColumnIndexOrThrow(SmsEntry.PHONE)));
        }
        if (!c.isNull(c.getColumnIndexOrThrow(SmsEntry.ERROR_CODE))) {
            smsInfo.setErrorCode(c.getInt(c.getColumnIndexOrThrow(SmsEntry.ERROR_CODE)));
        }
        return smsInfo;
    }

}
