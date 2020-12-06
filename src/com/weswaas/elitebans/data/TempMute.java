package com.weswaas.elitebans.data;

import com.weswaas.elitebans.enums.TimeUnits;

/**
 * Created by Weswas on 06/11/2016.
 */
public class TempMute extends Mute implements Temporary{

    private long expires;

    @Override
    public long getExpires() {
        return this.expires;
    }

    @Override
    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expires;
    }

    public TempMute(String actorName, String bannedUUID, int id, long creation, long expires, String reason, String bannedName, TimeUnits unit, int amount) {
        super(actorName, bannedUUID, id, creation, reason, bannedName, unit, amount);
        this.expires = expires;
    }
}
