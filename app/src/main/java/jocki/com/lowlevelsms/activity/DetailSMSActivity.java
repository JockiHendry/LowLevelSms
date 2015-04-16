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

package jocki.com.lowlevelsms.activity;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
import java.util.ArrayList;
import java.util.List;
import jocki.com.lowlevelsms.db.SmsDao;
import jocki.com.lowlevelsms.domain.SmsInfo;

public class DetailSMSActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (savedInstanceState == null && bundle != null) {
            new ReadDetailSmsTask().execute(bundle.getLong("sms_id"));
        }
    }

    private class ReadDetailSmsTask extends AsyncTask<Long, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Long... params) {
            SmsInfo smsInfo = new SmsDao(DetailSMSActivity.this).findSmsById(params[0]);
            Object[] pdus = smsInfo.getPdus();
            List<String> lines = new ArrayList<>();
            lines.add("PDU count: " + pdus.length);
            for (Object pdu : pdus) {
                StringBuilder hexPdu = new StringBuilder();
                for (byte b : (byte[]) pdu) {
                    hexPdu.append(String.format("%02x ", b));
                }
                lines.add(hexPdu.toString());
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                lines.add("Message Body: " + smsMessage.getDisplayMessageBody());
                lines.add("Message Class: " + smsMessage.getMessageClass());
                lines.add("Originating Addr: " + smsMessage.getDisplayOriginatingAddress());
                if (smsMessage.isEmail()) {
                    lines.add("Email Body: " + smsMessage.getEmailBody());
                    lines.add("Email From: " + smsMessage.getEmailFrom());
                }
                lines.add("Pseudo Subject: " + smsMessage.getPseudoSubject());
                lines.add("Service Center Address: " + smsMessage.getServiceCenterAddress());
                lines.add("Status: " + smsMessage.getStatus());
                lines.add("Index On ICC: " + smsMessage.getIndexOnIcc());
                lines.add("Status On ICC: " + smsMessage.getStatusOnIcc());
                if (smsMessage.isReplyPathPresent()) {
                    lines.add("TP-Reply-Path is set.");
                }
                if (smsMessage.isStatusReportMessage()) {
                    lines.add("This is SMS-STATUS-REPORT message");
                }
            }
            return lines;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            setListAdapter(new SimpleListAdapter(result));
        }

    }

}
