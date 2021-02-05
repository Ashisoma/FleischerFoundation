package com.example.fleischerfoundation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.button) Button mMentor;
    @BindView(R.id.button2) Button mStudent;
    @BindView(R.id.button3) Button mDonor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        mMentor.setOnClickListener(this);
        mStudent.setOnClickListener(this);
        mDonor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mMentor) {
            Intent intent = new Intent(WelcomeActivity.this, MentorCreateAccountActivity.class);
            startActivity(intent);
//            finish();
        }
        if (view == mStudent){
            Intent intent = new Intent(WelcomeActivity.this, StudentCreateAccountActivity.class);
            startActivity(intent);
//            finish();
        }
        if (view== mDonor){
            Intent intent = new Intent(WelcomeActivity.this, DonorCreateAccountActivity.class);
            startActivity(intent);
        }
    }
}