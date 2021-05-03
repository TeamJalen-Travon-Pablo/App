package com.example.mypageapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypageapplication.Model.UsersList;
import com.example.mypageapplication.OthersProfiles;
import com.example.mypageapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    List<UsersList> lists;

    public SearchAdapter(Context context, List<UsersList> lists) {
        this.context = context;
        this.lists = lists;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsersList usersList = lists.get(position);
        holder.username.setText(usersList.getFull_name());
        try {
            Picasso.get().load(usersList.getProfile()).placeholder(R.drawable.ic_profile_image).into(holder.profile);
        } catch (Exception e){
            holder.profile.setImageResource(R.drawable.ic_profile_image);
        }

        if(usersList.getUid().equals(FirebaseAuth.getInstance().getUid())){
            holder.btn_follow.setVisibility(View.INVISIBLE);
            holder.btn_following.setVisibility(View.INVISIBLE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OthersProfiles.class);
                intent.putExtra("userId",usersList.getEmail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profile;
        TextView username;
        Button btn_follow,btn_following;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile=itemView.findViewById(R.id.profile);
            username=itemView.findViewById(R.id.username);
            btn_follow=itemView.findViewById(R.id.btn_follow);
            btn_following=itemView.findViewById(R.id.btn_following);
        }
    }
}
