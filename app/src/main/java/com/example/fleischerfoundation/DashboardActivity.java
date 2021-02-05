package com.example.fleischerfoundation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    @BindView(R.id.goToEmail) ImageView goToChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("users");

        goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, ChatActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        userId = user.getUid();

        final TextView greeting = findViewById(R.id.greeting);
        final TextView fullName = findViewById(R.id.accountName);
        final TextView accountEmail = findViewById(R.id.email);
        final TextView cohort = findViewById(R.id.year);
        final TextView mentorName = findViewById(R.id.mentor);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper profile = snapshot.getValue(UserHelper.class);
                if (profile != null){
                    String name = profile.fullName;
                    String email = profile.email;
                    String year = profile.year;
                    String mentor = profile.mentor;

                    greeting.setText("Welcome, "+ name + "!");
                    fullName.setText(name);
                    accountEmail.setText(email);
                    cohort.setText(year);
                    mentorName.setText(mentor);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        });
    }
}