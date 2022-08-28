package com.example.sim_sms_call_info;

public class IccId {
    int simSlot;

    //String number ;
    String iccId;

    public IccId(int i, String id /*, String number*/) {
        this.simSlot = i ;
        this.iccId = id ;

       // this.number = number ;


    }

    @Override
    public String toString() {
        return "{" +
                "simSlot:" + simSlot +
                ", iccId:" + iccId +
                '}';
    }
}
