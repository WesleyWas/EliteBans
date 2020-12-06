package com.weswaas.elitebans.listeners;

import com.weswaas.elitebans.Main;
import com.weswaas.elitebans.data.Ban;
import com.weswaas.elitebans.data.IPBan;
import com.weswaas.elitebans.data.TempBan;
import com.weswaas.elitebans.data.TempIPBan;
import com.weswaas.elitebans.sql.SQLManager;
import com.weswaas.elitebans.util.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

/**
 * Created by Weswas on 06/11/2016.
 */
public class JoinListener implements Listener{

    private SQLManager sql;

    public JoinListener(SQLManager sql){
        this.sql = sql;
    }

    @EventHandler
    public void onLogin(PlayerPreLoginEvent e){

            String ip = e.getAddress().getHostName();
            String uuid = e.getUniqueId().toString();

            if(Main.getInstance().getBanManager().isBannedInMaps(uuid)){
                boolean perm = Main.getInstance().getBanManager().bans.containsKey(uuid);
                if(perm){
                    Ban ban = Main.getInstance().getBanManager().bans.get(uuid);
                    disallowPerm(e, ban, true);
                }else{
                    TempBan tempban = Main.getInstance().getBanManager().tempbans.get(uuid);
                    if(!tempban.hasExpired()){
                        disallowTemp(e, tempban, false);
                    }else{
                        Main.getInstance().getSQLManager().unban(uuid, false);
                    }
                }
            }else if(sql.isNoIPBannedAnywaysBanned(uuid) || sql.isIPBanned(ip)){
                if(sql.isIPBanned(ip)){
                    boolean perm = sql.isPermIPBanned(ip);
                    if(perm){
                        IPBan ban = sql.getIPBan(ip);
                        disallowPerm(e, ban, true);
                    }else{
                        TempIPBan ban = sql.getTempIPBan(ip);
                        if(!ban.hasExpired()){
                            disallowTemp(e, ban, true);
                        }else{
                            Main.getInstance().getSQLManager().unbanIP(uuid, false);
                        }
                    }
                }else{
                    boolean perm = sql.isPermBanned(uuid);
                    if(perm){
                        Ban ban = sql.getPermBan(uuid);
                        disallowPerm(e, ban, false);
                    }else{
                        TempBan tempban = sql.getTempBan(uuid);
                        if(!tempban.hasExpired()){
                            disallowTemp(e, tempban, false);
                        }else{
                            Main.getInstance().getSQLManager().unban(uuid, false);
                        }
                    }
                }
            }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        if(!sql.isRegisteredInAlts(uuid)){
            sql.registerIntoAlts(p.getAddress().getHostName(), uuid);
        }

        if(!sql.isRegisteredInInfos(uuid)){
            sql.storeInInfos(uuid);
        }

    }

    private void disallowTemp(PlayerPreLoginEvent e, TempBan tempban, boolean ip){

        String duration = tempban.getAmount() + " " + tempban.getTimeUnit().getName();

        e.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, Messages.getTempBanMessage(tempban.getPunishedName(), tempban.getReason(), tempban.getActorName(), duration, tempban.getCreation(), tempban.getExpires(), false));
    }

    private void disallowPerm(PlayerPreLoginEvent e, Ban ban, boolean ip){

        e.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, Messages.getPermBanMessage(ban.getPunishedName(), ban.getReason(), ban.getActorName(), ban.getCreation(), ip));

    }

}
