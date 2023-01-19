package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulab.databinding.ActivityAddCourseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.ArrayList;


public class AddCourse extends DrawerBase implements  View.OnClickListener {

    private String course, Uid, lastCourse, lastPre;
    private EditText courseInput;
    private Button add;
    private DatabaseReference coursesRef, userRef;

  //  private FirebaseUser fUser;
    Boolean b, b1;

    private AddCourseModel model;
    int num, num1;
    List<String> taken;
    ActivityAddCourseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Add Course");
        setContentView(binding.getRoot());
        model = new AddCourseModel();

        courseInput = (EditText) findViewById(R.id.CourseInput);

        add = (Button) findViewById(R.id.AddC);
        add.setOnClickListener(this);
     //   fUser = FirebaseAuth.getInstance().getCurrentUser();
        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Uid = "baJghyfNtINRCrOFxN5QFVjiTSj2";

    }

    //back to main page button
    public void Back(View view) {
        Intent i = new Intent(AddCourse.this, StudentDefault.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        String courseCode = courseInput.getText().toString().trim().toUpperCase();
        // get All courses
        if (courseCode.equals("")) {
            courseInput.setError("Course code is required");
            courseInput.requestFocus();
            return;
        }
        model.getCoursesByCourseCode(courseCode, (List<Course> allCourses) -> {
            if (allCourses == null) {
                courseInput.setError("Course you entered does not exist");
                courseInput.requestFocus();
                return;
            }

            Course toAdd = allCourses.get(0);
            model.getUserById(Uid, (UserAdd user) -> {
                for (int i = 1; i < allCourses.size(); i++) {
                    if (!user.coursesTaken.contains(allCourses.get(i).code)) {
                        courseInput.setError("Missing Prereqs");
                        courseInput.requestFocus();
                        return;
                    }
                }

                if (user.coursesTaken.contains(toAdd.code)) {
                    courseInput.setError("Course Already Taken");
                    courseInput.requestFocus();
                    return;
                } /*else if(user.coursesTaken.contains("None")) {
                    user.coursesTaken.remove(0);
                }*/

                user.coursesTaken.add(toAdd.code);
                model.saveUser(Uid, user, (Boolean success) -> {
                    if (success)
                        Toast.makeText(AddCourse.this, "Course added successfully", Toast.LENGTH_LONG).show();

                    else {
                        courseInput.setError("Course does not satisfies prerequisite");
                        courseInput.requestFocus();
                        return;
                    }


                });
            });
        });
    }
}
/*
                int i=0;
                for (String next: user.coursesTaken) {
                    if (next.equalsIgnoreCase("none"))
                        user.coursesTaken.remove(i);
                    i++;
                }
 */