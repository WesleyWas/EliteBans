package com.weswaas.elitebans.util;

import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Weswas on 06/11/2016.
 */
public class Messages {

    public static String getTempBanMessage(String name, String reason, String actor, String duration, long creation, long expires, boolean isIPBan){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        String expireDate = sdf.format(new Date(expires));
        String banDate = sdf.format(new Date(creation));

        String firstLine = ChatColor.DARK_AQUA + name + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + "You have been " + (isIPBan ? "IP-Banned" : "banned") + "\n";

        return firstLine
                + "§f\n"
                + ChatColor.DARK_AQUA + "Reason" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + reason + "\n"
                + ChatColor.DARK_AQUA + "Banned by" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + actor + "\n"
                + ChatColor.DARK_AQUA + "Duration" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + duration + "\n"
                + ChatColor.DARK_AQUA + "Banned on" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + banDate + "\n"
                + ChatColor.DARK_AQUA + "Unban Date" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + expireDate + "\n"
                + "§f\n"
                + ChatColor.DARK_AQUA + "Apply" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + "Contact us on Twitter @Oniziac";
    }

    public static String getPermBanMessage(String name, String reason, String actor, long creation, boolean isIPBan){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        String banDate = sdf.format(new Date(creation));

        String firstLine = ChatColor.DARK_AQUA + name + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + "You have been " + (isIPBan ? "IP-Banned" : "banned") + "\n";

        return firstLine
                + "§f\n"
                + ChatColor.DARK_AQUA + "Reason" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + reason + "\n"
                + ChatColor.DARK_AQUA + "Banned by" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + actor + "\n"
                + ChatColor.DARK_AQUA + "Duration" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + "Permanent \n"
                + ChatColor.DARK_AQUA + "Banned on" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + banDate + "\n"
                + "§f\n"
                + ChatColor.DARK_AQUA + "Apply" + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + "Contact us on Twitter @Oniziac";
    }

}
