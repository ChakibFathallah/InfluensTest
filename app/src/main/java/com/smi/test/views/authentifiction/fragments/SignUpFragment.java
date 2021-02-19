package com.smi.test.views.authentifiction.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smi.test.R;
import com.smi.test.utilities.Utilities;
import com.smi.test.views.authentifiction.AuthentificationActivity;
import com.smi.test.views.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpFragment extends Fragment {

    private static final String TAG = SignUpFragment.class.getName();

/*    @BindView(R.id.name_et)
    EditText nameET;

    @BindView(R.id.prenom_et)
    EditText prenomET;*/

    @BindView(R.id.email_et)
    EditText emailET;

    @BindView(R.id.pwd_et)
    EditText pwdEt;

    @BindView(R.id.confirm_pwd_et)
    EditText confirmPwdEt;

    @BindView(R.id.hole_container)
    LinearLayout holeContainer;

    private FragmentActivity mContext;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    public SignUpFragment() {
        // Required empty public constructor
    }


    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        holeContainer.setOnClickListener(v -> Utilities.hideSoftKeyboard(mContext, view));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @OnClick({R.id.signup_btn, R.id.back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signup_btn:
                Log.d(TAG, "onClick: attempting to register.");
                validForm();
                break;
            case R.id.back_btn:
                mContext.getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private void validForm() {
        String email = emailET.getText().toString().trim();
        String password = pwdEt.getText().toString();
        String confirmPassword = confirmPwdEt.getText().toString();

        //check for null valued EditText fields
        if (!isEmpty(email) && !isEmpty(password) && !isEmpty(confirmPassword)) {
            //check if user has a correct email address
            if (isEmailValid(email)) {
                if (password.length() >= 6) {
                    //check if passwords match
                    if (doStringsMatch(password, confirmPassword)) {
                        //Initiate registration task
                        createAccount(email, password);
                    } else {
                        Toast.makeText(getActivity(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Le mot de passe doit comporter au moins 6 caractères", Toast.LENGTH_SHORT).show();
                }
            } else {
                emailET.setError("Veuillez  inscrire avec un e-mail correct");
            }
        } else {
            Toast.makeText(getActivity(), "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }

    }

    private void createAccount(final String email, String password) {

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: AuthState: " + mAuth.getCurrentUser().getUid());
                            sendVerificationEmail();
                            mAuth.signOut();
                            ((AuthentificationActivity) mContext).replaceFragment(new SignInFragment());
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Inscription a été échoué", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "E-mail de vérification envoyé", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "E-mail de vérification échoué", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean doStringsMatch(String s1, String s2) {
        return s1.equals(s2);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }


}