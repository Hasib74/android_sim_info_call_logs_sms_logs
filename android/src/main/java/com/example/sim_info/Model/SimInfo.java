package com.example.sim_info;
import java.util.List;

public class SimInfo {

    List<SubscriptionId> subscriptionIds ;

    public List<SubscriptionId> getSubscriptionIds() {
        return subscriptionIds;
    }

    public void setSubscriptionIds(List<SubscriptionId> subscriptionIds) {
        this.subscriptionIds = subscriptionIds;
    }

    public List<IccId> getIccIds() {
        return iccIds;
    }

    public void setIccIds(List<IccId> iccIds) {
        this.iccIds = iccIds;
    }

    List<IccId> iccIds ;


    public  SimInfo(){

    }

    public SimInfo(List<SubscriptionId> subscriptionIds, List<IccId> iccIds) {
        this.subscriptionIds = subscriptionIds;
        this.iccIds = iccIds;
    }


    @Override
    public String toString() {
        return "{" +
                "subscriptionIds:" + subscriptionIds +
                ", iccIds:" + iccIds +
                '}';
    }
}


