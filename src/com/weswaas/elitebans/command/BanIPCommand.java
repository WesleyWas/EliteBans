package com.weswaas.elitebans.command;

import com.weswaas.elitebans.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 07/11/2016.
 */
public class BanIPCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player actor = (Player)sender;
            if(actor.hasPermission("elitebans.banip")){
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

                        if(Main.getInstance().getSQLManager().isTotalBanned(uuid)){
                            actor.sendMessage(ChatColor.RED + name + " is already banned.");
                        }

                        Main.getInstance().getGUIPresets().banInventory(actor, name, uuid, first, isOnline, ip, true);
                    }else{
                        actor.sendMessage(ChatColor.RED + "You can only IP-Ban a player wich is connected.");
                    }
                }else{
                    actor.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /banip <player>");
                }
            }
        }
        return false;
    }

}
