package com.example.bloodoor.models;

import java.io.Serializable;

public class BloodAvailable implements Serializable {
    String Name;
    String O_pos;
    String O_neg;
    String A_pos;
    String A_neg;
    String B_neg;
    String B_pos;
    String AB_pos;
    String AB_neg;

    public BloodAvailable() {
    }

    public BloodAvailable(String name, String o_pos, String o_neg, String a_pos, String a_neg, String b_neg, String b_pos, String AB_pos, String AB_neg) {
        Name = name;
        O_pos = o_pos;
        O_neg = o_neg;
        A_pos = a_pos;
        A_neg = a_neg;
        B_neg = b_neg;
        B_pos = b_pos;
        this.AB_pos = AB_pos;
        this.AB_neg = AB_neg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getO_pos() {
        return O_pos;
    }

    public void setO_pos(String o_pos) {
        O_pos = o_pos;
    }

    public String getO_neg() {
        return O_neg;
    }

    public void setO_neg(String o_neg) {
        O_neg = o_neg;
    }

    public String getA_pos() {
        return A_pos;
    }

    public void setA_pos(String a_pos) {
        A_pos = a_pos;
    }

    public String getA_neg() {
        return A_neg;
    }

    public void setA_neg(String a_neg) {
        A_neg = a_neg;
    }

    public String getB_neg() {
        return B_neg;
    }

    public void setB_neg(String b_neg) {
        B_neg = b_neg;
    }

    public String getB_pos() {
        return B_pos;
    }

    public void setB_pos(String b_pos) {
        B_pos = b_pos;
    }

    public String getAB_pos() {
        return AB_pos;
    }

    public void setAB_pos(String AB_pos) {
        this.AB_pos = AB_pos;
    }

    public String getAB_neg() {
        return AB_neg;
    }

    public void setAB_neg(String AB_neg) {
        this.AB_neg = AB_neg;
    }

}