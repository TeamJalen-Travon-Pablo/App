package com.example.mypageapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.mypageapplication.Fragments.FavouriteFragment;
import com.example.mypageapplication.Fragments.FeedFragment;
import com.example.mypageapplication.Fragments.FollowingFragment;
import com.example.mypageapplication.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.frame_layout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.following) {
                    FollowingFragment followingFragment = new FollowingFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, followingFragment);
                    fragmentTransaction.commit();
                } else if (id == R.id.feed) {
                    FeedFragment feedFragment = new FeedFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, feedFragment);
                    fragmentTransaction.commit();
                } else if (id == R.id.favourite) {
                    FavouriteFragment favouriteFragment = new FavouriteFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, favouriteFragment);
                    fragmentTransaction.commit();
                } else if (id == R.id.Profile) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, profileFragment);
                    fragmentTransaction.commit();

                    SharedPreferences.Editor editor = getSharedPreferences("USER", Context.MODE_PRIVATE).edit();
                    editor.putString("profileId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor.apply();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.following);
    }
}