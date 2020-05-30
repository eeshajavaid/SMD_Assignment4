package com.example.assignment4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder1> {
    List<User> users;
    private Context context;

    public RecyclerAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact,parent,false);
        ViewHolder1 viewHolder = new ViewHolder1(view, context, users);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder1 holder, int position) {
        holder.name.setText(users.get(position).name);
        Uri uri = Uri.parse(users.get(position).image_uri);
        holder.image.setImageURI(uri);
       // holder.email.setText(users.get(position).email);
        //holder.phone.setText(String.valueOf(users.get(position).number));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    public static class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Context context;
        List<User> users;
        TextView name, phone, email;
        ImageView image;
        public ViewHolder1(View itemView, Context context, List<User> users) {
            super(itemView);
            name = itemView.findViewById(R.id.profile_name);
            itemView.setOnClickListener(this);
            this.context =context;
            this.users = users;
            image = itemView.findViewById(R.id.profile_pic);
            //phone = itemView.findViewById(R.id.profile_number);
            //email = itemView.findViewById(R.id.profile_email);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ContactScreen.class);
            intent.putExtra("name", users.get(getAdapterPosition()).name);
            intent.putExtra("email", users.get(getAdapterPosition()).email);
            context.startActivity(intent);
        }
    }
}
