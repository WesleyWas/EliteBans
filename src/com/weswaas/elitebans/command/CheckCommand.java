package com.weswaas.elitebans.command;

import com.weswaas.elitebans.Main;
import com.weswaas.elitebans.data.*;
import com.weswaas.elitebans.sql.SQLManager;
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
public class CheckCommand implements CommandExecutor{

    private SQLManager sql;

    public CheckCommand(SQLManager sql){
        this.sql = sql;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(p.hasPermission("elitebans.check")){
                if(args.length == 1){
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    String uuid = op.getUniqueId().toString();
                    String name = op.getName();

                    if(!sql.isRegisteredInInfos(uuid)){
                        p.sendMessage(ChatColor.RED + name + " did never join the server.");
                    }

                    boolean banned = Main.getInstance().getBanManager().isBannedInMaps(uuid) || sql.isTotalBanned(uuid);
                    boolean muted = Main.getInstance().getBanManager().isMutedInMaps(uuid) || Main.getInstance().getBanManager().isMutedInSQL(uuid);

                    String msg = ChatColor.DARK_AQUA + "not banned";
                    if(banned){
                        String ip = sql.getIP(uuid);
                        if(sql.isIPBanned(uuid, false) || sql.isIPBanned(uuid, true)){
                            if(sql.isPermIPBannedAnywaysBanned(uuid)){
                                IPBan ban = sql.getIPBan(ip);
                                msg = ChatColor.RED + "IP-Banned" + ChatColor.DARK_AQUA + " for " + ChatColor.RED + ban.getReason();
                            }else{
                                TempIPBan ban = sql.getTempIPBan(ip);
                                msg = ChatColor.RED + "IP-Banned" + ChatColor.DARK_AQUA + " for " + ChatColor.RED + ban.getReason() + ChatColor.DARK_AQUA + " for a duration of " + ChatColor.RED + ban.getAmount() + " " + ban.getTimeUnit().getName();
                            }
                        }else{
                            if(sql.isPermBanned(uuid)){
                                Ban ban = sql.getPermBan(uuid);
                                msg = ChatColor.RED + "banned" + ChatColor.DARK_AQUA + " for " + ChatColor.RED + ban.getReason();
                            }else{
                                TempBan ban = sql.getTempBan(uuid);
                                msg = ChatColor.RED + "banned" + ChatColor.DARK_AQUA + " for " + ChatColor.RED + ban.getReason() + ChatColor.DARK_AQUA + " for a duration of " + ChatColor.RED + ban.getAmount() + " " + ban.getTimeUnit().getName();
                            }
                        }
                    }

                    String muteMsg = ChatColor.DARK_AQUA + "not muted";
                    if(muted){
                        if(Main.getInstance().getBanManager().mutes.containsKey(uuid) || sql.isPermMuted(uuid)){
                            Mute mute;
                            if(Main.getInstance().getBanManager().mutes.containsKey(uuid)){
                                mute = Main.getInstance().getBanManager().mutes.get(uuid);
                            }else{
                                mute = sql.getMute(uuid);
                            }
                            muteMsg = ChatColor.RED + "muted" + ChatColor.DARK_AQUA + " for " + ChatColor.RED + mute.getReason();
                        }else{
                            TempMute mute;
                            if(Main.getInstance().getBanManager().tempmutes.containsKey(uuid)){
                                mute = Main.getInstance().getBanManager().tempmutes.get(uuid);
                            }else{
                                mute = sql.getTempMute(uuid);
                            }
                            muteMsg = ChatColor.RED + "muted" + ChatColor.DARK_AQUA + " for " + ChatColor.RED + mute.getReason() + ChatColor.DARK_AQUA + " for a duration of " + ChatColor.RED + mute.getAmount() + " " + mute.getTimeUnit().getName();
                        }
                    }

                    p.sendMessage(ChatColor.DARK_AQUA + "Informations about " + ChatColor.AQUA + name + " :");
                    p.sendMessage("§a");

                    if(banned){
                        p.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + name + ChatColor.DARK_AQUA + " is currently " + msg);
                        p.sendMessage("§a");
                    }
                    if(muted){
                        p.sendMessage(ChatColor.DARK_AQUA + "» " + ChatColor.AQUA + name + ChatColor.DARK_AQUA + " is currently " + muteMsg);
                        p.sendMessage("§a");
                    }

                    if(!muted && !banned){
                        p.sendMessage(ChatColor.DARK_AQUA + "» " + ChatColor.AQUA + name + ChatColor.DARK_AQUA + " does not have any punishment yet.");
                        p.sendMessage("§a");
                    }

                    if(sql.hasBeenPunished(uuid)){
                        p.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + name + ChatColor.DARK_AQUA + " has been punished " + ChatColor.RED + sql.getTotalPunishments(uuid) + " times (" + sql.getTotalBans(uuid) + " bans and " + sql.getTotalMutes(uuid) + " mutes" +")");
                    }else{
                        p.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + name + ChatColor.DARK_AQUA + " has never been punished.");
                    }


                }else{
                    p.sendMessage(ChatColor.RED + "Invalid synthax. Please try with /check <player>");
                }
            }
        }
        return false;
    }

}
