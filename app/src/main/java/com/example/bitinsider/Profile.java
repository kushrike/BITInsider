package com.example.bitinsider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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

import java.io.File;
import java.util.Map;
import java.util.MissingFormatArgumentException;


public class Profile extends AppCompatActivity {
    public static int studyear=FirstList.year;public static String rollno=FirstList.rollno;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference student1 = database.getReference(studyear + "/" + rollno );
    DatabaseReference chatperson=database.getReference("Chats/"+rollno);
    DatabaseReference selfchat=database.getReference("Chats/"+MainActivity.rollno);


    ImageView profileImage;
    TextView nameTextView, branchTextView,aboutTextView;
    EditText aboutEditText;
    Button editButton, saveButton,chatButton;
    ImageView homeImageView, chatImageView, profileImageViewButton;
    boolean currentuser;
    public void onBackPressed() {
        startActivity(new Intent(Profile.this, FirstList.class));
        finish();
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toast.makeText(this, studyear+" "+rollno, Toast.LENGTH_LONG).show();

        nameTextView = (TextView)findViewById(R.id.nameTextView);
        branchTextView = (TextView)findViewById(R.id.branchTextView);
        aboutTextView = (TextView) findViewById(R.id.aboutTextView);
        editButton = (Button)findViewById(R.id.editButton);
        saveButton = (Button)findViewById(R.id.saveButton);
        profileImage=(ImageView)findViewById(R.id.profileImageView);
        chatButton=(Button)findViewById(R.id.chatButton);

        homeImageView = (ImageView)findViewById(R.id.homeImageView);
        chatImageView = (ImageView) findViewById(R.id.chatImageView);
        profileImageViewButton = (ImageView) findViewById(R.id.profileImageViewButton);

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, Years.class);
                startActivity(i);
//                deleteCache(Profile.this);
//                finish();
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


               // homeImageView.setImageResource(R.drawable.homeactive);
                //profileImageViewButton.setImageResource(R.drawable.profileinactive);
            }
        });

        chatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, ChatList.class);
                startActivity(i);
                deleteCache(Profile.this);
                finish();
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                //chatImageView.setImageResource(R.drawable.chatactive);;
                //profileImageViewButton.setImageResource(R.drawable.profileinactive);
            }
        });

//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Years.this, Profile.class);
//                startActivity(i);
//
//                homeImageView.setImageResource(R.drawable.homeinactive);
//                profileImageView.setImageResource(R.drawable.profileactive);
//            }
//        });

        student1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // Toast.makeText(Profile.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                nameTextView.setText("Name : "+map.get("Name").toString());
                aboutTextView.setText("About : "+map.get("About").toString());
                branchTextView.setText("Branch : "+map.get("Branch").toString());
                profileImage.setImageResource(Integer.parseInt(map.get("Photo").toString()));

                if(rollno.equals(MainActivity.rollno)==true)
                {
                    currentuser=true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(currentuser==true)
        {
            editButton.setVisibility(View.VISIBLE);
            //chatButton.setVisibility(View.VISIBLE);
        }
        else
        {
            chatButton.setVisibility(View.VISIBLE);
        }


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                aboutEditText.setText(aboutTextView.getText());
                aboutTextView.setVisibility(View.INVISIBLE);
                aboutEditText.setVisibility(View.VISIBLE);

                // aboutEditText.setEnabled(true);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);
                aboutTextView.setText(aboutEditText.getText());
                aboutTextView.setVisibility(View.VISIBLE);
                aboutEditText.setVisibility(View.INVISIBLE);
                // aboutEditText.setEnabled(false);

                // String about = aboutEditText.getText().toString();

                //now save this to the database
                student1.child("About").setValue(aboutTextView.getText().toString());
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Profile.this,PTPChat.class);
                chatperson.child(MainActivity.rollno).child("0").setValue(MainActivity.rollno);
                selfchat.child(rollno).child("0").setValue(rollno);
                i.putExtra("FROM_ACTIVITY", "Profile");
                startActivity(i);


            }
        });
    }
}