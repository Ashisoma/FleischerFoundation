package com.example.fleischerfoundation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity{

    CircleMenu circleMenu;
    ConstraintLayout constraintLayout;

//    @BindView(R.id.button) Button mMentor;
//    @BindView(R.id.button2) Button mStudent;
//    @BindView(R.id.button3) Button mDonort4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

//        mMentor.setOnClickListener(this);
//        mStudent.setOnClickListener(this);
//        mDonor.setOnClickListener(this);

        circleMenu = findViewById(R.id.circleMenu);
        constraintLayout = findViewById(R.id.contraint);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.menu_icon, R.drawable.cancel_icon)
                .addSubMenu(Color.parseColor("#2B6E2E"), R.drawable.student_icon)
                .addSubMenu(Color.parseColor("#83e85a"), R.drawable.profile_icon)
                .addSubMenu(Color.parseColor("#EC1C1C"), R.drawable.donate_icon)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        switch (index){
                            case 0:
                                Toast.makeText(WelcomeActivity.this, "Student Registration", Toast.LENGTH_LONG).show();
//                                constraintLayout.setBackgroundColor();
                                Intent intent = new Intent(WelcomeActivity.this, StudentCreateAccountActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(WelcomeActivity.this, "Mentor Registration", Toast.LENGTH_LONG).show();
//                                constraintLayout.setBackgroundColor();
                                Intent intent1 = new Intent(WelcomeActivity.this, MentorCreateAccountActivity.class);
                                startActivity(intent1);
                                break;
                            case 2:
                                Toast.makeText(WelcomeActivity.this, "Give a donation", Toast.LENGTH_LONG).show();
//                                constraintLayout.setBackgroundColor();
                                Intent intent2 = new Intent(WelcomeActivity.this, DonorCreateAccountActivity.class);
                                startActivity(intent2);
                                break;

                        }
                    }
                });

    }

}