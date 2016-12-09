package com.skysoft.slobodyanuk.transitionviewanimation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skysoft.slobodyanuk.transitionviewanimation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.activity_home)
    ListView mListView;

    private String[] items = new String[]{"Grid/List animations", "Calling animations"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);
    }

    @OnItemClick(R.id.activity_home)
    public void onItemClick(int pos) {
        switch (pos) {
            case 0:
                startActivity(new Intent(this, GridActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, CallActivity.class));
                break;
        }
    }

}
