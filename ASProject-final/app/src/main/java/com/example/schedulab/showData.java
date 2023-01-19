package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schedulab.databinding.ActivityShowDataBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class showData extends DrawerBase {
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    private Button logout;
    ActivityShowDataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDataBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Courses Taken");
        setContentView(binding.getRoot());


        listView = (ListView) findViewById(R.id.listviewtext);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users"); //update URL!!!
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> studentMap = (HashMap<String, Object>) dataSnapshot.child(currentuser).getValue();
                arrayList.clear();

                ArrayList<String> courses = (ArrayList<String>) studentMap.get("coursesTaken");
               // Collection<Object> coursesAsObjects = coursesMap.values();
                //arrayList.clear();
                int size = courses.size();
                for(int i=0; i<size; i++){
                    arrayList.add(courses.get(i));
                }
                //ArrayList<String> courses = new ArrayList<String>();

                //for(Object obj:coursesAsObjects) {
                    //courses.add(obj.toString());
                    //arrayList.add(coursesAsObjects.toString());
                //}
                arrayAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
}