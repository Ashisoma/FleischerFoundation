package com.example.fleischerfoundation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fleischerfoundation.helper.MentorHelper;
import com.example.fleischerfoundation.helper.UserHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MentorCreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.studentNameTextView) EditText mStudentEditText;
    @BindView(R.id.professionTextView) EditText mProfessionEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView)
    TextView mLoginTextView;

    @BindView(R.id.createUserButton)
    Button mCreateUserButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_create_account);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);

        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mLoginTextView){
            Intent intent = new Intent(MentorCreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } if (v == mCreateUserButton){
            createNewUser();
        }
    }
    public void createNewUser(){
        String name = mNameEditText.getText().toString().trim();
        String student = mStudentEditText.getText().toString().trim();
        String profession = mProfessionEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();



        if(TextUtils.isEmpty(name)){
            mNameEditText.setError("Please enter your name");
            mNameEditText.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(student)){
            mPasswordEditText.setError("Please enter your mentor's name");
            mPasswordEditText.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(profession)){
            mProfessionEditText.setError("Please enter your cohort year");
            mProfessionEditText.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            mPasswordEditText.setError("Please enter password");
            mPasswordEditText.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(confirmPassword)){
            mConfirmPasswordEditText.setError("Confirm your password");
            mConfirmPasswordEditText.requestFocus();
            return;
        }
        else if (!password.equals(confirmPassword)){
            mConfirmPasswordEditText.setError("Passwords don't match");
            mConfirmPasswordEditText.requestFocus();
            return;
        }
        else if(password.length()>6){
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            mPasswordEditText.requestFocus();
            return;
        }
//        else if(year.length()>4){
//            mYearTextView.setError("Enter the valid correct year");
//            mYearTextView.requestFocus();
//            return;
//        }
        else if (!ValidEmail(email)){
            mEmailEditText.setError("Enter a valid email address");
            mEmailEditText.requestFocus();
            return;
        }
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.show();
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    MentorHelper mentor = new MentorHelper(name,student,profession,email,password);
                    FirebaseDatabase.getInstance().getReference("mentors")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(mentor).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MentorCreateAccountActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MentorCreateAccountActivity.this, MentorDashboard.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(MentorCreateAccountActivity.this, "Registration failed failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(MentorCreateAccountActivity.this,"Registration failed failed.", Toast.LENGTH_SHORT).show();
                }
                mAuthProgressDialog.dismiss();
            }
        });
    }
    private boolean ValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(MentorCreateAccountActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

}