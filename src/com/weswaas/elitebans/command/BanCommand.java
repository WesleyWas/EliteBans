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
 * Created by Weswas on 06/11/2016.
 */
public class BanCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player actor = (Player)sender;
            if(actor.hasPermission("elitebans.ban")){
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
                        ip = target.getAddress().getHostName();
                    }else{
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        uuid = op.getUniqueId().toString();
                        name = op.getName();
                        first = op.getFirstPlayed();
                        isOnline = false;
                        ip = "Not Findeable";
                    }

                    if(Main.getInstance().getSQLManager().isTotalBanned(uuid)){
                        actor.sendMessage(ChatColor.RED + name + " is already banned.");
                        return true;
                    }

                    Main.getInstance().getGUIPresets().banInventory(actor, name, uuid, first, isOnline, ip, false);
                }else{
                    actor.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /ban <player>");
                }
            }
        }
        return false;
    }
}
