package com.weswaas.elitebans.listeners.events;

import com.weswaas.elitebans.enums.TimeUnits;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Weswas on 07/11/2016.
 */
public class PlayerTempIPBanEvent extends Event{

    private static final HandlerList handlers = new HandlerList();

    private String actorName;
    private String bannedUUID;
    private long creation;
    private String reason;
    private TimeUnits unit;
    private int amount;
    private long expires;

    public PlayerTempIPBanEvent(String actorName, String bannedUUID, long creation, String reason, TimeUnits unit, int amount, long expires){
        this.actorName = actorName;
        this.bannedUUID = bannedUUID;
        this.creation = creation;
        this.reason = reason;
        this.unit = unit;
        this.amount = amount;
        this.expires = expires;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getActorName(){
        return this.actorName;
    }

    public String getBannedUUID(){
        return this.bannedUUID;
    }

    public long getCreation(){
        return this.creation;
    }

    public String getReason(){
        return this.reason;
    }

    public TimeUnits getTimeUnit(){
        return this.unit;
    }

    public int getAmount(){
        return this.amount;
    }

    public long getExpires(){
        return this.expires;
    }

}
