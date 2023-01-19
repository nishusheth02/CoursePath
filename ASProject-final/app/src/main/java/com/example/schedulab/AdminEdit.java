package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulab.databinding.ActivityAdminEditBinding;
import com.example.schedulab.AdminDrawerBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminEdit extends AdminDrawerBase {
    ActivityAdminEditBinding binding;
    DatabaseReference databaseReference;
    DatabaseReference dataRef;
    DatabaseReference dataRefs;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_admin_edit);
        binding = ActivityAdminEditBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Edit Course");
        setContentView(binding.getRoot());

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseToEdit = binding.editTextTextPersonName.getText().toString();
                String newName = binding.editTextTextPersonName2.getText().toString();
                String newCode = binding.editTextTextPersonName3.getText().toString();
                CheckBox fall = binding.checkBox;
                CheckBox winter = binding.checkBox2;
                CheckBox summer = binding.checkBox3;
                String newPrereqs = binding.editTextTextPersonName5.getText().toString();

                editCourse(courseToEdit, newName, newCode, fall, winter, summer, newPrereqs);
            }
        });


    }



    private void editCourse(String courseToEdit, String newName, String newCode, CheckBox fall, CheckBox winter, CheckBox summer, String newPrereqs) {
        HashMap User = new HashMap();
        User.put("code", newCode);
        User.put("name", newName);
        String prereqs_list[] = newPrereqs.split("\\s*,\\s*");

        Query query = FirebaseDatabase.getInstance().getReference("Courses").orderByChild("code").equalTo(courseToEdit);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String keyWeWant = childSnapshot.getKey();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Courses");
                        if (prereqs_list.length == 1){
                            databaseReference.child(keyWeWant).child("prereqs").child("0").setValue(prereqs_list[0]);
                            long x = childSnapshot.child("prereqs").getChildrenCount();
                            if (x > 1){
                                while (x != 1){
                                    databaseReference.child(keyWeWant).child("prereqs").child(String.valueOf(x-1)).removeValue();
                                    x--;
                                }
                            }

                        }
                        else{
                            for (i = 0; i < prereqs_list.length; i++) {
                                databaseReference.child(keyWeWant).child("prereqs").child(String.valueOf(i)).setValue(prereqs_list[i]);

                            }
                            long y = childSnapshot.child("prereqs").getChildrenCount();
                            if (y > prereqs_list.length){
                                while (y != prereqs_list.length){
                                    databaseReference.child(keyWeWant).child("prereqs").child(String.valueOf(y-1)).removeValue();
                                    y--;
                                }

                            }

                        }

                        if (fall.isChecked()) {
                            databaseReference.child(keyWeWant).child("sessions").child("aFall").setValue(true);
                        } else if (!(fall.isChecked())) {
                            databaseReference.child(keyWeWant).child("sessions").child("aFall").setValue(false);
                        }
                        if (summer.isChecked()) {
                            databaseReference.child(keyWeWant).child("sessions").child("cSummer").setValue(true);
                        } else if (!(summer.isChecked())) {
                            databaseReference.child(keyWeWant).child("sessions").child("cSummer").setValue(false);
                        }
                        if (winter.isChecked()) {
                            databaseReference.child(keyWeWant).child("sessions").child("bWinter").setValue(true);
                        } else if (!(winter.isChecked())) {
                            databaseReference.child(keyWeWant).child("sessions").child("bWinter").setValue(false);
                        }


                        databaseReference.child(keyWeWant).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    binding.editTextTextPersonName.setText("");
                                    binding.editTextTextPersonName2.setText("");
                                    binding.editTextTextPersonName3.setText("");
                                    binding.editTextTextPersonName5.setText("");
                                    binding.checkBox.setChecked(false);
                                    binding.checkBox2.setChecked(false);
                                    binding.checkBox3.setChecked(false);
                                    Toast.makeText(AdminEdit.this, "Successfully edited course!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AdminEdit.this, "Failed to edit course", Toast.LENGTH_SHORT).show();

                                }


                            }
                        });


                    }
                }
                else{
                    Toast.makeText(AdminEdit.this, "Course does not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Query querySecond = FirebaseDatabase.getInstance().getReference("Users");
        querySecond.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();
                    DataSnapshot coursesTakenSnapshot = userSnapshot.child("coursesTaken");
                    for (DataSnapshot coursesSnapshot : coursesTakenSnapshot.getChildren()){

                        String keyToUse = (String) coursesSnapshot.getKey();
                        String valueToUse = (String) coursesSnapshot.getValue();

                        if (valueToUse.compareTo(courseToEdit) == 0){

                            dataRef = FirebaseDatabase.getInstance().getReference("Users");

                            dataRef.child(userKey).child("coursesTaken").child(keyToUse).setValue(newCode).addOnCompleteListener(new OnCompleteListener() {
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

        Query queryThird = FirebaseDatabase.getInstance().getReference("Courses");
        queryThird.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();
                    DataSnapshot coursesTakenSnapshot = userSnapshot.child("prereqs");
                    for (DataSnapshot coursesSnapshot : coursesTakenSnapshot.getChildren()){

                        String keyToUse = (String) coursesSnapshot.getKey();
                        String valueToUse = (String) coursesSnapshot.getValue();

                        if (valueToUse.compareTo(courseToEdit) == 0){

                            dataRefs = FirebaseDatabase.getInstance().getReference("Courses");

                            dataRefs.child(userKey).child("prereqs").child(keyToUse).setValue(newCode).addOnCompleteListener(new OnCompleteListener() {
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