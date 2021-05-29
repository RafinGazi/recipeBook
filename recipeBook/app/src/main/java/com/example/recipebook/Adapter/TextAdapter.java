package com.example.recipebook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipebook.Model.Recipe;
import com.example.recipebook.Model.chat;
import com.example.recipebook.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ChatViewHolder> {
    Context chatcontext;
    ArrayList<chat> chatlist;
    FirebaseUser firebaseUser;


    public TextAdapter(Context chatcontext, ArrayList<chat> chatlist) {
        this.chatcontext = chatcontext;
        this.chatlist = chatlist;
    }

    @NonNull
    @Override
    public TextAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(chatcontext);
        View v= layoutInflater.inflate(R.layout.convolook, parent, false);
        return new TextAdapter.ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TextAdapter.ChatViewHolder holder, int position) {
        chat cmodel = chatlist.get(position);
        //holder.uname.setText(cmodel.getSender());
        holder.utext.setText(cmodel.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        //TextView uname;
        TextView utext;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            //uname=itemView.findViewById(R.id.uName);
            utext=itemView.findViewById(R.id.msgtxt);
        }


    }

}
