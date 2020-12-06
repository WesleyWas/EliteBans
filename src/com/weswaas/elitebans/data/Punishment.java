package com.weswaas.elitebans.data;

import com.weswaas.elitebans.enums.TimeUnits;

/**
 * Created by Weswas on 06/11/2016.
 */
public class Punishment {

    String punishedUUID;
    String punishedName;
    String actorName;
    int id;
    long creation;
    String reason;
    TimeUnits unit;
    int amount;

    public Punishment(String actorName, String punishedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount){
        this.punishedUUID = punishedUUID;
        this.actorName = actorName;
        this.id = id;
        this.creation = creation;
        this.reason = reason;
        this.punishedName = punishedName;
        this.unit = unit;
        this.amount = amount;
    }

    public String getPunishedUUID(){
        return this.punishedUUID;
    }

    public String getActorName(){
        return this.actorName;
    }

    public int getId(){
        return this.id;
    }

    public long getCreation(){
        return creation;
    }

    public String getReason(){
        return this.reason;
    }

    public String getPunishedName(){
        return this.punishedName;
    }

    public TimeUnits getTimeUnit(){
        return this.unit;
    }

    public int getAmount(){
        return this.amount;
    }

}
