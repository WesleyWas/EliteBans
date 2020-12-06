package com.weswaas.elitebans.enums;

import com.weswaas.elitebans.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 09/11/2016.
 */
public enum MuteReasons {

    HACKUSATION("Hackusation", 1, TimeUnits.HOURS, 9, new ItemBuilder().type(Material.IRON_SWORD).name(ChatColor.GREEN + "Hackusation").build()),
    DISRESPECT("Disrespect", 45, TimeUnits.MINUTES, 10, new ItemBuilder().type(Material.BLAZE_POWDER).name(ChatColor.GREEN + "Disrespect").build()),
    BADSPORTSMANSHIP("Bad Sportsmanship", 1, TimeUnits.HOURS, 11, new ItemBuilder().type(Material.LEATHER_BOOTS).name(ChatColor.GREEN + "Bad Sportsmanship").build()),
    SUICIDEENCOURAGEMENT("Suicide Encouragement", 3, TimeUnits.DAYS, 12, new ItemBuilder().type(Material.SKULL_ITEM).data((byte)2).name(ChatColor.GREEN + "Suicide Encouragement").build()),
    DEATHTHREADS("Death Threads", 3, TimeUnits.DAYS, 13, new ItemBuilder().type(Material.SKULL_ITEM).data((byte)1).name(ChatColor.GREEN + "Death Threads").build()),
    SPAM("Spam", 30, TimeUnits.MINUTES, 14, new ItemBuilder().type(Material.BOWL).name(ChatColor.GREEN + "Spam").build()),
    RACISM("Racism", 3, TimeUnits.DAYS, 15, new ItemBuilder().type(Material.NETHER_WARTS).name(ChatColor.GREEN + "Racism").build()),
    ADVERTISEMENT("Advertisement", 2, TimeUnits.HOURS, 16, new ItemBuilder().type(Material.PAPER).name(ChatColor.GREEN + "Advertisement").build()),
    OTHER("Other", 3, TimeUnits.HOURS, 17, new ItemBuilder().type(Material.REDSTONE_TORCH_ON).name(ChatColor.GREEN + "Other").build());

    private String name;
    private int duration;
    private TimeUnits unit;
    private ItemStack item;
    private int slot;

    MuteReasons(String name, int duration, TimeUnits unit, int slot, ItemStack item){
        this.name = name;
        this.duration = duration;
        this.unit = unit;
        this.item = item;
        this.slot = slot;
    }

    public String getName(){
        return this.name;
    }

    public int getDuration(){
        return this.duration;
    }

    public TimeUnits getUnit(){
        return this.unit;
    }

    public ItemStack getItem(){
        return this.item;
    }

    public int getSlot(){
        return this.slot;
    }

    public static MuteReasons getByMaterial(Material mat){
        for(MuteReasons mutes : MuteReasons.values()){
            if(mutes.getItem().getType() == mat){
                return mutes;
            }
        }
        return null;
    }

}
