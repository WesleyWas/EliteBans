package com.weswaas.elitebans.listeners.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Weswas on 07/11/2016.
 */
public class PlayerIPBanEvent extends Event{

    private static final HandlerList handlers = new HandlerList();

    private String actorName;
    private String bannedUUID;
    private long creation;
    private String reason;

    public PlayerIPBanEvent(String actorName, String bannedUUID, long creation, String reason){
        this.actorName = actorName;
        this.bannedUUID = bannedUUID;
        this.creation = creation;
        this.reason = reason;
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

}
