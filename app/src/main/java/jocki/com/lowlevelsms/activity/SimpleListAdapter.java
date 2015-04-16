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

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class SimpleListAdapter extends BaseAdapter {

    private List list;

    public SimpleListAdapter(List list) {
        this.list = list;
    }

    public List getList() {
        return list;
    }

    public String getDisplayText(int position) {
        return String.valueOf(getList().get(position));
    }

    @Override
    public int getCount() {
        return getList().size();
    }

    @Override
    public Object getItem(int position) {
        return getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if ((convertView != null) && (convertView instanceof TextView)) {
            textView = (TextView) convertView;
        } else {
            textView = new TextView(parent.getContext());
        }
        textView.setText(getDisplayText(position));
        textView.setTextAppearance(parent.getContext(), android.R.style.TextAppearance_DeviceDefault_Medium);
        textView.setPadding(10,5,5,5);
        return textView;
    }

}
