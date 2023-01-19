package com.example.schedulab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class Course implements Serializable {

    public String code;
    public String name;
    public List<String> prereqs;
    public Map<String, Boolean> sessions;

    public Course() {
        this.prereqs = new ArrayList<>();
        this.sessions = new HashMap<>();
    }
}