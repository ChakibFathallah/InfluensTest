package com.smi.test.views.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.smi.test.models.Dashboard;
import com.smi.test.views.authentifiction.AuthentificationActivity;
import com.smi.test.views.authentifiction.fragments.SignInFragment;
import com.smi.test.views.base.BaseActivity;
import com.smi.test.views.home.fragments.DashboardFragment;
import com.smi.test.R;
import com.smi.test.views.home.fragments.NewBrandsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getName();

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    private List<Fragment> fragmentlist;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();


        addFragment(new DashboardFragment(), DashboardFragment.class.getName());
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    switchFragment(new DashboardFragment(), Dashboard.class.getName());
                    return true;
                case R.id.nav_search:
                    switchFragment(new NewBrandsFragment(), NewBrandsFragment.class.getName());
                    return true;
                case R.id.nav_sign_out:
                    switchFragment(new SignInFragment(), SignInFragment.class.getName());
                    signOut();
                    return true;
            }
            return true;
        }
    };

    public void handleBackStackChanges() {
        try {
            fragmentlist = new ArrayList();
            fragmentlist.add(new DashboardFragment());
            fragmentlist.add(new NewBrandsFragment());
            fragmentlist.add(new SignInFragment());

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
            if (currentFragment != null) {
                if (fragmentlist != null) {
                    if (currentFragment.getClass().toString().equals(fragmentlist.get(0).getClass().toString())) {
                        toggleNavState(0);
                    } else if (currentFragment.getClass().toString().equals(fragmentlist.get(1).getClass().toString())) {
                        toggleNavState(1);
                    } else if (currentFragment.getClass().toString().equals(fragmentlist.get(2).getClass().toString())) {
                        toggleNavState(2);
                    } else {
                        toggleNavState(-1);
                    }
                }

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void toggleNavState(int index) {
        try {
            if (index != -1) {
                bottomNavigation.getMenu().setGroupCheckable(0, true, true);
                if (index < bottomNavigation.getMenu().size()) {
                    bottomNavigation.getMenu().getItem(index).setChecked(true);
                }
            } else {
                bottomNavigation.getMenu().setGroupCheckable(0, false, true);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(this, AuthentificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}