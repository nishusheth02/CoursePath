package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.example.schedulab.databinding.ActivityGenerateTimelineBinding;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class GenerateTimeline extends DrawerBase {


    ArrayList<Pair<String,String>> tableData = new ArrayList<Pair<String, String>>();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String currentDate=sdf.format(new Date());
    ArrayList<String> coursesTaken = new ArrayList<String>();
    DatabaseReference UserRef;
    DatabaseReference CourseRef;
    FirebaseUser fUser;

    private interface FirebaseCallback{
        void onCallback(ArrayList<String> list);
    }

    private ActivityGenerateTimelineBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenerateTimelineBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Timeline");
        setContentView(binding.getRoot());

        final RecyclerView recyclerView = findViewById(R.id.timelineView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(GenerateTimeline.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new TimelineAdapter(tableData,this));

        //String Uid = "baJghyfNtINRCrOFxN5QFVjiTSj2";

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = fUser.getUid();

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        CourseRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        Intent i=getIntent();
        Bundle bundle = i.getExtras();
        String s = bundle.getString("input");


        //String s = "CSCA08,CSCA48,CSCB63";
        ArrayList<SampleCourse> allCourses = new ArrayList<SampleCourse>();

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //coursesTaken = (ArrayList<String>) snapshot.child(Uid).child("coursesTaken").getValue();
                Map<String, Object> studentMap = (HashMap<String, Object>) snapshot.child(Uid).getValue();
                coursesTaken = (ArrayList<String>)studentMap.get("coursesTaken");
                Log.d("courseTakenSize",coursesTaken.size()+"");


            CourseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //clear old items from list to add new data
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    //getting all children from users root
                    for(DataSnapshot child : children){
                        SampleCourse newCourse = child.getValue(SampleCourse.class);
                        allCourses.add(newCourse);
                    }
                    String[] courseRequestsList = s.trim().split(",");

                    CourseRequest req =new CourseRequest(courseRequestsList, coursesTaken, allCourses);
                    ArrayList<SampleCourse> reqCourse = new ArrayList<SampleCourse>();
                    reqCourse = req.computeCoursesToTake();
                    if(reqCourse.size()>0)
                    //for(Course c: reqCourse)
                    //    Log.d("GenerateTimeline","Courses required: "+c.getCode());

                    {
                        Log.d("time: ", currentDate);
                        Log.d("reqC","Required courses: "+reqCourse.size());
                        Log.d("bundle", "starting course-session grouping");

                        tableData = bundleCourseAndSession(reqCourse, coursesTaken);
                        Log.d("bundle", "finished course-session grouping. size: " + tableData.size());
                        for (Pair pair : tableData)
                            Log.d("GenerateTableData", pair.first.toString() + " " + pair.second.toString());

                        recyclerView.setAdapter(new TimelineAdapter(tableData, GenerateTimeline.this));
                    }
                    else
                    {
                        Log.d("reqC","No Required courses: ");
                        TextView warningText = (TextView) findViewById(R.id.tvwarning);

                        warningText.setText("Courses requested are already taken");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public int getSemester(){
        int month = Integer.parseInt(currentDate.substring(0,2));
        if(month<=4) //Jan,Feb,Mar,April
            return 1;
        else if (month<=8) //May,Jun,Jul,Aug
            return 2;
        else //Sep,Oct,Nov,Dec
            return 0;
    }

    public boolean canBeTaken(SampleCourse course, ArrayList<String> coursesTaken){
        List<String> prereq = new ArrayList<String>();
        prereq = course.getPrereqs();
        String courseCode="none";
        for(String code : prereq){
            courseCode=code;
            if(code.equals("None") || code.equals("none")) {
                //Log.d("basicCourse",code+" has no prereq");
                return true;
            }
            if(!coursesTaken.contains(code)) {
                //Log.d("courseNotTaken",code+" was never taken");
                return false;
            }
        }
        //Log.d("eligibleSuccess",courseCode+" can be taken");
        return true;
    }
    // 6 courses per semester???????
    public ArrayList<Pair<String, String>> bundleCourseAndSession(ArrayList<SampleCourse> coursesWanted,ArrayList<String> coursesTaken){
        int maxCoursesPerSession=6;
        ArrayList<String> temp = new ArrayList<String>();
        temp.addAll(coursesTaken);
        int addYear=0;
        ArrayList<Pair<String,String>> courseBundle = new ArrayList<Pair<String,String>>();
        int sems = getSemester();
        while(coursesWanted.size()>0){
            Log.d("courseWantedLoop",coursesWanted.size()+""+coursesWanted.get(0));
            for(int i=0;i<3;i++){
                if(!(coursesWanted.size()>0))
                    break;
                i+=sems;
                sems=0;
                String cTaken="";
                String currentSession;
                String session;
                if(i==0){
                    currentSession="Winter ";
                    session="bWinter";
                }
                else if(i==1) {
                    currentSession = "Summer ";
                    session="cSummer";
                }
                else {
                    currentSession = "Fall ";
                    session="aFall";
                }
                int currentYear = Integer.parseInt(currentDate.substring(6));
                currentSession+=currentYear+addYear;
                Log.d("semesterLoop","i: "+i);

                Iterator itr = coursesWanted.iterator();
                ArrayList<String> toBeTemped = new ArrayList<String>();
                int coursesThisSession = 0;
                while(itr.hasNext() && coursesThisSession < maxCoursesPerSession){
                    SampleCourse course = (SampleCourse)itr.next();
                    //Log.d("coursecheck","checking: "+course.getCode());


                    boolean sessionMatched = course.getSessions().get(session);
                    boolean eligible = canBeTaken(course,temp);
                    //Log.d("sessionreport",currentSession+": "+sessionMatched);
                    //Log.d("eligibilityreport","course can be taken: "+eligible);
                    if(sessionMatched && eligible){
                        cTaken+=course.getCode()+", ";
                        toBeTemped.add(course.getCode());
                        coursesThisSession++;
                        Log.d("deletecourse","deleted "+course.getCode());
                        itr.remove();
                    }
                }
                for(String code : toBeTemped)
                    temp.add(code);
                if(cTaken.length()>3)
                    cTaken=cTaken.substring(0,cTaken.length()-2);
                Pair<String,String> pair = new Pair<String,String>(currentSession,cTaken);
                courseBundle.add(pair);
                if(i==2)
                    addYear++;
            }

            //coursesWanted.remove();
        }
        return courseBundle;
    }
}