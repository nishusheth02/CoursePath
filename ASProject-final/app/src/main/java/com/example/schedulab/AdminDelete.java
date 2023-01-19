package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.schedulab.databinding.ActivityAdminDeleteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminDelete extends AdminDrawerBase {

    ActivityAdminDeleteBinding binding;
    DatabaseReference databaseReference;
    DatabaseReference dataRef;
    DatabaseReference dataRefThird;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_admin_delete);
        binding = ActivityAdminDeleteBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Delete Course");
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseToDelete = binding.editTextTextPersonName6.getText().toString();

                deleteCourse(courseToDelete);
            }
        });
    }

    private void deleteCourse(String courseToDelete) {
        Query query = FirebaseDatabase.getInstance().getReference("Courses").orderByChild("code").equalTo(courseToDelete);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String keyWeWant = childSnapshot.getKey();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Courses");
                        databaseReference.child(keyWeWant).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    binding.editTextTextPersonName6.setText("");
                                    Toast.makeText(AdminDelete.this, "Successfully deleted course!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AdminDelete.this, "Failed to delete course", Toast.LENGTH_SHORT).show();


                                }
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(AdminDelete.this, "Course does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        Query querySecond = FirebaseDatabase.getInstance().getReference("Courses");
        querySecond.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    String courseKey = courseSnapshot.getKey();
                    DataSnapshot prereqsSnapshot = courseSnapshot.child("prereqs");
                    for (DataSnapshot prereqSnapshot : prereqsSnapshot.getChildren()){

                        String keyToUse = (String) prereqSnapshot.getKey();
                        String valueToUse = (String) prereqSnapshot.getValue();

                        if (valueToUse.compareTo(courseToDelete) == 0){

                            dataRef = FirebaseDatabase.getInstance().getReference("Courses");

                            dataRef.child(courseKey).child("prereqs").child(keyToUse).removeValue().addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                    }
                                    else{
                                    }


                                }
                            });

                        }


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query queryThird = FirebaseDatabase.getInstance().getReference("Users");
        queryThird.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();
                    DataSnapshot coursesTakenSnapshot = userSnapshot.child("CoursesTaken");
                    for (DataSnapshot coursesSnapshot : coursesTakenSnapshot.getChildren()){

                        String keyToUse = (String) coursesSnapshot.getKey();
                        String valueToUse = (String) coursesSnapshot.getValue();

                        if (valueToUse.compareTo(courseToDelete) == 0){

                            dataRefThird = FirebaseDatabase.getInstance().getReference("Users");
                            dataRefThird.child(userKey).child("CoursesTaken").child(keyToUse).removeValue().addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                    }
                                    else{
                                    }


                                }
                            });

                        }


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}