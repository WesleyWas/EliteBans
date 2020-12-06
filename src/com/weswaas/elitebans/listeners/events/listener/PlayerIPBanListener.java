package com.weswaas.elitebans.listeners.events.listener;

import com.weswaas.elitebans.listeners.events.PlayerIPBanEvent;
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
public class PlayerIPBanListener implements Listener{

    @EventHandler
    public void onIPBan(PlayerIPBanEvent e){

        Player target = Bukkit.getPlayer(UUID.fromString(e.getBannedUUID()));

        String name;
        String reason = e.getReason();
        String actor = e.getActorName();
        long creation = e.getCreation();

        if(target != null && target.isOnline()){
            name = target.getName();
            target.kickPlayer(Messages.getPermBanMessage(name, reason, actor, creation, true));
        }else{
            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(e.getBannedUUID()));
            name = op.getName();
        }
    }

}
