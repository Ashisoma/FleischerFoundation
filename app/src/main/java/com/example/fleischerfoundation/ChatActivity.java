package com.example.fleischerfoundation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.cache.DiskLruCache;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.receipientEmail) EditText receiverEmail;
    @BindView(R.id.subject) EditText subject;
    @BindView(R.id.et_message) EditText messageSent;
    @BindView(R.id.send) Button sendButton;
    String sEmail, sPassword;

    private FirebaseAuth mAuth;
    private DataSnapshot snapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        UserHelper profile = snapshot.getValue(UserHelper.class);

        assert profile != null;
        sEmail = profile.email;
        sPassword = profile.password;

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Properties properties = new Properties();
                properties.put("mail.smpt.auth","true");
                properties.put("mail.smpt.starttls.enable","true");
                properties.put("mail.smpt.host","smpt.gmail.com");
                properties.put("mail.smpt.port","587");


                Session session = Session.getInstance(properties,new Authenticator() {

                    @Override
                    public PasswordAuthentication getPasswordAuthentication(){

                        return new PasswordAuthentication(profile.email,profile.password);
                    }
                });

                try {
                    Message message = new MimeMessage(session);

                    message.setFrom(new InternetAddress(sEmail));

                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail.getText().toString().trim()));

                    message.setSubject(subject.getText().toString().trim());

                    message.setText(messageSent.getText().toString().trim());

                    new SendMail().execute(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private class SendMail extends AsyncTask<Message,String,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ChatActivity.this, "Please Wait", "Sending Message", true, false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if (s.equals("Success")){
                Context context;
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle(Html.fromHtml("<font color = '#509324'>Success</font>"));
                builder.setMessage("Mail sent successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        receiverEmail.setText("");
                        subject.setText("");
                        messageSent.setText("");
                    }
                });

                builder.show();
            }else {
                Toast.makeText(getApplicationContext(), "Something went wrong!!",Toast.LENGTH_LONG ).show();
            }
        }
    }
}