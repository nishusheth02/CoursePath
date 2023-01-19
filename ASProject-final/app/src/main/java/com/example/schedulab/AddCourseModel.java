package com.example.schedulab;


import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.example.schedulab.UserAdd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class AddCourseModel {
    DatabaseReference coursesRef;
    DatabaseReference usersRef;

    public AddCourseModel() {
        coursesRef = FirebaseDatabase.getInstance().getReference("Courses");
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    /*
        Return a list of ALL the courses from firebase
     */
    public void getCoursesByCourseCode(String courseCode, Consumer<List<com.example.schedulab.Course>> callback) {

        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, com.example.schedulab.Course> allCourses = new HashMap<>();
                Boolean has_this_course = false;
                for (DataSnapshot courseSnapShot: snapshot.getChildren()) {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
                    System.out.println(courseSnapShot);
                    com.example.schedulab.Course course = courseSnapShot.getValue(com.example.schedulab.Course.class);
                    allCourses.put(course.code, course);
                    if (course.code.equals(courseCode))
                        has_this_course = true;
                }

                if (!has_this_course) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        callback.accept(null);
                    return;
                }



                List<com.example.schedulab.Course> result = new ArrayList<>();

                Queue<String> q = new LinkedList<>();
                String curr = courseCode;

                q.offer(curr);
                while (!q.isEmpty()) {
                    curr = q.poll();
                    com.example.schedulab.Course course = allCourses.get(curr);
                    result.add(course);
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
                    System.out.println(course);
                    if (course!=null) {
                        for (String next : course.prereqs) {
                            if (!next.equalsIgnoreCase("none"))
                                q.offer(next);
                        }
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    callback.accept(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void getUserById(String uid, Consumer<UserAdd> callback) {
        System.out.println("#################");
        System.out.println(uid);
        usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println(snapshot);
                UserAdd user = snapshot.getValue(UserAdd.class);
                int i=0;
                for (String next: user.coursesTaken) {
                    if (next.equalsIgnoreCase("none"))
                        user.coursesTaken.remove(i);
                    i++;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    callback.accept(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    public void saveUser(String uid, UserAdd user, Consumer<Boolean> callback) {
        usersRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    callback.accept(task.isSuccessful());
            }
        });
    }
}
