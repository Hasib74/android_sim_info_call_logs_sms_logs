package com.example.sim_info.Model;

import java.util.List;

public class SmsModel {

    List<SMS> sms ;

    public SmsModel(List<SMS> sms) {
        this.sms = sms;
    }

    @Override
    public String toString() {
        return "{" +
                "data:" + sms +
                '}';
    }



}
