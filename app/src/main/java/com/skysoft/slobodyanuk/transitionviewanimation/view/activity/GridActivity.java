package com.skysoft.slobodyanuk.transitionviewanimation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.skysoft.slobodyanuk.transitionviewanimation.view.fragment.GridFragment;
import com.skysoft.slobodyanuk.transitionviewanimation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GridActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.menu_list)
    RelativeLayout mListMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle(R.string.app_name);
        mListMenu.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, GridFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @OnClick(R.id.menu_list)
    public void onMenuList(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
