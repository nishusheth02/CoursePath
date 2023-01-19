package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.schedulab.databinding.ActivityRequestTimelineBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.Locale;


public class RequestTimeline extends DrawerBase implements  View.OnClickListener {

    private Button Generate;
    public String s;
    private EditText input;
    private DatabaseReference coursesRef, userRef;
    private FirebaseUser fUser;
    private String Uid;
    private int a,c,count;
    private boolean b;
    Context myContext = this;

    ActivityRequestTimelineBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestTimelineBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Request Timeline");
        setContentView(binding.getRoot());

        input = (EditText) findViewById(R.id.CourseG);

        Generate = (Button) findViewById(R.id.Generate);
        Generate.setOnClickListener(this);

    }

    public void Back(View view) {
        Intent i = new Intent(RequestTimeline.this, StudentDefault.class);
        startActivity(i);
    }


    @Override
    public void onClick(View view) {
        b=true;
        s = input.getText().toString().toUpperCase().trim();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        coursesRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        //fUser = FirebaseAuth.getInstance().getCurrentUser();
        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Uid="baJghyfNtINRCrOFxN5QFVjiTSj2";
        if (s.equals("")) {
            input.setError("Course code is required");
            input.requestFocus();
            return;
        } else {
            getNum1();
            checkValid(s);
        }

    }

    public void checkValid (String s){
        String[] parts = s.split(",");
        count=0;
        for (String part : parts) {
            part.toUpperCase().trim();
            Query query = coursesRef.orderByChild("code").equalTo(part.toUpperCase().trim());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        input.setError("Please enter the correct code");
                        input.requestFocus();
                        b=false;
                    } else if(b==true) {
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snp : snapshot.child(Uid).child("coursesTaken").getChildren()) {
                                    if (snp.getValue().toString().toUpperCase().trim().equals(part)) {
                                        input.setError("Course already taken");
                                        input.requestFocus();
                                        b=false;
                                    } else {
                                        count++;
                                    }
                                }
                                if (part.equals(getLast(s).toUpperCase())&&b==true&&count==c*getNum(s)) {
                                        Intent i = new Intent(myContext,GenerateTimeline.class);
                                    i.putExtra("input", s);
                                    System.out.println(s);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RequestTimeline.this, "Course failed to add", Toast.LENGTH_LONG);
                }
            });
            if(!b) {
                //input.setError("Please enter the correct code");
                //input.requestFocus();
                break;
            }
        }
    }
    public String getLast(String s) {
        a=getNum(s);
        int b=0;
        String last=null;
        String[] parts = s.split(",");
        for (String part : parts) {
            b++;
            if (a==b) {
                last = part.toUpperCase().trim();
            }
        }
        return last;
    }
    public int getNum(String s) {
        int b=0;
        String[] parts = s.split(",");
        for (String part : parts) {
            b++;
        }
        return b;
    }
    public void getNum1() {
        c=0;
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snp : snapshot.child(Uid).child("coursesTaken").getChildren()) {
                        c++;
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}


