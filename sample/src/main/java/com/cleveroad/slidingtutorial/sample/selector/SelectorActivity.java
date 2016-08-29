package com.cleveroad.slidingtutorial.sample.selector;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cleveroad.slidingtutorial.sample.R;

public class SelectorActivity extends ListActivity implements AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                com.cleveroad.slidingtutorial.sample.support.MainActivity.start(this);
                break;
            case 1:
                com.cleveroad.slidingtutorial.sample.MainActivity.start(this);
                break;
        }
    }
}
