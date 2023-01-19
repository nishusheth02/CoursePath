package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.schedulab.databinding.ActivityStudentDefaultBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StudentDefault extends DrawerBase {
    ActivityStudentDefaultBinding binding;
    private TextView actAge, actStudentID, actName, actEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityStudentDefaultBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Student Dashboard");
        setContentView(binding.getRoot());

        actAge = (TextView) findViewById(R.id.actAge);
        actName = (TextView) findViewById(R.id.actName);
        actEmail = (TextView) findViewById(R.id.actEmail);
        actStudentID = (TextView) findViewById(R.id.actStudentID);



        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users"); //update URL!!!
        ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot child:dataSnapshot.getChildren()) {
                Map<String, Object> studentMap = (HashMap<String, Object>) dataSnapshot.child(currentuser).getValue();
                String age = (String)studentMap.get("age");
                actAge.setText(age);
                String email = (String)studentMap.get("email");
                actEmail.setText(email);
                String fullName = (String)studentMap.get("fullName");
                actName.setText(fullName);
                String studentId = (String)studentMap.get("studentId");
                actStudentID.setText(studentId);


            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("warning", "loadPost:onCancelled", databaseError.toException());
        }
    });
}
}