package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schedulab.databinding.ActivityAllCoursesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCourses extends AdminDrawerBase {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Courses");
    private List<Entry> myEntriesList = new ArrayList<>();

    ActivityAllCoursesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCoursesBinding.inflate(getLayoutInflater());
        allocateActivityTitle("All courses");
        setContentView(binding.getRoot());


        /*remaining code goes here*/

        final RecyclerView recyclerView = findViewById(R.id.courseList);

        //setting recyclerview size fixed for every item
        recyclerView.setHasFixedSize(true);
        //setting layout manager to the recycler view
        LinearLayoutManager llm =  new LinearLayoutManager(AllCourses.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new AllCoursesAdapter(myEntriesList, this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear old items from list to add new data
                myEntriesList.clear();
                //getting all children from users root
                for(DataSnapshot courses :snapshot.getChildren()){
                    Map<String, Object> coursesMap = (HashMap<String, Object>) courses.getValue();
                    String name = (String)coursesMap.get("name");
                    String code = (String)coursesMap.get("code");


                    //Map<String, Object> prereqMap = (HashMap<String, Object>) coursesMap.get("prereqs");
                    ArrayList<String> prereqs = (ArrayList<String>) coursesMap.get("prereqs");
                    //cannot type cast above from arrayList into a HashMap

                    Map<String, Object> sessMap = (HashMap<String, Object>) coursesMap.get("sessions");
                    //Collection<String> sessAsObjects = sessMap.keySet();
                    Collection<Object> VsessAsObjects = sessMap.values();
                    HashMap<String, Boolean> session = new HashMap<String, Boolean>();

                    //setting the session hashmap
                    session.put("Fall",Boolean.parseBoolean((VsessAsObjects.toArray()[0]).toString()));
                    session.put("Winter",Boolean.parseBoolean((VsessAsObjects.toArray()[1]).toString()));
                    session.put("Summer",Boolean.parseBoolean((VsessAsObjects.toArray()[2]).toString()));

                    Entry myEntries = new Entry();
                    myEntries.setName(name);
                    myEntries.setCode(code);
                    myEntries.setSessions(session);
                    myEntries.setPrereqs(prereqs);
                    //adding this entry to list
                    myEntriesList.add(myEntries);

                }

                //after adding all courses to the list, we set adapter to RecyclerView
                recyclerView.setAdapter(new AllCoursesAdapter(myEntriesList, AllCourses.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //this is code without adapter


    }
}