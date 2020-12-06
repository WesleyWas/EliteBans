package com.weswaas.elitebans.data;

import com.weswaas.elitebans.enums.TimeUnits;

/**
 * Created by Weswas on 06/11/2016.
 */
public class TempBan extends Ban implements Temporary{

    private long expires;

    @Override
    public long getExpires() {
        return this.expires;
    }

    @Override
    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expires;
    }

    public TempBan(String actorName, String bannedUUID, int id, long creation, long expires, String reason, String punishedName, TimeUnits unit, int amount) {
        super(actorName, bannedUUID, id, creation, reason, punishedName, unit, amount);
        this.expires = expires;
    }
}
