package com.applabs.mysampleapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applabs.mysampleapp.AddNewInvitesActivity;
import com.applabs.mysampleapp.R;
import com.applabs.mysampleapp.adapter.InviteAdapter;
import com.applabs.mysampleapp.model.ActionCallback;
import com.applabs.mysampleapp.model.Invites;
import com.applabs.mysampleapp.support.UserHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InviteeFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    private DatabaseReference mFirebaseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invited, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fbadd);
        floatingActionButton.setVisibility(View.VISIBLE);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("").child("invites");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewInvitesActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        refreshListView();


        return rootView;
    }

    InviteAdapter inviteAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            refreshListView();
        }
    }

    ArrayList<Invites> invites = new ArrayList<>();

    private void refreshListView() {

        mFirebaseDatabase.orderByChild("senderEmail").equalTo(UserHelper.getUserEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String key = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    key = snapshot.getKey();
                    Invites invites1 = snapshot.getValue(Invites.class);
                    invites1.setInvitesKey(key);
                    invites.add(invites1);
                }

                if (invites != null && invites.size() > 0) {
                    inviteAdapter = new InviteAdapter(getContext(), invites, new ActionCallback() {
                        @Override
                        public void isAllowClicked(final int postion) {
                            mFirebaseDatabase.child(invites.get(postion).getInvitesKey()).child("allow").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getActivity(), R.string.txt_allow_done, Toast.LENGTH_SHORT).show();
                                    inviteAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                        @Override
                        public void isBlockClicked(final int postion) {
                            mFirebaseDatabase.child(invites.get(postion).getInvitesKey()).child("allow").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getActivity(), R.string.txt_block_done, Toast.LENGTH_SHORT).show();
                                    inviteAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    recyclerView.setAdapter(inviteAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}