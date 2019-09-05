package com.example.bitinsider;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PTPChat extends AppCompatActivity {
    ListView chats;
    EditText msg;
    Button send;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String rollno;
    int number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptpchat);

        msg=(EditText)findViewById(R.id.msgEditText);
        send=(Button)findViewById(R.id.sendButton);
        chats=(ListView)findViewById(R.id.messagesListView);

        Intent mIntent = getIntent();
        String previousActivity= mIntent.getStringExtra("FROM_ACTIVITY");
        if(previousActivity.equals("Profile"))
        {
                rollno=Profile.rollno;
        }
        else if(previousActivity.equals("ChatList"))
        {
            rollno=ChatList.reciever;
        }

        final DatabaseReference peer=database.getReference("Chats/"+MainActivity.rollno+"/"+rollno);
        final DatabaseReference peer2=database.getReference("Chats/"+rollno+"/"+MainActivity.rollno);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        chats.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sendMsg=msg.getText().toString();
                if(!sendMsg.equals(""))
                {
                    peer.child(Integer.toString(number+1)).setValue(sendMsg);
                    peer2.child(Integer.toString(number+1)).setValue(">> "+sendMsg);
                    msg.setText("");
                }

            }
        });
        peer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,String> map=(Map<String,String>)dataSnapshot.getValue();
                Collection<String> coll=map.values();
                number=(int)dataSnapshot.getChildrenCount();
               // Toast.makeText(PTPChat.this, coll.toString(), Toast.LENGTH_SHORT).show();
                Object[] arr=coll.toArray();
                arrayList.clear();
                arrayList.addAll(0,coll);
                Toast.makeText(PTPChat.this, arrayList.toString(), Toast.LENGTH_SHORT).show();
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}