package com.weswaas.elitebans.command;

import com.weswaas.elitebans.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 09/11/2016.
 */
public class KickCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("elitebans.kick")){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null && target.isOnline()){
                        Main.getInstance().getGUIPresets().kickInventory(p, target.getName());
                    }else{
                        p.sendMessage(ChatColor.RED + args[0] + " is not online.");
                    }
                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /kick <player>");
                }
            }
        }
        return false;
    }

}
