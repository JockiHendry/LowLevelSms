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

package jocki.com.lowlevelsms.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import jocki.com.lowlevelsms.R;
import jocki.com.lowlevelsms.db.SmsDao;
import jocki.com.lowlevelsms.domain.SmsInfo;

public class SmsReceiver extends BroadcastReceiver {

    public final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        final Context myContext = context;
        if (bundle != null) {
            // Save SMS information to database
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SmsInfo smsInfo = new SmsInfo();
                    smsInfo.setPdus((Object[]) bundle.get("pdus"));
                    smsInfo.setFormat(bundle.getString("format"));
                    if (bundle.containsKey("subscription")) {
                        smsInfo.setSubscription(bundle.getLong("subscription"));
                    }
                    if (bundle.containsKey("slot")) {
                        smsInfo.setSlot(bundle.getInt("slot"));
                    }
                    if (bundle.containsKey("phone")) {
                        smsInfo.setPhone(bundle.getInt("phone"));
                    }
                    if (bundle.containsKey("errorCode")) {
                        smsInfo.setErrorCode(bundle.getInt("errorCode"));
                    }
                    SmsDao dao = new SmsDao(myContext);
                    dao.addSms(smsInfo);
                }
            }).start();

            // Add new notification
            Notification.Builder builder = new Notification.Builder(myContext)
                .setSmallIcon(R.drawable.message)
                .setContentTitle("Pesan SMS Baru")
                .setContentText("Anda menerima pesan SMS baru");
            NotificationManager notificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

}
