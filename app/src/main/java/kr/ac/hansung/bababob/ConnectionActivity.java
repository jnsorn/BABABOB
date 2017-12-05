package kr.ac.hansung.bababob;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class ConnectionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ConnectionActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //SignIn
    private RelativeLayout mRelativeLayoutSignIn;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mBtnSignIn;
    private TextView mTextSignUp;
    private TextWatcher textWatcher;
    private String signInEmail;
    //SignUp
    private RelativeLayout mRelativeLayoutSignUp;
    private EditText mEditTextPasswordUp;
    private EditText mEditTextEmailUp;
    private Button mBtnSignUp;
    private TextView mTextSignIn;
    private TextWatcher textWatcherUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        setContentView(R.layout.activity_connection);
        initUI();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent it = new Intent(ConnectionActivity.this, MainActivity.class);
                    startActivity(it);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void initUI() {
        //signIn
        mRelativeLayoutSignIn = (RelativeLayout)findViewById(R.id.signin_relative_layout);
        mEditTextEmail = (EditText)findViewById(R.id.signin_email);
        mEditTextPassword = (EditText)findViewById(R.id.signin_password);
        mTextSignUp = (TextView)findViewById(R.id.txt_signUp);
        mBtnSignIn = (Button)findViewById(R.id.btn_signIn);
        //signUp
        mRelativeLayoutSignUp = (RelativeLayout)findViewById(R.id.signup_relative_layout);
        mEditTextPasswordUp = (EditText)findViewById(R.id.signup_password);
        mEditTextEmailUp = (EditText)findViewById(R.id.signup_email);
        mTextSignIn = (TextView)findViewById(R.id.txt_signIn);
        mBtnSignUp = (Button)findViewById(R.id.btn_signUp);

        mTextSignUp.setOnClickListener(this);
        mBtnSignIn.setOnClickListener(this);
        mBtnSignIn.setEnabled(false);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mEditTextEmail.getText().toString().trim().isEmpty()&&!mEditTextPassword.getText().toString().trim().isEmpty()) {
                    mBtnSignIn.setEnabled(true);
                }else
                    mBtnSignIn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mEditTextEmail.addTextChangedListener(textWatcher);
        mEditTextPassword.addTextChangedListener(textWatcher);

        mTextSignIn.setOnClickListener(this);
        mBtnSignUp.setOnClickListener(this);
        mBtnSignUp.setEnabled(false);

        textWatcherUp = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mEditTextEmailUp.getText().toString().trim().isEmpty()
                        &&!mEditTextPasswordUp.getText().toString().trim().isEmpty()) {
                    mBtnSignUp.setEnabled(true);
                }else
                    mBtnSignUp.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mEditTextPasswordUp.addTextChangedListener(textWatcherUp);
        mEditTextEmailUp.addTextChangedListener(textWatcherUp);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ConnectionActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ConnectionActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_signUp: {
                mEditTextEmail.setText("");
                mEditTextPassword.setText("");
                mRelativeLayoutSignIn.setVisibility(View.GONE);
                mRelativeLayoutSignUp.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.txt_signIn: {
                mEditTextPasswordUp.setText("");
                mEditTextEmailUp.setText("");
                mRelativeLayoutSignUp.setVisibility(View.GONE);
                mRelativeLayoutSignIn.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.btn_signIn: {
                signInEmail = mEditTextEmail.getText().toString().trim();
                if(checkEmailForm(signInEmail)) {
                    signIn(signInEmail,mEditTextPassword.getText().toString().trim());
                } else {
                    Toast.makeText(this,"Invaild Email",Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.btn_signUp: {
                if(checkEmailForm(mEditTextEmailUp.getText().toString().trim())) {
                    createAccount(mEditTextEmailUp.getText().toString().trim(), mEditTextPasswordUp.getText().toString().trim());
                } else {
                    Toast.makeText(this,"Invaild Email",Toast.LENGTH_LONG).show();
                }
                break;
            }
            default:
                break;
        }
    }

    public boolean checkEmailForm(String src){
        String emailRegex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return Pattern.matches(emailRegex, src);
    }
}
