package com.example.sim_info;
public class SubscriptionId {

    int simSlot;

    public SubscriptionId(int simSlot, int subsCriptionId) {
        this.simSlot = simSlot;
        this.subsCriptionId = subsCriptionId;
    }

    int subsCriptionId;

    SubscriptionId() {
    }

    @Override
    public String toString() {
        return "{" +
                "simSlot:" + simSlot +
                ", subSubscriptionId:" + subsCriptionId +
                '}';
    }
}
