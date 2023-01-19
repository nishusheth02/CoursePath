package com.example.schedulab;

import java.util.ArrayList;
import java.util.Map;

public class SampleCourse {
    String code;
    String name;
    ArrayList<String> prereqs;
    Map<String, Boolean> sessions;

    public SampleCourse(String code, String name, ArrayList<String> prereqs, Map<String, Boolean> sessions) {
        this.code = code;
        this.name = name;
        this.prereqs = prereqs;
        this.sessions = sessions;
    }

    public SampleCourse(){}

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPrereqs() {
        return prereqs;
    }

    public Map<String, Boolean> getSessions() {
        return sessions;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrereqs(ArrayList<String> prereqs) {
        this.prereqs = prereqs;
    }

    public void setSessions(Map<String, Boolean> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                '}';
    }
}
