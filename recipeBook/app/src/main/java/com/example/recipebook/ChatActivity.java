package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recipebook.Adapter.TextAdapter;
import com.example.recipebook.Model.chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ImageButton sndbtn;
    EditText sndtxt;
    FirebaseAuth firebaseAuth;
    String uid;

    TextAdapter textAdapter;
    ArrayList<chat> mChat;
    RecyclerView rview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rview = findViewById(R.id.viewRecycler);
        rview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rview.setLayoutManager(linearLayoutManager);

        sndbtn = findViewById(R.id.btn_send);
        sndtxt = findViewById(R.id.txt_send);

        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        mChat = new ArrayList<chat>();

        databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot sd : snapshot.getChildren()) {
                    chat text = sd.getValue(chat.class);
                    mChat.add(text);
                }

                    textAdapter = new TextAdapter(ChatActivity.this, mChat);
                    rview.setAdapter(textAdapter);

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sndbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sndtxt.getText().toString();
                if (!msg.equals("")) {
                    sendMsg(uid, msg);
                } else {
                    Toast.makeText(ChatActivity.this, "you can't text", Toast.LENGTH_SHORT).show();
                }
                sndtxt.setText("");

            }
        });


    }

    public void sendMsg(String sender, String text) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashmp = new HashMap<>();
        hashmp.put("Sender", sender);
        hashmp.put("Text", text);
        databaseReference.child("chats").push().setValue(hashmp);
    }
}

