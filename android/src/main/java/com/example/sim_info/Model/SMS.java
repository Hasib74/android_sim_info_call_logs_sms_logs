package com.example.sim_info.Model;
public class SMS {


    @Override
    public String toString() {
        return "{" +
                "body:'" + body + '\'' +
                ", isRead:'" + isRead + '\'' +
                ", sim:" + sim +
                ", date_sent:'" + date_sent + '\'' +
                ", date:'" + date + '\'' +
                ", service_center:'" + service_center + '\'' +
                ", person:'" + person + '\'' +
                '}';
    }

    String body;

    String isRead;
    int sim;
    String date_sent;
    String date;
    String service_center;

    String person;


    public SMS(String body, String isRead, int sim, String date_sent, String date, String service_center, String person) {
        this.body = body;

        this.isRead = isRead;
        this.sim = sim;
        this.date_sent = date_sent;
        this.date = date;
        this.service_center = service_center;
        this.person = person;
    }





}
