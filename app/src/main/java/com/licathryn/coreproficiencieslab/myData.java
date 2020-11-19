package com.licathryn.coreproficiencieslab;

import java.io.Serializable;

public class myData implements Serializable {
    public String gender;
    public int votes;

    public myData(String g, int n) {
        gender = g;
        votes = n;
    }
}
