package com.weswaas.elitebans.command;

import com.weswaas.elitebans.Main;
import com.weswaas.elitebans.enums.TimeUnits;
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
public class UnmuteCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("elitebans.unmute")){
                if(args.length == 1 || args.length == 3){
                    Player target = Bukkit.getPlayer(args[0]);
                    String uuid;
                    String name;

                    if(target != null && target.isOnline()){
                        uuid = target.getUniqueId().toString();
                        name = target.getName();
                    }else{
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        uuid = op.getUniqueId().toString();
                        name = op.getName();
                    }

                    if(!Main.getInstance().getBanManager().isMutedInMaps(uuid) && !Main.getInstance().getBanManager().isMutedInSQL(uuid)){
                        p.sendMessage(ChatColor.RED + name + " is currently not muted.");
                        return true;
                    }

                    if(args.length == 3){

                        int amount = Integer.valueOf(args[1]);
                        if(amount > 0){
                            if(TimeUnits.isTimeUnit(args[2])){
                                TimeUnits unit = TimeUnits.getBySymbol(args[2]);
                                if(unit != TimeUnits.INFINITE){
                                    if(Main.getInstance().getBanManager().mutes.containsKey(uuid) || Main.getInstance().getBanManager().tempmutes.containsKey(uuid)){
                                        Main.getInstance().getBanManager().unMuteIn((Main.getInstance().getBanManager().mutes.containsKey(uuid) ? Main.getInstance().getBanManager().mutes.get(uuid) : Main.getInstance().getBanManager().tempmutes.get(uuid)), unit, amount);
                                    }else{
                                        boolean perm = Main.getInstance().getSQLManager().isPermMuted(uuid);
                                        Main.getInstance().getBanManager().unMuteIn((perm) ? Main.getInstance().getSQLManager().getMute(uuid) : Main.getInstance().getSQLManager().getTempMute(uuid), unit, amount);
                                    }
                                    p.sendMessage(ChatColor.DARK_AQUA + name + ChatColor.AQUA + " will be unmuted in " + ChatColor.DARK_AQUA + amount + " " + unit.getName());
                                }else{
                                    p.sendMessage(ChatColor.RED + "Please choose another unit than infinite.");
                                }
                            }else{
                                p.sendMessage(ChatColor.RED + args[2] + " is not a time unit symbol.");
                            }
                        }else{
                            p.sendMessage(ChatColor.RED + "Please give a bigger number than 0.");
                        }

                    }else{
                        Main.getInstance().getBanManager().mutes.remove(uuid);
                        Main.getInstance().getBanManager().tempmutes.remove(uuid);
                        Main.getInstance().getBanManager().unMute(uuid);
                        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + name + ChatColor.AQUA + " has been unmuted.");
                    }
                    }

                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /unmute <player> [<amount>] [<unit>]");
                }
            }
            return false;
        }

}
