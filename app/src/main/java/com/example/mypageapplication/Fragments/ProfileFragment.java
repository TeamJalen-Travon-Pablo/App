package com.example.mypageapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypageapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    ImageView background, pick_image;
    CircleImageView ic_profile_image;
    TextView followers, followers_count,following,following_count;
    TextView username, bio;
    Button settings, update_bg;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    public ProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize(view);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        return view;
    }
        private void initialize(View v){
        background=v.findViewById(R.id.background);
        pick_image=v.findViewById(R.id.pick_image);
        ic_profile_image=v.findViewById(R.id.profile);
        followers=v.findViewById(R.id.followers);
        followers_count=v.findViewById(R.id.followers_count);
        following=v.findViewById(R.id.following);
        username=v.findViewById(R.id.username);
        bio=v.findViewById(R.id.bio);
        update_bg=v.findViewById(R.id.update_bg);
        settings=v.findViewById(R.id.settings);
        recyclerView=v.findViewById(R.id.recyclerView);
        }
        private void getUserData(){
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n=snapshot.child("full_name").getValue().toString();

                if (snapshot.child("bio").exists()){
                    String b=snapshot.child("bio").getValue().toString();
                    bio.setText(b);
                } else {
                    bio.setText("Add bio");
                }
                if (snapshot.child("profile").exists()) {
                    String b=snapshot.child("profile").getValue().toString();
                    Picasso.get().load(b).placeholder(R.drawable.ic_profile_image).into(ic_profile_image);
                }else{
                    ic_profile_image.setImageResource(R.drawable.ic_profile_image);
                }
                username.setText(n);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
}