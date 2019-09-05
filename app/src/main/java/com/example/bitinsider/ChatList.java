package com.example.bitinsider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ChatList extends AppCompatActivity {

    ListView chatListView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
   public static String reciever="";
    ImageView homeImageView, chatImageView, profileImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListView=(ListView)findViewById(R.id.chatListView);
        DatabaseReference chatList=database.getReference("Chats/"+MainActivity.rollno);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        chatListView.setAdapter(adapter);
        homeImageView = (ImageView)findViewById(R.id.homeImageView);
        chatImageView = (ImageView) findViewById(R.id.chatImageView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatList.this, Years.class);
                startActivity(i);


            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatList.this, Profile.class);
                startActivity(i);

            }
        });


        chatList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> map=(Map<String, Object>)dataSnapshot.getValue();
                Set<String> set=map.keySet();
                Object[] arr=set.toArray();
                for(int i=0;i<set.size();i++)
                {
                    arrayList.add(arr[i].toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ChatList.this,PTPChat.class);
                reciever=arrayList.get(i);
                intent.putExtra("FROM_ACTIVITY", "ChatList");
                startActivity(intent);

            }
        });

    }
}