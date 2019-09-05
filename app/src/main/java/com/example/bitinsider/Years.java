package com.example.bitinsider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class Years extends AppCompatActivity {
    Button b1,b2,b3,b4,b5;
    static int year;
    ImageView homeImageView, chatImageView, profileImageView;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference student = database.getReference(MainActivity.studyear + "/" + MainActivity.rollno);
//    @Override
//    protected void onStop() {
//        super.onStop();
//        student.child("LINSTAT").setValue("NO");
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_years);
        b1=(Button)findViewById(R.id.firstYr);
        b2=(Button)findViewById(R.id.secondYr);
        b3=(Button)findViewById(R.id.thirdYr);
        b4=(Button)findViewById(R.id.forthYr);
        b5=(Button)findViewById(R.id.alumni);
        homeImageView = (ImageView)findViewById(R.id.homeImageView);
        chatImageView = (ImageView) findViewById(R.id.chatImageView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);

//        homeImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Years.this, FirstList.class);
//                startActivity(i);
//            }
//        });

        chatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Years.this, ChatList.class);
                startActivity(i);

               // chatImageView.setImageResource(R.drawable.chatactive);;
               // homeImageView.setImageResource(R.drawable.homeinactive);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Years.this, Profile.class);
                startActivity(i);

//                homeImageView.setImageResource(R.drawable.homeinactive);
//                profileImageView.setImageResource(R.drawable.profileactive);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=2019;
                Intent i=new Intent(Years.this, FirstList.class);
                startActivity(i);
                //finish();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    year=2018;
                Intent j=new Intent(Years.this, FirstList.class);
                startActivity(j);
                //finish();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    year=2017;
                Intent k=new Intent(Years.this, FirstList.class);
                startActivity(k);
               //finish();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    year=2016;
                Intent l=new Intent(Years.this, FirstList.class);
                startActivity(l);
                //finish();
            }
        });
//        b5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent m=new Intent(Years.this, Alumini.class);
//                startActivity(m);
//                finish();
//            }
//        });
    }
}
