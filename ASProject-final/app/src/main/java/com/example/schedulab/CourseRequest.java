package com.example.schedulab;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CourseRequest {
    ArrayList<SampleCourse> targetCourses = new ArrayList<SampleCourse>();
    ArrayList<SampleCourse> allCourses = new ArrayList<SampleCourse>();
    ArrayList<SampleCourse> coursesTaken = new ArrayList<SampleCourse>();;

    public CourseRequest(String[] courseRequests, ArrayList<String> coursesTaken, ArrayList<SampleCourse>
            allCourses) {
        this.allCourses = allCourses;
        Log.d("allCourses","allCourses created");
        if(allCourses.size()!=0) {
            Log.d("allCourses1", "allCourses 1:" + this.allCourses.get(0).getCode());
        }
        if(coursesTaken.size()!=0) {
            this.coursesTaken = findCourses(coursesTaken);
            //Log.d("coursesTaken", "coursesTaken 1:" + this.coursesTaken.get(0).getCode());
        }
        else
            Log.d("coursesTaken", "No courses Taken");
        ArrayList<String> reqArr = new ArrayList<>(Arrays.asList(courseRequests));
        //Log.d("targetCourses","reqArr 1:"+reqArr.get(0));
        this.targetCourses = findCourses(reqArr);
        //Log.d("targetCourses","targetCourse 1:"+targetCourses.get(0).getCode());


    }
    public ArrayList<SampleCourse> computeCoursesToTake()
    {
        //Log.d("compute","computing courses to take");
        ArrayList<SampleCourse> courses = new ArrayList<SampleCourse>();
        courses=findReqPrereqs(targetCourses,new ArrayList<SampleCourse>());
        return courses;
    }

    public ArrayList<SampleCourse> findReqPrereqs(ArrayList<SampleCourse> requestedCourses, ArrayList<SampleCourse>
            foundCourses)
    {
        //Log.d("prereq","searching prereq");
        ArrayList<SampleCourse> prereqCourses = new ArrayList<SampleCourse>();
        for(SampleCourse course : requestedCourses){
            SampleCourse theCourse = course;
            while(!coursesTaken.contains(theCourse) //&& theCourse.getPrereqs().get(0).equals("none")
                    && !foundCourses.contains(theCourse)){
                //Log.d("prereq","searching prereqs for "+theCourse.getCode());
                prereqCourses=findCourses(theCourse.getPrereqs());
                foundCourses=findReqPrereqs(prereqCourses, foundCourses);
                foundCourses.add(theCourse);
                //Log.d("add","adding "+theCourse.getCode());
            }
            //Log.d("prereq","finished searching for prereqs of "+theCourse.getCode());
        }
        //Log.d("prereq","requested courses found");
        return foundCourses;
    }

    public ArrayList<SampleCourse> findCourses(ArrayList<String> courseRequests) {
        ArrayList<SampleCourse> foundCourses = new ArrayList<SampleCourse>();
        for (String code: courseRequests) {
            for (SampleCourse aCourse : allCourses) {
                if (aCourse.getCode().equals(code)){
                    foundCourses.add(aCourse);
                    break;
                }
            }
        }
        return foundCourses;
    }
}
