package com.smi.test.views.authentifiction.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smi.test.R;
import com.smi.test.utilities.Utilities;
import com.smi.test.views.authentifiction.AuthentificationActivity;
import com.smi.test.views.base.BaseActivity;
import com.smi.test.views.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignInFragment extends Fragment {

    private static final String TAG = SignInFragment.class.getName();
    private FragmentActivity mContext;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @BindView(R.id.signup_tv)
    TextView singnUp;

    @BindView(R.id.login_et)
    EditText loginEt;

    @BindView(R.id.pwd_et)
    EditText pwdEt;

    @BindView(R.id.hole_container)
    LinearLayout holeContainer;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
            reload();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);
        holeContainer.setOnClickListener(v -> Utilities.hideSoftKeyboard(mContext, getView()));

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

    @OnClick({R.id.signin_btn, R.id.signup_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signin_btn:
                String email = loginEt.getText().toString().trim();
                String password = pwdEt.getText().toString();
                if (!isEmpty(email) && !isEmpty(password)) {
                    if (isEmailValid(email)) {
                        Log.d(TAG, "onClick: attempting to authenticate.");
                        BaseActivity.showProgressDialog(mContext);
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(mContext, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        BaseActivity.hideProgressDialog();
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            if (user.isEmailVerified()) {
                                                Intent intent = new Intent(mContext, HomeActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                mContext.finish();
                                            } else {
                                                Toast.makeText(mContext, "L'email n'est pas verifié, Vérifiez votre boîte de réception", Toast.LENGTH_SHORT).show();
                                                mAuth.signOut();
                                            }
                                        } else {
                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            Toast.makeText(mContext, "Login a été échoué", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Authentication a échoué", Toast.LENGTH_SHORT).show();
                                BaseActivity.hideProgressDialog();
                            }
                        });
                    } else {
                        loginEt.setError("Veuillez  inscrire avec un e-mail correct");
                    }
                } else {
                    Toast.makeText(mContext, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signup_tv:
                ((AuthentificationActivity) mContext).replaceFragment(new SignUpFragment());
                break;
        }


    }

    private boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void reload() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        //check if email is verified
                        if (user.isEmailVerified()) {
                            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                            Toast.makeText(mContext, "Authentifié avec: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            redirectToHome();
                        } else {
                            Toast.makeText(mContext, "L'email n'est pas verifié, Vérifiez votre boîte de réception", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    } else {
                        Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            }
        });

    }


    private void redirectToHome() {
        Intent intent = new Intent(mContext, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        mContext.finish();
    }

}