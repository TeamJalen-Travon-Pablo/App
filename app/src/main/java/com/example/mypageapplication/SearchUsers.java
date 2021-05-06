package com.example.mypageapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SearchUsers extends AppCompatActivity {
    RecyclerView recyclerView;
    List<UsersList> dataOneList;
    SearchAdapter adapter;
    Toolbar toolbar;

    EditText et_search;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        et_search=findViewById(R.id.et_search);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        readUsers();

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

        dataOneList =new ArrayList<>();

        adapter=new SearchAdapter(this,dataOneList);
        recyclerView.setAdapter ( adapter );


    }

    private void readUsers()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (et_search.getText().toString().equals("")) {
                    dataOneList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        UsersList dataOne = dataSnapshot.getValue(UsersList.class);
                        dataOneList.add(dataOne);

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchUsers.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });









    }

    private void searchUsers(String a)
    {
        Query query= FirebaseDatabase.getInstance ().getReference ("Users").orderByChild ( "username" )
                .startAt ( et_search.getText().toString() )
                .endAt ( et_search.getText().toString()+"\uf8ff" );

        query.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataOneList.clear ();
                for (DataSnapshot snapshot : dataSnapshot.getChildren ()){
                    UsersList user = snapshot.getValue (UsersList.class);
                    dataOneList.add ( user );
                }

                adapter.notifyDataSetChanged ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(SearchUsers.this, "Error "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SearchUsers.this,HomeActivity.class));
    }
}