package com.weswaas.elitebans.data;

import com.weswaas.elitebans.enums.TimeUnits;

/**
 * Created by Weswas on 07/11/2016.
 */
public class IPBan extends Ban{

    private String ip;

    public IPBan(String actorName, String bannedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount, String ip) {
        super(actorName, bannedUUID, id, creation, reason, punishedName, unit, amount);
        this.ip = ip;
    }

    public String getIp(){
        return this.ip;
    }
}
