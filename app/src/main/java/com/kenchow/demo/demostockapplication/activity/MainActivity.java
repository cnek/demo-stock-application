package com.kenchow.demo.demostockapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kenchow.demo.demostockapplication.R;
import com.kenchow.demo.demostockapplication.ui.chart.ChartFragment;
import com.kenchow.demo.demostockapplication.ui.stock.StockFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvSubTitle)
    TextView tvSubTitle;

    Fragment mTempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        goFragment(StockFragment.getInstance(),"Portfolio","");
    }

    public void goFragment(Fragment fragment, @NonNull String title,@NonNull  String subTitle){

        tvTitle.setVisibility(!TextUtils.isEmpty(title)? View.VISIBLE:View.GONE);
        tvTitle.setText(title);
        tvSubTitle.setVisibility(!TextUtils.isEmpty(subTitle)? View.VISIBLE:View.GONE);
        tvSubTitle.setText(subTitle);

        switchFragment(fragment);
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != mTempFragment) {
            if (!fragment.isAdded()) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(mTempFragment!=null)
                    ft.hide(mTempFragment);
                ft.add(R.id.fragment_container, fragment).commit();
                if(fragment instanceof StockFragment)
                    ft.addToBackStack(fragment.getClass().getName());
            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(mTempFragment).show(fragment).commit();
            }
            mTempFragment = fragment;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        int backStackEntryCount = manager.getBackStackEntryCount();
        if (mTempFragment instanceof ChartFragment) {
            Fragment fragment = manager.getFragments().get(backStackEntryCount - 1);
            goFragment(fragment,"Portfolio","");
        } else {
            finish();
        }
    }





}
