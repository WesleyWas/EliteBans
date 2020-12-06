package com.weswaas.elitebans.command;

import com.weswaas.elitebans.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 09/11/2016.
 */
public class MuteCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("elitebans.mute")){
                if(args.length == 1){
                    String uuid;
                    String name;
                    long first;
                    boolean isOnline;
                    String ip;

                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null && target.isOnline()){
                        uuid = target.getUniqueId().toString();
                        name = target.getName();
                        first = target.getFirstPlayed();
                        isOnline = true;
                        ip =  target.getAddress().getHostName();
                    }else{
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        uuid = op.getUniqueId().toString();
                        name = op.getName();
                        first = op.getFirstPlayed();
                        isOnline = false;
                        ip = "Not Findeable";
                    }

                    if(Main.getInstance().getBanManager().isMutedInMaps(uuid) || Main.getInstance().getBanManager().isMutedInSQL(uuid)){
                        p.sendMessage(ChatColor.RED + name + " is already muted.");
                        return true;
                    }

                    Main.getInstance().getGUIPresets().muteInventory(p, name, uuid, first, isOnline, ip);
                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /mute <player>");
                }
            }
        }
        return false;
    }

}
