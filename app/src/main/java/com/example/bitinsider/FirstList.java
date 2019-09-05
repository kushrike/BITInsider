package com.example.bitinsider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static android.R.layout.simple_expandable_list_item_1;


public class FirstList extends AppCompatActivity {

    public static int year=Years.year;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference currentYear=database.getReference(Integer.toString(year));
    ListView studentlistView;
    ArrayAdapter<String> adapter;
    public static String rollno;

@Override
public void onBackPressed() {
    startActivity(new Intent(FirstList.this, Years.class));
    finish();
}


    //ListView list =
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //studentlistView.setAdapter(adapter);
        setContentView(R.layout.activity_first_list);
        studentlistView=(ListView)findViewById(R.id.studentListView);
        final ArrayList al=new ArrayList(10);
        adapter=new ArrayAdapter<String>(FirstList.this, simple_expandable_list_item_1,al);
        currentYear.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                // Toast.makeText(FirstList.this, dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                Set<String> keyset =map.keySet();
                Object[] orr=keyset.toArray();

                for(int i=0;i<keyset.size();i++)
                {
                    Object o=map.get(orr[i].toString());
                    Map<String, String> map1 = (Map<String, String>) o;
                    al.add(map1.get("Name"));

                }

                studentlistView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                studentlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String name=al.get(i).toString();
                        Intent intent=new Intent(FirstList.this,Profile.class);

                        rollno=al.get(i).toString().substring(al.get(i).toString().lastIndexOf(' ')+1);
                        //Toast.makeText(FirstList.this, Profile.studyear+"  "+Profile.rollno, Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    }
                });
                // Toast.makeText(FirstList.this, al.toString(), Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}