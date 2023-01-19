package com.example.schedulab;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    private ArrayList <Pair<String, String>> suggestions;
    private Context context;

    public TimelineAdapter(ArrayList<Pair<String, String>> suggestions, Context context){
        this.suggestions=suggestions;
        this.context = context;
    }

    @NonNull
    @Override
    public TimelineAdapter.TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimelineViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_card,null));
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.TimelineViewHolder holder, int position) {
        Pair<String, String> myPair = suggestions.get(position);

        holder.tvsession.setText(myPair.first);
        if(myPair.second.isEmpty())
        holder.tvcourse.setText("Courses: None");
        else
            holder.tvcourse.setText("Courses: "+myPair.second);


    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }
    static class TimelineViewHolder extends RecyclerView.ViewHolder {
        private TextView tvsession, tvcourse;
        public TimelineViewHolder(@NonNull View itemView) {
            super(itemView);

            tvcourse = itemView.findViewById(R.id.tvcourse);
            tvsession = itemView.findViewById(R.id.tvsession);
        }


    }
}
