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
 * Created by Weswas on 10/11/2016.
 */
public class IPCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(p.hasPermission("elitebans.ip")){
                if(args.length == 1){
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    String name = op.getName();
                    String uuid = op.getUniqueId().toString();

                    String ip = Main.getInstance().getSQLManager().getIP(uuid);

                    if(ip == null){
                        p.sendMessage(ChatColor.RED + name + " did never join the server.");
                        return true;
                    }

                    p.sendMessage(ChatColor.DARK_AQUA + name + "'s IP" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + ip);

                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /ip <player>");
                }
            }
        }
        return false;
    }

}
