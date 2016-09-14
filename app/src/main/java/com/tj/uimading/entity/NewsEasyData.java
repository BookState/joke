package com.tj.uimading.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/1.
 */
public class NewsEasyData {

    private ArrayList<subData> tList;

    public NewsEasyData(ArrayList<subData> tList) {
        this.tList = tList;
    }

    public ArrayList<subData> gettList() {
        return tList;
    }

    public void settList(ArrayList<subData> tList) {
        this.tList = tList;
    }

    public static class subData implements Serializable {
        private  String tname;
        private String tid;

        public subData(String tname, String tid) {
            this.tname = tname;
            this.tid = tid;
        }

        public  String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
