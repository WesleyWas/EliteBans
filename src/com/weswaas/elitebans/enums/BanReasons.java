package com.weswaas.elitebans.enums;

import com.weswaas.elitebans.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 06/11/2016.
 */
public enum BanReasons {

    KILLAURA("KillAura", 1, TimeUnits.INFINITE, 9, new ItemBuilder().type(Material.DIAMOND_SWORD).name(ChatColor.GREEN + "KillAura").build()),
    FLY("Fly", 2, TimeUnits.MONTHS, 18, new ItemBuilder().type(Material.GOLD_BOOTS).name(ChatColor.GREEN + "Fly").build()),
    ANTIKNOCKBACK("Anti-Knockback", 2, TimeUnits.MONTHS, 19, new ItemBuilder().type(Material.IRON_BOOTS).name(ChatColor.GREEN + "Anti-Knockback").build()),
    SPEEDHACK("Speed-Hack", 2, TimeUnits.MONTHS, 20, new ItemBuilder().type(Material.DIAMOND_BOOTS).name(ChatColor.GREEN + "Speed-Hack").build()),
    FASTBOW("Fast-Bow", 3, TimeUnits.WEEKS, 27, new ItemBuilder().type(Material.BOW).name(ChatColor.GREEN + "Fast-Bow").build()),
    FASTMINE("Fast-Mining", 2, TimeUnits.MONTHS, 36, new ItemBuilder().type(Material.WOOD_PICKAXE).name(ChatColor.GREEN + "Fast-Mining").build()),
    ILLEGALMINING("Illegal-Mining", 1, TimeUnits.WEEKS, 37, new ItemBuilder().type(Material.GOLD_PICKAXE).name(ChatColor.GREEN + "Illegal-Mining").build()),
    CAVEFINDER("Cave-Finder", 3, TimeUnits.MONTHS, 38, new ItemBuilder().type(Material.IRON_PICKAXE).name(ChatColor.GREEN + "Cave-Finder").build()),
    XRAY("X-Ray", 3, TimeUnits.MONTHS, 39, new ItemBuilder().type(Material.DIAMOND_PICKAXE).name(ChatColor.GREEN + "X-Ray").build()),
    ABUSIVE_DISRESPECT("Abusive Disrespect", 2, TimeUnits.DAYS, 45, new ItemBuilder().type(Material.PAPER).name(ChatColor.GREEN + "Abusive Disrespect").build()),
    IPVP("iPvP", 2, TimeUnits.WEEKS, 46, new ItemBuilder().type(Material.REDSTONE_TORCH_ON).name(ChatColor.GREEN + "iPvP").build());

    private String name;
    private int duration;
    private TimeUnits unit;
    private ItemStack item;
    private int slot;

    BanReasons(String name, int duration, TimeUnits unit, int slot, ItemStack item){
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

    public static BanReasons getByMaterial(Material mat){
        for(BanReasons bans : BanReasons.values()){
            if(bans.getItem().getType() == mat){
                return bans;
            }
        }
        return null;
    }

}
