package com.weswaas.elitebans.gui;

import com.weswaas.elitebans.enums.BanReasons;
import com.weswaas.elitebans.enums.KickReasons;
import com.weswaas.elitebans.enums.MuteReasons;
import com.weswaas.elitebans.enums.TimeUnits;
import com.weswaas.elitebans.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Weswas on 06/11/2016.
 */
public class GUIPresets {

    public void banInventory(Player p, String targetName, String uuid, long firstPlayed, boolean isOnline, String ip, boolean banIP){

        String finalName;

        if(targetName.length() >= 23){
            finalName = targetName.substring(0, 23);
        }else{
            finalName = targetName;
        }
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Ban" + (banIP ? "IP" : "") + " " + finalName);

        ItemStack info = new ItemStack(Material.SIGN);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Ban infos");
        List<String> lore = new ArrayList<>();
        for(BanReasons bans : BanReasons.values()){
            lore.add(ChatColor.DARK_AQUA + bans.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + (bans.getUnit() == TimeUnits.INFINITE ? "Infinite" : bans.getDuration() + " " + bans.getUnit().getName()));
        }
        meta.setLore(lore);
        info.setItemMeta(meta);

        Date date = new Date(firstPlayed);

        ItemStack pInfo = new ItemBuilder().type(Material.SKULL_ITEM).data((byte)3).name(ChatColor.GREEN + targetName + "'s infos")
                .lore("§a",
                ChatColor.DARK_AQUA + "Name" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + targetName,
                ChatColor.DARK_AQUA + "UUID" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + uuid,
                ChatColor.DARK_AQUA + "First Join" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + (firstPlayed > 1000 ? date : "Has never joined"),
                ChatColor.DARK_AQUA + "Is Online" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + (isOnline ? "Yes" : "No"),
                ChatColor.DARK_AQUA + "Last IP Address" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + ip,
                ChatColor.DARK_AQUA + "Ban Intention" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + (banIP ? "IP-Ban" : "Account-Ban"))
                .build();

        inv.setItem(3, pInfo);
        inv.setItem(5, info);

        for(BanReasons bans : BanReasons.values()){
            inv.setItem(bans.getSlot(), bans.getItem());
        }

        p.openInventory(inv);

    }

    public void muteInventory(Player p, String targetName, String uuid, long firstPlayed, boolean isOnline, String ip){

        String finalName;

        if(targetName.length() >= 22){
            finalName = targetName.substring(0, 23);
        }else{
            finalName = targetName;
        }
        Inventory inv = Bukkit.createInventory(null, 18, ChatColor.RED + "Mute " + finalName);

        ItemStack info = new ItemStack(Material.SIGN);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Mute infos");
        List<String> lore = new ArrayList<>();
        for(MuteReasons mutes : MuteReasons.values()){
            lore.add(ChatColor.DARK_AQUA + mutes.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + (mutes.getUnit() == TimeUnits.INFINITE ? "Infinite" : mutes.getDuration() + " " + mutes.getUnit().getName()));
        }
        meta.setLore(lore);
        info.setItemMeta(meta);

        Date date = new Date(firstPlayed);

        ItemStack pInfo = new ItemBuilder().type(Material.SKULL_ITEM).data((byte)3).name(ChatColor.GREEN + targetName + "'s infos")
                .lore("§a",
                        ChatColor.DARK_AQUA + "Name" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + targetName,
                        ChatColor.DARK_AQUA + "UUID" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + uuid,
                        ChatColor.DARK_AQUA + "First Join" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + (firstPlayed > 1000 ? date : "Has never joined"),
                        ChatColor.DARK_AQUA + "Is Online" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + (isOnline ? "Yes" : "No"),
                        ChatColor.DARK_AQUA + "Last IP Address" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + ip)
                .build();

        inv.setItem(3, pInfo);
        inv.setItem(5, info);

        for(MuteReasons mutes : MuteReasons.values()){
            inv.setItem(mutes.getSlot(), mutes.getItem());
        }

        p.openInventory(inv);

    }

    public void kickInventory(Player p, String targetName){
        String finalName;

        if(targetName.length() >= 22){
            finalName = targetName.substring(0, 23);
        }else{
            finalName = targetName;
        }
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + "Kick " + finalName);

        for(KickReasons kicks : KickReasons.values()){
            inv.setItem(kicks.getSlot(), kicks.getItem());
        }

        p.openInventory(inv);
    }

}
