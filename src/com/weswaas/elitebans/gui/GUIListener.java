package com.weswaas.elitebans.gui;

import com.weswaas.elitebans.enums.BanReasons;
import com.weswaas.elitebans.enums.KickReasons;
import com.weswaas.elitebans.enums.MuteReasons;
import com.weswaas.elitebans.enums.TimeUnits;
import com.weswaas.elitebans.managers.BanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

/**
 * Created by Weswas on 06/11/2016.
 */
public class GUIListener implements Listener{

    private BanManager manager;

    public GUIListener(BanManager manager){
        this.manager = manager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();

        if(e.getClickedInventory() != null && e.getClickedInventory().getName().contains("Ban")){
            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()){

                e.setCancelled(true);

                String uuid = null;
                String name = null;
                long expires;
                String result = "false";
                boolean ipBan = false;
                String finalIP;

                ItemStack skull = e.getClickedInventory().getItem(3);

                String uuid2 = skull.getItemMeta().getLore().get(2);
                String[] parts = uuid2.split(" ");
                String last = parts[parts.length - 1];
                uuid = last.substring(2);

                String name2 = skull.getItemMeta().getLore().get(1);
                String[] parts2 = name2.split(" ");
                String last2 = parts2[parts2.length - 1];
                name = last2.substring(2);

                String name3 = skull.getItemMeta().getLore().get(6);
                String[] parts3 = name3.split(" ");
                String last3 = parts3[parts3.length - 1];
                result = last3.substring(2);
                if(result.equalsIgnoreCase("IP-Ban")){
                    ipBan = true;
                }

                String ip = skull.getItemMeta().getLore().get(5);
                String[] parts4 = ip.split(" ");
                String last4 = parts4[parts4.length - 1];
                finalIP = last4.substring(2);

                ItemStack item = e.getCurrentItem();

                if(item.getType() != Material.SIGN && item.getType() != Material.SKULL_ITEM){

                    BanReasons ban = BanReasons.getByMaterial(item.getType());
                    TimeUnits unit = ban.getUnit();
                    boolean infinite = unit.equals(TimeUnits.INFINITE);
                    expires = System.currentTimeMillis() + (ban.getUnit().getDuration() * ban.getDuration());

                    Date now = new Date(System.currentTimeMillis());

                    if(infinite){
                        if(ipBan){
                            manager.IPBan(p.getName(), uuid, 2, System.currentTimeMillis(), ban.getName(), name, unit, ban.getDuration(), false, finalIP);
                        }else {
                            manager.ban(p.getName(), uuid, 2, System.currentTimeMillis(), ban.getName(), name, unit, ban.getDuration(), false);
                        }
                    }else{
                        if(ipBan){
                            manager.tempIPBan(p.getName(), uuid, 2, System.currentTimeMillis(), expires, ban.getName(), name, unit, ban.getDuration(), false, finalIP);
                        }else{
                            manager.tempBan(p.getName(), uuid, 3, System.currentTimeMillis(), expires, ban.getName(), name, unit, ban.getDuration(), false);
                        }
                    }

                    p.closeInventory();
                }

            }
        }else if(e.getClickedInventory() != null && e.getClickedInventory().getName() != null && e.getClickedInventory().getName().contains("Mute")){
            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()){

                e.setCancelled(true);

                String uuid = null;
                String name = null;
                long expires;
                String result = "false";
                boolean ipBan = false;
                String finalIP;

                ItemStack skull = e.getClickedInventory().getItem(3);

                String uuid2 = skull.getItemMeta().getLore().get(2);
                String[] parts = uuid2.split(" ");
                String last = parts[parts.length - 1];
                uuid = last.substring(2);

                String name2 = skull.getItemMeta().getLore().get(1);
                String[] parts2 = name2.split(" ");
                String last2 = parts2[parts2.length - 1];
                name = last2.substring(2);

                String ip = skull.getItemMeta().getLore().get(5);
                String[] parts4 = ip.split(" ");
                String last4 = parts4[parts4.length - 1];
                finalIP = last4.substring(2);

                ItemStack item = e.getCurrentItem();

                if(item.getType() != Material.SIGN && !item.getData().equals((byte)3)){

                    MuteReasons mute = MuteReasons.getByMaterial(item.getType());
                    TimeUnits unit = mute.getUnit();
                    boolean infinite = unit.equals(TimeUnits.INFINITE);
                    expires = System.currentTimeMillis() + (mute.getUnit().getDuration() * mute.getDuration());

                    Date now = new Date(System.currentTimeMillis());

                    if(infinite){
                        manager.mute(p.getName(), uuid, 2, System.currentTimeMillis(), mute.getName(), name, unit, mute.getDuration(), false);
                    }else{
                        manager.tempMute(p.getName(), uuid, 2, System.currentTimeMillis(), expires, mute.getName(), name, unit, mute.getDuration(), false);
                    }

                    p.closeInventory();

                }
            }
        }else if(e.getClickedInventory() != null && e.getClickedInventory().getName() != null && e.getClickedInventory().getName().contains("Kick")){
            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()){

                e.setCancelled(true);

                ItemStack item = e.getCurrentItem();

                KickReasons kick = KickReasons.getByMaterial(item.getType());

                String name = "Error";

                String name2 = e.getClickedInventory().getName();
                String[] parts2 = name2.split(" ");
                String last2 = parts2[parts2.length - 1];
                name = last2;

                Player target = Bukkit.getPlayer(name);

                target.kickPlayer("\n"
                 + ChatColor.DARK_AQUA + name + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + "You have been kicked\n"
                + "\n"
                + ChatColor.DARK_AQUA + "Kicked by" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + p.getName() + "\n"
                + ChatColor.DARK_AQUA + "Reason" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + kick.getName() + "\n"
                + "\n"
                + ChatColor.AQUA + "ts.oniziac.net");

                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + name + ChatColor.AQUA + " has been kicked for " + ChatColor.DARK_AQUA + kick.getName());

                p.closeInventory();

            }
        }

    }

}
