package com.example.mypageapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypageapplication.Adapters.SearchAdapter;
import com.example.mypageapplication.Model.UsersList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUsersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText et_search;
    Toolbar toolbar;
    List<UsersList> lists;
    SearchAdapter adapter;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        recyclerView = findViewById(R.id.recyclerView);
        et_search = findViewById(R.id.et_search);
        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        lists = new ArrayList<>();
        adapter = new SearchAdapter(this,lists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getUsersList();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getSupportActionBar(Toolbar toolbar) {
        return;
    }

    private void searchUsers(String a) {
        Query query=reference.orderByChild("full name").startAt(et_search.getText().toString()).endAt(et_search.getText().toString()+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    UsersList usersList=datasnapshot.getValue(UsersList.class);
                    lists.add(usersList);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getUsersList() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    UsersList usersList=datasnapshot.getValue(UsersList.class);
                    lists.add(usersList);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchUsersActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}