package com.example.schedulab;

import java.util.List;
import java.util.Map;

public class Entry {

    private String name;
    private String code;
    List<String> prereqs;
    Map<String, Boolean> sessions;

    public Entry(){

    }

    public Entry(String cName, String cCode, List<String> cP, Map<String, Boolean> session){
        name = cName;
        code = cCode;
        prereqs = cP;
        sessions = session;
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public List<String> getPrereqs() {
        return prereqs;
    }
    public void setPrereqs(List<String> prereqs) {
        this.prereqs = prereqs;
    }

    public Map<String, Boolean> getSessions() {
        return sessions;
    }
    public void setSessions(Map<String, Boolean> sessions) {
        this.sessions = sessions;
    }



}
