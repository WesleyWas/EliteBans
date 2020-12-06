package com.weswaas.elitebans.util;

import com.weswaas.elitebans.Main;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Weswas on 14/11/2016.
 */
public class ConfigUtil {

    private Main main;

    private FileConfiguration config;

    private String username, password, urlbase, host, database;

    public ConfigUtil(Main main){
        this.main = main;
        this.config = main.getConfig();
        loadConfiguration();
        readConfiguration();
    }

    public void loadConfiguration(){
        config.options().copyDefaults(true);
        main.saveDefaultConfig();
    }

    public void readConfiguration(){
        this.username = config.getString("sql.username");
        this.password = config.getString("sql.password");
        this.urlbase = config.getString("sql.urlbase");
        this.host = config.getString("sql.host");
        this.database = config.getString("sql.database");
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getUrlbase(){
        return this.urlbase;
    }

    public String getHost(){
        return this.host;
    }

    public String getDatabase(){
        return this.database;
    }

}
