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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.List;
import jocki.com.lowlevelsms.db.SmsDao;
import jocki.com.lowlevelsms.domain.SmsInfo;

public class ReadSmsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ReadSmsTask().execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SmsInfo smsInfo = (SmsInfo) getListAdapter().getItem(position);
        Intent intent = new Intent(this, DetailSMSActivity.class);
        intent.putExtra("sms_id", smsInfo.getId());
        startActivity(intent);
    }

    private class ReadSmsTask extends AsyncTask<Void, Void, List<SmsInfo>> {

        @Override
        protected List<SmsInfo> doInBackground(Void... params) {
            SmsDao dao = new SmsDao(ReadSmsActivity.this);
            return dao.findAllSms();
        }

        @Override
        protected void onPostExecute(List<SmsInfo> result) {
            setListAdapter(new SimpleListAdapter(result));
        }

    }

}
