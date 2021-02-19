package com.smi.test.views.authentifiction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.smi.test.R;
import com.smi.test.utilities.Utilities;
import com.smi.test.views.authentifiction.fragments.SignInFragment;
import com.smi.test.views.base.BaseActivity;

import butterknife.ButterKnife;


public class AuthentificationActivity extends BaseActivity {

    private static final String TAG = AuthentificationActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        ButterKnife.bind(this);
        addFragment(new SignInFragment(), SignInFragment.class.getName());

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }


}