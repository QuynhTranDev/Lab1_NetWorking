package com.example.testing;

import java.util.HashMap;

public class ToDo {
    String id;
    String title;
    String des;

    public ToDo() {
    }
    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("descriptions", des);
        return map;
    }
    public ToDo(String id, String title, String des) {
        this.id = id;
        this.title = title;
        this.des = des;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }



}
