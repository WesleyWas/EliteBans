package com.weswaas.elitebans.command;

import com.weswaas.elitebans.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Weswas on 09/11/2016.
 */
public class AltsCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(p.hasPermission("elitebans.alts")){
                if(args.length == 1){

                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);

                    ArrayList<String> found = Main.getInstance().getSQLManager().getAlts(op.getUniqueId().toString());

                    if(found.size() == 0){
                        p.sendMessage(ChatColor.RED + "No alts has logged in with " + args[0] + "'s IP.");
                        return true;
                    }

                    p.sendMessage(ChatColor.DARK_AQUA + "Possible alts founds for " + ChatColor.AQUA + op.getName() + " :");
                    for(String s : found){
                        p.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + s);
                    }

                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /alts <player>");
                }
            }
        }
        return false;
    }

}
