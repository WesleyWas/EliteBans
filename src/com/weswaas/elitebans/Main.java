package com.weswaas.elitebans;

import com.weswaas.elitebans.command.*;
import com.weswaas.elitebans.gui.GUIListener;
import com.weswaas.elitebans.gui.GUIPresets;
import com.weswaas.elitebans.listeners.ChatListener;
import com.weswaas.elitebans.listeners.JoinListener;
import com.weswaas.elitebans.listeners.events.listener.PlayerBanListener;
import com.weswaas.elitebans.listeners.events.listener.PlayerIPBanListener;
import com.weswaas.elitebans.listeners.events.listener.PlayerTempBanListener;
import com.weswaas.elitebans.listeners.events.listener.PlayerTempIPBanListener;
import com.weswaas.elitebans.managers.BanManager;
import com.weswaas.elitebans.sql.SQLManager;
import com.weswaas.elitebans.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Weswas on 05/11/2016.
 */
public class Main extends JavaPlugin{

    private GUIPresets presets;
    private BanManager manager;
    private SQLManager sql;
    private ConfigUtil cu;

    private static Main instance;

    @Override
    public void onEnable() {
        instances();

        listeners();
        registerCommands();
        sql.connect();

    }

    @Override
    public void onDisable(){
        sql.deconnection();
    }

    private void instances(){
        instance = this;

        presets = new GUIPresets();
        cu = new ConfigUtil(this);
        sql = new SQLManager(cu.getUrlbase(),cu.getHost(),cu.getDatabase(),cu.getUsername(),cu.getPassword());
        manager = new BanManager(sql);
    }

    public static Main getInstance(){
        return instance;
    }

    public GUIPresets getGUIPresets(){
        return this.presets;
    }

    public SQLManager getSQLManager(){
        return this.sql;
    }

    public BanManager getBanManager(){
        return this.manager;
    }

    private void registerCommands(){

        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("banip").setExecutor(new BanIPCommand());
        getCommand("alts").setExecutor(new AltsCommand());
        getCommand("check").setExecutor(new CheckCommand(sql));
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnmuteCommand());
        getCommand("banlist").setExecutor(new BanListCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("ip").setExecutor(new IPCommand());

    }

    private void listeners(){

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new GUIListener(manager), this);
        pm.registerEvents(new PlayerBanListener(), this);
        pm.registerEvents(new PlayerTempBanListener(), this);
        pm.registerEvents(new JoinListener(sql), this);
        pm.registerEvents(new PlayerIPBanListener(), this);
        pm.registerEvents(new PlayerTempIPBanListener(), this);
        pm.registerEvents(new ChatListener(), this);

    }
}
