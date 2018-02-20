package com.applabs.mysampleapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.applabs.mysampleapp.R;
import com.applabs.mysampleapp.adapter.InviteAdapter;
import com.applabs.mysampleapp.model.Invites;
import com.applabs.mysampleapp.support.UserHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InvitedFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    private DatabaseReference mFirebaseDatabase;


    public InvitedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invited, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fbadd);
        floatingActionButton.setVisibility(View.GONE);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("").child("invites");

        refreshListView();
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            refreshListView();
        }
    }

    ArrayList<Invites> invites = new ArrayList<>();
    private void refreshListView() {

        mFirebaseDatabase.orderByChild("receiverEmail").equalTo(UserHelper.getUserEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String key = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    key = snapshot.getKey();
                    Invites invites1 = snapshot.getValue(Invites.class);
                    invites.add(invites1);
                    break;
                }

                if (invites != null && invites.size() > 0) {
                    InviteAdapter inviteAdapter = new InviteAdapter(getContext(), invites, null);
                    recyclerView.setAdapter(inviteAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}