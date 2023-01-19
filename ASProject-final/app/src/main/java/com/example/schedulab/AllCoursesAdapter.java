package com.example.schedulab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class AllCoursesAdapter extends RecyclerView.Adapter<AllCoursesAdapter.AllViewHolder> {

    /* they make type Entry fields final
    * final is supposed to just make sure the values cannot be changed
    * so i ignored for now */

    private List<Entry> entries; //entries array list
    private Context context; //context

    //constructor
    public AllCoursesAdapter(List<Entry> entries, Context context) {
        this.entries = entries;
        this.context = context;
    }

    @NonNull
    @Override
    public AllCoursesAdapter.AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adpater_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AllCoursesAdapter.AllViewHolder holder, int position) {

        //getting single item / user details from List
        Entry myEntries = entries.get(position);

        //setting user details to TextViews
        holder.courseName.setText(myEntries.getName());
        holder.courseCode.setText(myEntries.getCode());

        String displaySessions = "";
        Map<String, Boolean> sessInput = myEntries.getSessions();
        int map_size = sessInput.size();
        int i = 0;
        for(Map.Entry<String, Boolean> pair: sessInput.entrySet()){
            if (pair.getValue() == true){
                if(i == 0){
                    displaySessions = pair.getKey();
                }else{
                    displaySessions = displaySessions + ", " + pair.getKey();
                }

                i++;
            }
        }

        //displaySessions = "Sessions Offered: " + displaySessions;

        holder.courseSessions.setText(displaySessions);

        String displayPrereq = "";
        List<String> preInput = myEntries.getPrereqs();
        for(int c = 0; c < preInput.size(); c++){
            if(c == 0)
                displayPrereq = preInput.get(c);
            if(c != 0)
                displayPrereq = displayPrereq + ",  " + preInput.get(c);
        }

        //displayPrereq = "Prerequisites: " + displayPrereq;

        holder.coursePrereq.setText(displayPrereq);


    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    //AllViewHolder class to hold view reference for every item in the RecyclerView
    static class AllViewHolder extends RecyclerView.ViewHolder {

        private TextView courseName, courseCode, courseSessions, coursePrereq;

        public AllViewHolder(@NonNull View itemView) {
            super(itemView);

            //getting textviews from out adapter layout file
            courseName = itemView.findViewById(R.id.courseName);
            courseCode = itemView.findViewById(R.id.courseCode);
            courseSessions = itemView.findViewById(R.id.courseSessions);
            coursePrereq = itemView.findViewById(R.id.coursePrereq);

        }
    }
}
