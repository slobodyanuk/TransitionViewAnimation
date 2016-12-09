package com.skysoft.slobodyanuk.transitionviewanimation.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.skysoft.slobodyanuk.transitionviewanimation.R;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.phone.PhoneSurfaceView;
import com.thefinestartist.utils.ui.DisplayUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallActivity extends AppCompatActivity {

    @BindView(R.id.phone_surface)
    PhoneSurfaceView mPhoneSurfaceView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.activity_call)
    RelativeLayout mRoot;

    @BindView(R.id.background)
    ImageView mImageBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_call);
        ButterKnife.bind(this);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mToolbar.getLayoutParams();
        params.topMargin = DisplayUtil.getStatusBarHeight();
        mToolbar.setLayoutParams(params);

        Glide.with(this)
                .load(R.drawable.ic_call_background)
                .asBitmap().centerCrop().into(mImageBackground);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

}