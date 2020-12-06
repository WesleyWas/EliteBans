package com.weswaas.elitebans.managers;

import com.weswaas.elitebans.data.Ban;
import com.weswaas.elitebans.data.Mute;
import com.weswaas.elitebans.data.TempBan;
import com.weswaas.elitebans.data.TempMute;
import com.weswaas.elitebans.enums.TimeUnits;
import com.weswaas.elitebans.listeners.events.PlayerBanEvent;
import com.weswaas.elitebans.listeners.events.PlayerIPBanEvent;
import com.weswaas.elitebans.listeners.events.PlayerTempBanEvent;
import com.weswaas.elitebans.listeners.events.PlayerTempIPBanEvent;
import com.weswaas.elitebans.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Weswas on 06/11/2016.
 */
public class BanManager {

    public HashMap<String, Ban> bans = new HashMap<>();
    public HashMap<String, TempBan> tempbans = new HashMap<>();
    public HashMap<String, Mute> mutes = new HashMap<>();
    public HashMap<String, TempMute> tempmutes = new HashMap<>();

    private SQLManager sql;

    public BanManager(SQLManager sql){
        this.sql = sql;
    }

    public void ban(String actorName, String bannedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount, boolean silent){
        Ban ban = new Ban(actorName, bannedUUID, id, creation, reason, punishedName, unit, amount);
        this.bans.put(bannedUUID, ban);
        sql.storeBan(actorName, bannedUUID, id, creation, reason, punishedName, unit, amount);
        Bukkit.getServer().getPluginManager().callEvent(new PlayerBanEvent(actorName, bannedUUID, creation, reason));
        if(!silent){
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + punishedName + ChatColor.AQUA + " has been permanently banned for " + ChatColor.DARK_AQUA + reason + ".");
        }
        sql.incrementBans(bannedUUID);
    }
    public void tempBan(String actorName, String bannedUUID, int id, long creation, long expires, String reason, String punishedName, TimeUnits unit, int amount, boolean silent){
        TempBan tempban = new TempBan(actorName, bannedUUID, id, creation, expires, reason, punishedName, unit, amount);
        this.tempbans.put(bannedUUID, tempban);
        sql.storeTempBan(actorName, bannedUUID, id, creation, expires, reason, punishedName, unit, amount);
        Bukkit.getServer().getPluginManager().callEvent(new PlayerTempBanEvent(actorName, bannedUUID, creation, reason, unit, amount, expires));
        if(!silent){
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + punishedName + ChatColor.AQUA + " has been temporarily banned for " + ChatColor.DARK_AQUA + reason + ChatColor.AQUA + " for a duration of " + ChatColor.DARK_AQUA + amount + " " + unit.getName());
        }
        sql.incrementBans(bannedUUID);
    }


    public void IPBan(String actorName, String bannedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount, boolean silent, String ip){
        sql.storeIPBan(actorName, ip, id, creation, reason, punishedName, unit, amount, bannedUUID);
        Bukkit.getServer().getPluginManager().callEvent(new PlayerIPBanEvent(actorName, bannedUUID, creation, reason));
        if(!silent){
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + punishedName + ChatColor.AQUA + " has been permanently IP-Banned for " + ChatColor.DARK_AQUA + reason + ".");
        }
        sql.incrementBans(bannedUUID);
    }

    public void tempIPBan(String actorName, String bannedUUID, int id, long creation, long expires, String reason, String punishedName, TimeUnits unit, int amount, boolean silent, String ip){
        sql.storeTempIPBan(actorName, ip, id, creation, expires, reason, punishedName, unit, amount, bannedUUID);
        Bukkit.getServer().getPluginManager().callEvent(new PlayerTempIPBanEvent(actorName, bannedUUID, creation, reason, unit, amount, expires));
        if(!silent){
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + punishedName + ChatColor.AQUA + " has been temporarily IP-Banned for " + ChatColor.DARK_AQUA + reason + ChatColor.AQUA + " for a duration of " + ChatColor.DARK_AQUA + amount + " " + unit.getName());
        }
        sql.incrementBans(bannedUUID);
    }

    public void mute(String actorName, String bannedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount, boolean silent){
        Mute mute = new Mute(actorName, bannedUUID, id, creation, reason, punishedName, unit, amount);
        this.mutes.put(bannedUUID, mute);
        sql.storeMute(actorName, bannedUUID, id, creation, reason, punishedName, unit, amount);
        if(!silent){
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + punishedName + ChatColor.AQUA + " has been permanently muted for " + ChatColor.DARK_AQUA + reason + ChatColor.AQUA);
        }
        sql.incrementMutes(bannedUUID);
    }

    public void tempMute(String actorName, String bannedUUID, int id, long creation, long expires, String reason, String punishedName, TimeUnits unit, int amount, boolean silent){
        TempMute tempmute = new TempMute(actorName, bannedUUID, id, creation, expires, reason, punishedName, unit, amount);
        this.tempmutes.put(bannedUUID, tempmute);
        sql.storeTempMute(actorName, bannedUUID, id, creation, expires, reason, punishedName, unit, amount);
        if(!silent){
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + punishedName + ChatColor.AQUA + " has been temporarily muted for " + ChatColor.DARK_AQUA + reason + ChatColor.AQUA + " for a duration of " + ChatColor.DARK_AQUA + amount + " " + unit.getName());
        }
        sql.incrementMutes(bannedUUID);
    }

    public void unban(String uuid){

        boolean wasPermBanned = sql.isPermBanned(uuid);
        boolean wasIPPermBanned = sql.isPermIPBannedAnywaysBanned(uuid);
        boolean wasIPBanned = sql.isIPBanned(uuid, true) || sql.isIPBanned(uuid, false);
        if(wasIPBanned){
            sql.unbanIP(uuid, wasIPPermBanned);
        }else{
            sql.unban(uuid, wasPermBanned);
        }
    }

    public boolean hasDoubleBan(String uuid){
        ArrayList<String> altsName = sql.getAlts(uuid);
        ArrayList<String> altsUUID = new ArrayList<>();

        if(altsName.size() > 1){
            for(String s : altsName){
                String uuid2 = Bukkit.getOfflinePlayer(s).getUniqueId().toString();
                altsUUID.add(uuid2);
            }
            for(String alts : altsUUID){
                if(sql.isTotalBanned(alts)){
                    return true;
                }
            }
        }
        return false;
    }

    public void unMute(String uuid){
        boolean perm = sql.isPermMuted(uuid);
        sql.unMute(uuid, perm);
    }

    public void unMuteIn(Mute oldMute, TimeUnits newUnit, int newAmount){
        unMute(oldMute.getPunishedUUID());
        long expires = System.currentTimeMillis() + (newUnit.getDuration() * newAmount);
        tempMute(oldMute.getActorName(), oldMute.getPunishedUUID(), oldMute.getId(), System.currentTimeMillis(), expires, oldMute.getReason(), oldMute.getPunishedName(), newUnit, newAmount, true);
    }

    public void unbanIn(Ban oldBan, TimeUnits newUnit, int newAmount){
        unban(oldBan.getPunishedUUID());
        long expires = System.currentTimeMillis() + (newUnit.getDuration() * newAmount);
        tempBan(oldBan.getActorName(), oldBan.getPunishedUUID(), oldBan.getId(), System.currentTimeMillis(), expires, oldBan.getReason(), oldBan.getPunishedName(), newUnit, newAmount, true);
    }

    public boolean isBannedInMaps(String uuid){
        if(this.bans.containsKey(uuid) || this.tempbans.containsKey(uuid)){
            return true;
        }
        return false;
    }

    public boolean isMutedInSQL(String uuid){
        return sql.isMuted(uuid);
    }

    public boolean isMutedInMaps(String uuid){
        if(this.mutes.containsKey(uuid) || this.tempmutes.containsKey(uuid)){
            return true;
        }
        return false;
    }

}
