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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmsOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Sms";

    SmsOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SmsEntry.TABLE_NAME + " (" +
            SmsEntry._ID + " INTEGER PRIMARY KEY, " +
            SmsEntry.FORMAT + " format STRING, " +
            SmsEntry.SUBSCRIPTION + " subscription INTEGER, " +
            SmsEntry.SLOT + " slot INTEGER, " +
            SmsEntry.PHONE + " phone INTEGER, " +
            SmsEntry.ERROR_CODE + " errorCode INTEGER " +
            ")"
        );

        db.execSQL("CREATE TABLE " + SmsPduEntry.TABLE_NAME + " (" +
            SmsPduEntry._ID + " INTEGER PRIMARY KEY, " +
            SmsPduEntry.PDU + " BLOB," +
            SmsPduEntry.SMS_ID + " INTEGER," +
            "FOREIGN KEY (" + SmsPduEntry.SMS_ID + ") REFERENCES " + SmsEntry.TABLE_NAME + "(" + SmsEntry._ID + ")" +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
