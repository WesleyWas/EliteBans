package com.weswaas.elitebans.data;

import com.weswaas.elitebans.enums.TimeUnits;

/**
 * Created by Weswas on 06/11/2016.
 */
public class Mute extends Punishment{

    public Mute(String actorName, String bannedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount) {
        super(actorName, bannedUUID, id, creation, reason, punishedName, unit, amount);
    }

}
