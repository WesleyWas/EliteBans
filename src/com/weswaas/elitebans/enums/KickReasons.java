package com.weswaas.elitebans.enums;

import com.weswaas.elitebans.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 09/11/2016.
 */
public enum KickReasons {

    GLITCHED("Glitched", 0, new ItemBuilder().type(Material.FEATHER).name(ChatColor.GREEN + "Glitched").build()),
    CAMPING("Camping", 1, new ItemBuilder().type(Material.COAL).name(ChatColor.GREEN + "Camping").build()),
    TEAMING("Teaming", 2, new ItemBuilder().type(Material.GOLD_SWORD).name(ChatColor.GREEN + "Teaming").build()),
    OTHER("Other", 8, new ItemBuilder().type(Material.REDSTONE_TORCH_ON).name(ChatColor.GREEN + "Other").build());

    private String name;
    private ItemStack item;
    private int slot;

    KickReasons(String name, int slot, ItemStack item){
        this.name = name;
        this.item = item;
        this.slot = slot;
    }

    public String getName(){
        return this.name;
    }

    public ItemStack getItem(){
        return this.item;
    }

    public int getSlot(){
        return this.slot;
    }

    public static KickReasons getByMaterial(Material mat){
        for(KickReasons kicks : KickReasons.values()){
            if(kicks.getItem().getType() == mat){
                return kicks;
            }
        }
        return null;
    }

}
