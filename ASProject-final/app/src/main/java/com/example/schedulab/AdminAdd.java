package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulab.databinding.ActivityAdminAddBinding;
import com.example.schedulab.AdminDrawerBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAdd extends AdminDrawerBase {

    private static final String TAG = "AdminAdd";
    private static final String KEY_NAME = "name";
    private static final String KEY_CODE = "code";
    private static final String KEY_SESSIONS = "sessions";
    private static final String KEY_PREREQ = "prereqs";

    private CheckBox mFallCheck, mWinterCheck, mSummerCheck;

    private EditText editTextName, editTextCode, editTextPreReq;
    //editTextSessions, editTextPreReq;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Courses");
    private List<String> allCoursesData = new ArrayList<String>();
    private boolean exists = true;


    ActivityAdminAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Add Course");
        setContentView(binding.getRoot());



        editTextName = findViewById(R.id.course_name);
        editTextCode = findViewById(R.id.course_code);
        editTextPreReq = findViewById(R.id.course_prereq);

        mFallCheck = findViewById(R.id.check_fall);
        mWinterCheck = findViewById(R.id.check_winter);
        mSummerCheck = findViewById(R.id.check_summer);

    }

    public void saveEntry(View v){

        String name = editTextName.getText().toString();
        String code = editTextCode.getText().toString();
        String PQinput = editTextPreReq.getText().toString();

        Boolean checkFall = mFallCheck.isChecked();
        Boolean checkWinter = mWinterCheck.isChecked();
        Boolean checkSummer = mSummerCheck.isChecked();

        String[] PQarray = PQinput.split("\\s*,\\s*"); //removes empty spaces at beginning and end of each single string
        List<String> cPreReqs = Arrays.asList(PQarray);

        Map<String, Boolean> sessions = new HashMap<>();
        sessions.put("aFall", checkFall);
        sessions.put("bWinter", checkWinter);
        sessions.put("cSummer", checkSummer);

        Entry entry = new Entry();
        entry.setName(name);
        entry.setCode(code);
        entry.setPrereqs(cPreReqs);
        entry.setSessions(sessions);

        /* check if the course name or course code already exists */
        //to return after

        //doing a query
        root.orderByChild("code").equalTo(code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){ //checks if course code exists
                    Toast.makeText(AdminAdd.this, "Course Code already exists. Failed to add.", Toast.LENGTH_SHORT).show();
                }else if(code.equals("")){
                    Toast.makeText(AdminAdd.this, "Course Code has not been entered. Failed to add.", Toast.LENGTH_SHORT).show();
                }
                else{
                    //actual code goes in here
                    //also going to check if all the pqs exist in the array list to be created

                    root.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot courses : snapshot.getChildren()) {
                                Map<String, Object> coursesData = (HashMap<String, Object>) courses.getValue();
                                String name = (String) coursesData.get("code");
                                allCoursesData.add(name);
                            }
                            exists = true;
                            for (int ind = 0; ind < cPreReqs.size(); ind++) {
                                String cPQ = cPreReqs.get(ind);
                                if (allCoursesData.contains(cPQ) == false) {
                                    if(cPQ.equals("none") == false){
                                        exists = false;
                                        break;
                                    }
                                }
                            }

                            if(exists == false){//if it does not exist
                                Toast.makeText(AdminAdd.this, "One or more prerequistites does not exist. Failed to add.", Toast.LENGTH_SHORT).show();
                            }else{
                                //actual final work goes in here
                                root.push().setValue(entry)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                editTextName.setText("");
                                                editTextCode.setText("");
                                                editTextPreReq.setText("");

                                                mFallCheck.setChecked(false);
                                                mWinterCheck.setChecked(false);
                                                mSummerCheck.setChecked(false);

                                                Toast.makeText(AdminAdd.this, "Course Added", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AdminAdd.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                        }//onDataChange

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });//end of root.addValue....

                }//end inner else
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //the rest should be under an else?

    }
}