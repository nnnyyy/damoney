package com.dacom.damoney;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.dacom.damoney.Functional.BottomNavigationViewHelper;
import com.dacom.damoney.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding mBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupStatusBar();
        setBotNavView();
    }

    private void setupStatusBar() {
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorMainFont));
    }

    private void setBotNavView() {
        BottomNavigationViewHelper.disableShiftMode(mBind.bnv);
        mBind.bnv.setOnNavigationItemSelectedListener(this);
        changeFragment(R.id.act_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return changeFragment(item.getItemId());
    }

    private boolean changeFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = null;
        switch(id) {
            case R.id.act_home:
                f = new BMHomeFragment();
                break;
            case R.id.act_earn:
                f = new BMEarnFragment();
                break;
            case R.id.act_cash:
                f = new BMCashFragment();
                break;
            case R.id.act_comm:
                f = new BMCommunityFragment();
                break;
            case R.id.act_profile:
                f = new BMProfileFragment();
                break;
            default:
                return false;
        }
        if(f == null) return false;
        fm.beginTransaction().replace(R.id.content, f).commit();
        return true;
    }
}
