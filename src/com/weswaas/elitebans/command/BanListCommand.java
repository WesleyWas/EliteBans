package com.weswaas.elitebans.command;

import com.weswaas.elitebans.Main;
import com.weswaas.elitebans.sql.SQLManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Weswas on 09/11/2016.
 */
public class BanListCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(p.hasPermission("elitebans.banlist")){
                if(args.length == 0){

                    SQLManager sql = Main.getInstance().getSQLManager();

                    ArrayList<String> totalA = sql.getTotalBannedNames();
                    int total = totalA.size();

                    StringBuilder list = new StringBuilder("");
                    int i = 1;

                    for(String s : totalA){
                        if(list.length() > 0){
                            if(i == totalA.size()){
                                list.append(" and ");
                            }else{
                                list.append(", ");
                            }
                        }

                        list.append(s);
                        i++;
                    }

                    p.sendMessage(ChatColor.DARK_AQUA + "List of all banned players (" + total + ") :");
                    p.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + list.toString().trim());

                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /banlist");
                }
            }
        }
        return false;
    }

}
