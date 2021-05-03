package com.example.mypageapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class OthersProfiles extends AppCompatActivity {
    ImageView background;
    CircleImageView ic_profile_image;
    TextView followers, followers_count,following,following_count;
    TextView username, bio;
    Button isFollow;
    RecyclerView recyclerView;
    String userId;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profiles);

        init();
        userId=getIntent().getStringExtra("userId");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        getUserData();
    }
    private void init(){
        background=findViewById(R.id.background);

        ic_profile_image=findViewById(R.id.profile);
        followers=findViewById(R.id.followers);
        followers_count=findViewById(R.id.followers_count);
        following=findViewById(R.id.following);
        username=findViewById(R.id.username);
        bio=findViewById(R.id.bio);

        isFollow=findViewById(R.id.isFollow);
        recyclerView=findViewById(R.id.recyclerView);
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