package com.netanel.todolist.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer serialnumber;
    private int userid;
    private String task;

    public Item() {

    }

    public Item(int userid, String task) {
        this.userid = userid;
        this.task = task;
    }

    public Integer getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(Integer serialnumber) {
        this.serialnumber = serialnumber;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Item{" +
                "serialnumber=" + serialnumber +
                ", userid=" + userid +
                ", task='" + task + '\'' +
                '}';
    }
}
