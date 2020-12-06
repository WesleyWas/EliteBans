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
 * Created by Weswas on 07/11/2016.
 */
public class UnbanCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(p.hasPermission("elitebans.unban")){
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

                    if(!Main.getInstance().getSQLManager().isTotalBanned(uuid)){
                        p.sendMessage(ChatColor.RED + name + " is currently not banned.");
                        return true;
                    }

                    if(args.length == 3){

                        int amount = Integer.valueOf(args[1]);
                        if(amount > 0){
                            if(TimeUnits.isTimeUnit(args[2])){
                                TimeUnits unit = TimeUnits.getBySymbol(args[2]);
                                if(unit != TimeUnits.INFINITE){
                                    if(Main.getInstance().getBanManager().bans.containsKey(uuid) || Main.getInstance().getBanManager().tempbans.containsKey(uuid)){
                                        Main.getInstance().getBanManager().unbanIn((Main.getInstance().getBanManager().bans.containsKey(uuid) ? Main.getInstance().getBanManager().bans.get(uuid) : Main.getInstance().getBanManager().tempbans.get(uuid)), unit, amount);
                                    }else{
                                        boolean perm = Main.getInstance().getSQLManager().isPermBanned(uuid);
                                        Main.getInstance().getBanManager().unbanIn((perm) ? Main.getInstance().getSQLManager().getPermBan(uuid) : Main.getInstance().getSQLManager().getTempBan(uuid), unit, amount);
                                    }
                                    p.sendMessage(ChatColor.DARK_AQUA + name + ChatColor.AQUA + " will be unbanned in " + ChatColor.DARK_AQUA + amount + " " + unit.getName());
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
                        boolean ip = Main.getInstance().getSQLManager().isIPBanned(uuid, true) || Main.getInstance().getSQLManager().isIPBanned(uuid, false);
                        if(!ip){
                            Main.getInstance().getBanManager().bans.remove(uuid);
                            Main.getInstance().getBanManager().tempbans.remove(uuid);
                        }
                        Main.getInstance().getBanManager().unban(uuid);
                        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + name + ChatColor.AQUA + " has been unbanned.");
                    }
                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /unban <player [<time>] [<unit>}");
                }
            }
        }
        return false;
    }
}
