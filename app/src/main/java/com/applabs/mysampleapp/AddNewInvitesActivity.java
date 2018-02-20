package com.applabs.mysampleapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.applabs.mysampleapp.model.Invites;
import com.applabs.mysampleapp.model.User;
import com.applabs.mysampleapp.support.UserHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewInvitesActivity extends AppCompatActivity {

    EditText edEmailsEntry, edMessage;
    Button sendInvite;
    int emaildCount = 0;
    private DatabaseReference mFirebaseDatabase;
    ProgressBar progres;
    String[] emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invites);

        progres = (ProgressBar) findViewById(R.id.progres);
        edEmailsEntry = (EditText) findViewById(R.id.edEmailsEntry);
        edMessage = (EditText) findViewById(R.id.edMesssage);
        sendInvite = (Button) findViewById(R.id.btnSendInvites);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("").child("invites");
        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edEmailsEntry.getText().toString()))
                    Toast.makeText(AddNewInvitesActivity.this, R.string.error_empty_email, Toast.LENGTH_SHORT).show();

                else if (TextUtils.isEmpty(edMessage.getText().toString()))
                    Toast.makeText(AddNewInvitesActivity.this, R.string.error_empty_message, Toast.LENGTH_SHORT).show();

                else {
                    emails = edEmailsEntry.getText().toString().split(",");
                    emaildCount = emails.length;
                    progres.setVisibility(View.VISIBLE);
                    for (int i = 0; i < emails.length; i++) {
                        Invites invites = new Invites();
                        invites.setSenderEmail(UserHelper.getUserEmail());
                        invites.setReceiverEmail(emails[i]);
                        invites.setAllow(true);
                        invites.setInviteMessage(edMessage.getText().toString());

                        String inviteId = mFirebaseDatabase.push().getKey();
                        // pushing user to 'users' node using the userId
                        mFirebaseDatabase.child(inviteId).setValue(invites).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                checkCompletion();
                            }
                        });


                    }


                }
            }
        });
    }

    int addCompleteCount = 0;

    private void checkCompletion() {
        addCompleteCount++;

        if (emaildCount == addCompleteCount) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progres.setVisibility(View.GONE);
                }
            });

            Intent emailLauncher = new Intent(Intent.ACTION_SEND_MULTIPLE);
            emailLauncher.setType("message/rfc822");
            emailLauncher.putExtra(Intent.EXTRA_EMAIL, emails);
            emailLauncher.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.txt_email_sub));
            emailLauncher.putExtra(Intent.EXTRA_TEXT, "hey check this message body!"); //TODO: Add deeplink
            try{
                startActivityForResult(emailLauncher, 100);
            }catch(ActivityNotFoundException e){

            }




        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){

            setResult(RESULT_OK);
            finish();
        }
    }
}
