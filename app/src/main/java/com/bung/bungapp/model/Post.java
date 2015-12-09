package com.bung.bungapp.model;

import java.util.Date;

public class Post {
    /**
     * time : 2015-11-14T02:27:25.976Z
     * userid : 1
     * word : aaaaaaaaaaaaaaaaaaaaaa
     */

    private int userid;
    private String word;
    private Date time;

    public void setTime(Date time) {
        this.time = time;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getTime() {
        return time;
    }

    public int getUserid() {
        return userid;
    }

    public String getWord() {
        return word;
    }
}
