package com.weswaas.elitebans.listeners.events.listener;

import com.weswaas.elitebans.listeners.events.PlayerTempBanEvent;
import com.weswaas.elitebans.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * Created by Weswas on 06/11/2016.
 */
public class PlayerTempBanListener implements Listener{

    @EventHandler
    public void onTempBan(PlayerTempBanEvent e){

        Player target = Bukkit.getPlayer(UUID.fromString(e.getBannedUUID()));

        String name;
        String reason = e.getReason();
        String actor = e.getActorName();
        String duration = e.getAmount() + " " + e.getTimeUnit().getName();
        long creation = e.getCreation();

        if(target != null && target.isOnline()){
            name = target.getName();
            target.kickPlayer(Messages.getTempBanMessage(name, reason, actor, duration, creation, e.getExpires(), false));
        }else{
            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(e.getBannedUUID()));
            name = op.getName();
        }

    }

}
