package com.weswaas.elitebans.listeners;

import com.weswaas.elitebans.Main;
import com.weswaas.elitebans.data.Mute;
import com.weswaas.elitebans.data.TempMute;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Weswas on 09/11/2016.
 */
public class ChatListener implements Listener{

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){

        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        if(Main.getInstance().getBanManager().isMutedInMaps(uuid)){
            boolean perm = Main.getInstance().getBanManager().mutes.containsKey(uuid);
            if(perm){
                Mute mute = Main.getInstance().getBanManager().mutes.get(uuid);
                disallowPerm(e, mute);
            }else{
                TempMute mute = Main.getInstance().getBanManager().tempmutes.get(uuid);
                if(!mute.hasExpired()){
                    disallowTemp(e, mute);
                    Main.getInstance().getBanManager().mutes.remove(uuid);
                }else{
                    Main.getInstance().getSQLManager().unMute(uuid, false);
                }
            }
        }else if(Main.getInstance().getBanManager().isMutedInSQL(uuid)){
            if(Main.getInstance().getSQLManager().isPermMuted(uuid)){
                Mute mute = Main.getInstance().getSQLManager().getMute(uuid);
                disallowPerm(e, mute);
                Main.getInstance().getBanManager().mutes.put(uuid, mute);
            }else{
                TempMute mute = Main.getInstance().getSQLManager().getTempMute(uuid);
                if(!mute.hasExpired()){
                    disallowTemp(e, mute);
                    Main.getInstance().getBanManager().tempmutes.put(uuid, mute);
                }else{
                    Main.getInstance().getSQLManager().unMute(uuid, false);
                }
            }
        }

    }

    private void disallowPerm(AsyncPlayerChatEvent e, Mute mute){
        e.setCancelled(true);

        e.getPlayer().sendMessage(ChatColor.RED + "You are currently muted for " + mute.getReason() + " for an infinite duration.");

    }

    private void disallowTemp(AsyncPlayerChatEvent e, TempMute mute){
        e.setCancelled(true);

        String duration = mute.getAmount() + " " + mute.getTimeUnit().getName();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String end = sdf.format(new Date(mute.getExpires()));

        e.getPlayer().sendMessage(ChatColor.RED + "You are currently muted for " + mute.getReason() + " for a duration of " + duration + " (expires: " + end + ")");

    }

}
