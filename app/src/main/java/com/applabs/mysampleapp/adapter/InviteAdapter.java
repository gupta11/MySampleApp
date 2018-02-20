package com.applabs.mysampleapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applabs.mysampleapp.LoginActivity;
import com.applabs.mysampleapp.MainActivity;
import com.applabs.mysampleapp.R;
import com.applabs.mysampleapp.model.ActionCallback;
import com.applabs.mysampleapp.model.Invites;
import com.applabs.mysampleapp.model.User;
import com.applabs.mysampleapp.support.UserHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.MyViewHolder> {
    private Context mContext;
    ArrayList<Invites> invitesArrayList;
    ActionCallback actionCallback;
    private DatabaseReference mFirebaseDatabase;

    public InviteAdapter(Context applicationContext, ArrayList<Invites> invitesArrayList, ActionCallback actionCallback) {
        this.mContext = applicationContext;
        this.actionCallback = actionCallback;
        this.invitesArrayList = invitesArrayList;
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("");
    }

    @Override
    public int getItemCount() {
        return invitesArrayList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_invitee_emails, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Invites invites = invitesArrayList.get(position);

        if (actionCallback == null)
            holder.tvEmail.setText(invites.getSenderEmail());
        else
            holder.tvEmail.setText(invites.getReceiverEmail());

        if (actionCallback != null) {

            holder.tvAllow.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_light));
            holder.tvBlock.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_red_light));
            if (invites.isAllow()) {
                holder.tvBlock.setVisibility(View.VISIBLE);
                holder.tvAllow.setVisibility(View.GONE);
            } else {
                holder.tvBlock.setVisibility(View.GONE);
                holder.tvAllow.setVisibility(View.VISIBLE);
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvEmail, tvBlock, tvAllow;


        public MyViewHolder(View view) {
            super(view);
            tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            tvBlock = (TextView) view.findViewById(R.id.tvBlock);
            tvAllow = (TextView) view.findViewById(R.id.tvAllow);

            itemView.setOnClickListener(this);
            if (actionCallback != null) {
                tvBlock.setOnClickListener(this);
                tvAllow.setOnClickListener(this);
            } else {
                tvBlock.setVisibility(View.GONE);
                tvAllow.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            if (view == tvAllow)
                actionCallback.isAllowClicked(getLayoutPosition());

            else if (view == tvBlock)
                actionCallback.isBlockClicked(getLayoutPosition());

            else {

                //enable block once bio integrate

                String email = "";
                email = invitesArrayList.get(getLayoutPosition()).getSenderEmail();
                mFirebaseDatabase.child("users").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User userData = null;
                        String key = "";
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            key = snapshot.getKey();
                            userData = snapshot.getValue(User.class);

                            break;
                        }

                        if (userData != null) {
                            if (!TextUtils.isEmpty(userData.getBio()))
                                Toast.makeText(mContext, TextUtils.isEmpty(userData.getBio()) ? "" : userData.getBio(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        }
    }
}