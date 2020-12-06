package com.weswaas.elitebans.listeners.events.listener;

import com.weswaas.elitebans.listeners.events.PlayerTempIPBanEvent;
import com.weswaas.elitebans.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * Created by Weswas on 07/11/2016.
 */
public class PlayerTempIPBanListener implements Listener{

    @EventHandler
    public void onTempIPBan(PlayerTempIPBanEvent e){

        Player target = Bukkit.getPlayer(UUID.fromString(e.getBannedUUID()));

        String name;
        String reason = e.getReason();
        String actor = e.getActorName();
        long creation = e.getCreation();
        long expires = e.getExpires();
        String duration = e.getAmount() + " " + e.getTimeUnit().getName();

        if(target != null && target.isOnline()){
            name = target.getName();
            target.kickPlayer(Messages.getTempBanMessage(name, reason, actor, duration, creation, expires, true));
        }else{
            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(e.getBannedUUID()));
            name = op.getName();
        }

    }

}
