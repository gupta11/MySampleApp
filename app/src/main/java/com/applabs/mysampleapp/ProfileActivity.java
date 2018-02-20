package com.applabs.mysampleapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.applabs.mysampleapp.model.Invites;
import com.applabs.mysampleapp.support.UserHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    EditText etBio;
    Button submitBtn;
    int emailedCount = 0;
    private DatabaseReference mFirebaseDatabase;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progress = (ProgressBar) findViewById(R.id.progress);
        etBio = (EditText) findViewById(R.id.etBio);
        etBio.setText(UserHelper.getPrefUserBio());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        submitBtn = (Button) findViewById(R.id.btnSubmit);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("").child("users");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (TextUtils.isEmpty(etBio.getText().toString()))
                Toast.makeText(ProfileActivity.this, R.string.error_empty_bio, Toast.LENGTH_SHORT).show();
//            else if (TextUtils.isEmpty(etMessage.getText().toString()))
//                Toast.makeText(AddNewInvitesActivity.this, R.string.error_empty_message, Toast.LENGTH_SHORT).show();
            else {
                progress.setVisibility(View.VISIBLE);
                mFirebaseDatabase.child(UserHelper.getID()).child("bio").setValue(etBio.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress.setVisibility(View.GONE);
                        UserHelper.setPrefUserBio(etBio.getText().toString());
                        Toast.makeText(ProfileActivity.this, R.string.txt_bio_save_success, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
