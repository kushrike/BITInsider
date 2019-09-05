package com.example.bitinsider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {




    EditText rollNoEditText, passwordEditText;
    Button loginButton;
    TextView contactTextView;
    public static String studyear,rollno;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference student = database.getReference(studyear + "/" + rollno);

   String storedPass,storedPass1;
    boolean flag=false;
   public static final String TAG="MainActivity";

    @Override
    protected void onStop() {
        super.onStop();
        student.child("LINSTAT").setValue("NO");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        rollNoEditText = (EditText)findViewById(R.id.rollNoEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        contactTextView = (TextView)findViewById(R.id.contactTextView);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rollno = rollNoEditText.getText().toString();
                String studyeartemp = rollno.substring(rollno.length() - 2);
                studyear = "20" + studyeartemp;
                final String pass = passwordEditText.getText().toString();
                DatabaseReference dbr = database.getReference("");

                dbr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        Set set = map.keySet();
                        if (set.contains(rollno)) {
                            flag = true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //if (flag == true){
                    student = database.getReference(studyear + "/" + rollno);
                student.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                        String storedPass = map.get("Pass");
                        if (pass.equals(storedPass)) {
                            String LINSTAT = map.get("LINSTAT");
                            if (LINSTAT.equals("NO")) {
                                student.child("LINSTAT").setValue("YES");
                                student.child("Photo").setValue(R.mipmap.ic_launcher);
                                student.child("About").setValue(rollno);
                                Intent i = new Intent(MainActivity.this, Years.class);
                                startActivity(i);
                                finish();
                            }


                        } else {
                            Toast.makeText(MainActivity.this, "Authentication Problem\nPlease try again.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            //}
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Roll Number Not Found", Toast.LENGTH_SHORT).show();
//                }
            }
        });

//        DatabaseReference stud=database.getReference(studyear+"/"+rollno);
//        stud.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Map<String,String> map=(Map<String,String>) dataSnapshot.getValue();
//                String LINSTAT=map.get("LINSTAT");
//                if(LINSTAT.equals("YES"))
//                {
//                    Intent j=new Intent(MainActivity.this, Years.class);
//                    startActivity(j);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }



}
