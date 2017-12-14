package com.dacom.damoney;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dacom.damoney.Functional.BottomNavigationViewHelper;
import com.dacom.damoney.Push.DamoneyPushManager;
import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.ActivityMainBinding;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding mBind;
    Fragment curFragment;
    boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupStatusBar();
        setBotNavView();
        MyPassport.getInstance().init(this);

        boolean bAds = getIntent().getBooleanExtra("Ads", false);

        if(!bAds && DamoneyPushManager.bEnableAdsPUsh) {
            DamoneyPushManager.Reserv(getApplicationContext(), DamoneyPushManager.FIVE_MIN);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void setupStatusBar() {
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (android.os.Build.VERSION.SDK_INT > 19)
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHomeNavBack));
        else {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintColor(R.color.colorMainFont);
        }
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

    public void changeNav(int id) {
        mBind.bnv.setSelectedItemId(id);
    }

    public boolean changeFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = null;
        switch(id) {
            case R.id.act_home:
                f = new BMHomeFragment();
                boolean bAds = getIntent().getBooleanExtra("Ads", false);
                getIntent().removeExtra("Ads");
                if(bAds) {
                    ((BMHomeFragment)f).setViewAdsByNoti();
                }
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
            case R.id.btn_go_premium_map:
                f = new BMPremiumMapFragment();
                break;
            case R.id.btn_ads_distribute:
                f = new BMAdsDistFragment();
                break;
            case R.id.act_recommfriend:
                f = new BMRecommandFriend();
                break;
            case R.id.btn_coupon:
                f = new BMCouponFragment();
                break;
            case R.id.bonus_item:
                f = new BMBonusMainFragment();
                break;
            default:
                return false;
        }
        if(f == null) return false;
        curFragment = f;
        fm.beginTransaction().replace(R.id.content, f).commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(curFragment instanceof BMHomeFragment) {
            if (exit) {
                CharacterAnimator.getInstance().clear();
                finish(); // finish activity
            } else {
                Toast.makeText(this, "한번 더 누르면 종료 됩니다",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
        else if(curFragment instanceof FragmentEx) {
            FragmentEx f = (FragmentEx)curFragment;
            f.onBack(this);
        }
    }
}
