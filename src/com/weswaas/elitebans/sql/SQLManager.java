package com.weswaas.elitebans.sql;

import com.weswaas.elitebans.data.*;
import com.weswaas.elitebans.enums.TimeUnits;
import org.bukkit.Bukkit;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Weswas on 06/11/2016.
 */
public class SQLManager {

    private String urlbase, host, user, pass, database;
    private Connection connection;

    public SQLManager(String urlbase, String host, String database, String user, String pass) {
        this.urlbase = urlbase;
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.database = database;
    }

    public void connect(){

        if(!isConnected()){
            try{
                connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);

                tables();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void deconnection(){
        if(isConnected()){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private boolean isConnected() {
        try {
            if ((connection == null) || connection.isClosed() || (!connection.isValid(5))) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() {
        if (!isConnected()) {
            connect();
        }

        return this.connection;
    }



    private void tables(){
        try{
            PreparedStatement sts = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS bans (actorname VARCHAR(255), uuid VARCHAR(255), id INTEGER NOT NULL, creation BIGINT NOT NULL DEFAULT 0, reason VARCHAR(255), punishedname VARCHAR(255), unit VARCHAR(255), amount INTEGER NOT NULL DEFAULT 0)");
            PreparedStatement sts2 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tempbans (actorname VARCHAR(255), uuid VARCHAR(255), id INTEGER NOT NULL, creation BIGINT NOT NULL DEFAULT 0, expires BIGINT NOT NULL DEFAULT 0, reason VARCHAR(255), punishedname VARCHAR(255), unit VARCHAR(255), amount INTEGER NOT NULL DEFAULT 0)");
            PreparedStatement sts3 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS mutes (actorname VARCHAR(255), uuid VARCHAR(255), id INTEGER NOT NULL, creation BIGINT NOT NULL DEFAULT 0, reason VARCHAR(255), punishedname VARCHAR(255), unit VARCHAR(255), amount INTEGER NOT NULL DEFAULT 0)");
            PreparedStatement sts4 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tempmutes (actorname VARCHAR(255), uuid VARCHAR(255), id INTEGER NOT NULL, creation BIGINT NOT NULL DEFAULT 0, expires BIGINT NOT NULL DEFAULT 0, reason VARCHAR(255), punishedname VARCHAR(255), unit VARCHAR(255), amount INTEGER NOT NULL DEFAULT 0)");
            PreparedStatement sts5 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS infos (uuid VARCHAR(255), totalbans INTEGER NOT NULL, totalmutes INTEGER NOT NULL, totalpunishments INTEGER NOT NULL, warns INTEGER NOT NULL)");
            PreparedStatement sts6 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS alts (ip VARCHAR(255), uuid VARCHAR(255))");
            PreparedStatement sts7 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ipbans (actorname VARCHAR(255), bannedip VARCHAR(255), id INTEGER NOT NULL, creation BIGINT NOT NULL DEFAULT 0, reason VARCHAR(255), punishedname VARCHAR(255), uuid VARCHAR(255), unit VARCHAR(255), amount INTEGER NOT NULL DEFAULT 0)");
            PreparedStatement sts8 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tempipbans (actorname VARCHAR(255), bannedip VARCHAR(255), id INTEGER NOT NULL, creation BIGINT NOT NULL DEFAULT 0, expires BIGINT NOT NULL DEFAULT 0, reason VARCHAR(255), punishedname VARCHAR(255), uuid VARCHAR(255), unit VARCHAR(255), amount INTEGER NOT NULL DEFAULT 0)");

            sts.executeUpdate();
            sts.close();

            sts2.executeUpdate();
            sts2.close();

            sts3.executeUpdate();
            sts3.close();

            sts4.executeUpdate();
            sts4.close();

            sts5.executeUpdate();
            sts5.close();

            sts6.executeUpdate();
            sts6.close();

            sts7.executeUpdate();
            sts7.close();

            sts8.executeUpdate();
            sts8.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void storeMute(String actorName, String bannedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO mutes (actorname, uuid, id, creation, reason, punishedname, unit, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            sts.setString(1, actorName);
            sts.setString(2, bannedUUID);
            sts.setInt(3, id);
            sts.setObject(4, BigInteger.valueOf(creation));
            sts.setString(5, reason);
            sts.setString(6, punishedName);
            sts.setString(7, unit.getName());
            sts.setInt(8, amount);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void storeBan(String actorName, String bannedUUID, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO bans (actorname, uuid, id, creation, reason, punishedname, unit, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            sts.setString(1, actorName);
            sts.setString(2, bannedUUID);
            sts.setInt(3, id);
            sts.setObject(4, BigInteger.valueOf(creation));
            sts.setString(5, reason);
            sts.setString(6, punishedName);
            sts.setString(7, unit.getName());
            sts.setInt(8, amount);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void storeIPBan(String actorName, String bannedIP, int id, long creation, String reason, String punishedName, TimeUnits unit, int amount, String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO ipbans (actorname, bannedip, id, creation, reason, punishedname, uuid, unit, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sts.setString(1, actorName);
            sts.setString(2, bannedIP);
            sts.setInt(3, id);
            sts.setObject(4, BigInteger.valueOf(creation));
            sts.setString(5, reason);
            sts.setString(6, punishedName);
            sts.setString(7, uuid);
            sts.setString(8, unit.getName());
            sts.setInt(9, amount);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void storeTempMute(String actorName, String bannedUUID, int id, long creation, long expires, String reason, String punishedName, TimeUnits unit, int amount){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO tempmutes (actorname, uuid, id, creation, expires, reason, punishedname, unit, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sts.setString(1, actorName);
            sts.setString(2, bannedUUID);
            sts.setInt(3, id);
            sts.setObject(4, BigInteger.valueOf(creation));
            sts.setObject(5, BigInteger.valueOf(expires));
            sts.setString(6, reason);
            sts.setString(7, punishedName);
            sts.setString(8, unit.getName());
            sts.setInt(9, amount);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void storeTempBan(String actorName, String bannedUUID, int id, long creation, long expires, String reason, String punishedName, TimeUnits unit, int amount){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO tempbans (actorname, uuid, id, creation, expires, reason, punishedname, unit, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sts.setString(1, actorName);
            sts.setString(2, bannedUUID);
            sts.setInt(3, id);
            sts.setObject(4, BigInteger.valueOf(creation));
            sts.setObject(5, BigInteger.valueOf(expires));
            sts.setString(6, reason);
            sts.setString(7, punishedName);
            sts.setString(8, unit.getName());
            sts.setInt(9, amount);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void storeTempIPBan(String actorName, String bannedIP, int id, long creation, long expires, String reason, String punishedName, TimeUnits unit, int amount, String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO tempipbans (actorname, bannedip, id, creation, expires, reason, punishedname, uuid, unit, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sts.setString(1, actorName);
            sts.setString(2, bannedIP);
            sts.setInt(3, id);
            sts.setObject(4, BigInteger.valueOf(creation));
            sts.setObject(5, BigInteger.valueOf(expires));
            sts.setString(6, reason);
            sts.setString(7, punishedName);
            sts.setString(8, uuid);
            sts.setString(9, unit.getName());
            sts.setInt(10, amount);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void storeInInfos(String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO infos (uuid, totalbans, totalmutes, totalpunishments, warns) VALUES (?, 0, 0, 0, 0)");
            sts.setString(1, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isRegisteredInInfos(String uuid){
        try{
            boolean is = false;
            PreparedStatement sts = getConnection().prepareStatement("SELECT totalbans FROM infos WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getAlts(String uuid){
        ArrayList<String> altsUUID = new ArrayList<>();
        ArrayList<String> altsName = new ArrayList<>();

        String ip = getIP(uuid);

        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT * FROM alts WHERE ip = ?");
            sts.setString(1, ip);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                altsUUID.add(rs.getString("uuid"));
            }
            sts.close();

            for(String s : altsUUID){
                String name = Bukkit.getOfflinePlayer(UUID.fromString(s)).getName();
                altsName.add(name);
            }

            return altsName;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getIP(String uuid){
        try{
            String ip = null;
            PreparedStatement sts = getConnection().prepareStatement("SELECT ip FROM alts WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                ip = rs.getString("ip");
            }
            sts.close();

            return ip;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isIPBanned(String uuid, boolean perm){
        try{
            String table = (perm ? "ipbans" : "tempipbans");
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRegisteredInAlts(String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT ip FROM alts WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();

            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void registerIntoAlts(String ip, String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO alts (ip, uuid) VALUES (?, ?)");
            sts.setString(1, ip);
            sts.setString(2, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void unban(String uuid, boolean wasPermBanned){
        String table = (wasPermBanned ? "bans" : "tempbans");
        try{
            PreparedStatement sts = getConnection().prepareStatement("DELETE FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void unbanIP(String uuid, boolean wasPermBanned){
        String table = (wasPermBanned ? "ipbans" : "tempipbans");
        try{
            PreparedStatement sts = getConnection().prepareStatement("DELETE FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isPermIPBannedAnywaysBanned(String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM ipbans WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPermBanned(String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM bans WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPermIPBanned(String ip){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM ipbans WHERE bannedip = ?");
            sts.setString(1, ip);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTempIPBanned(String ip){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM tempipbans WHERE bannedip = ?");
            sts.setString(1, ip);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTempBanned(String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM tempbans WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public IPBan getIPBan(String ip){
        try{
            String actorName = "Error";
            int id = 0;
            String creatio = "Error";
            long creation = 0;
            String reason = "Unspecified";
            String punishedName = "Error";
            String uuid = "Error";
            String unitS = "Null";
            TimeUnits unit;
            int amount = 0;

            PreparedStatement sts = getConnection().prepareStatement("SELECT actorname FROM ipbans WHERE bannedip = ?");
            sts.setString(1, ip);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                actorName = rs.getString("actorname");
            }
            sts.close();

            PreparedStatement sts2 = getConnection().prepareStatement("SELECT id FROM ipbans WHERE bannedip = ?");
            sts2.setString(1, ip);
            ResultSet rs2 = sts2.executeQuery();
            while(rs2.next()){
                id = rs2.getInt("id");
            }
            sts2.close();

            PreparedStatement sts3 = getConnection().prepareStatement("SELECT creation FROM ipbans WHERE bannedip = ?");
            sts3.setString(1, ip);
            ResultSet rs3 = sts3.executeQuery();
            while(rs3.next()){
                creatio = rs3.getString("creation");
            }
            sts3.close();

            PreparedStatement sts4 = getConnection().prepareStatement("SELECT reason FROM ipbans WHERE bannedip = ?");
            sts4.setString(1, ip);
            ResultSet rs4 = sts4.executeQuery();
            while(rs4.next()){
                reason = rs4.getString("reason");
            }
            sts4.close();

            PreparedStatement sts5 = getConnection().prepareStatement("SELECT punishedname FROM ipbans WHERE bannedip = ?");
            sts5.setString(1, ip);
            ResultSet rs5 = sts5.executeQuery();
            while(rs5.next()){
                punishedName = rs5.getString("punishedname");
            }
            sts5.close();

            PreparedStatement sts6 = getConnection().prepareStatement("SELECT unit FROM ipbans WHERE bannedip = ?");
            sts6.setString(1, ip);
            ResultSet rs6 = sts6.executeQuery();
            while(rs6.next()){
                unitS = rs6.getString("unit");
            }
            sts6.close();

            PreparedStatement sts7 = getConnection().prepareStatement("SELECT amount FROM ipbans WHERE bannedip = ?");
            sts7.setString(1, ip);
            ResultSet rs7 = sts7.executeQuery();
            while(rs7.next()){
                amount = rs7.getInt("amount");
            }
            sts7.close();

            PreparedStatement sts8 = getConnection().prepareStatement("SELECT uuid FROM ipbans WHERE bannedip = ?");
            sts8.setString(1, ip);
            ResultSet rs8 = sts8.executeQuery();
            while(rs8.next()){
                uuid = rs8.getString("uuid");
            }
            sts8.close();

            creation = Long.parseLong(creatio);
            unit = TimeUnits.getByName(unitS);

            return new IPBan(actorName, uuid, id, creation, reason, punishedName, unit, amount, ip);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new IPBan("Error", "Error", 0, 0, "Unspecified", "Error", TimeUnits.SECONDS, 0, "Error");
    }

    public Ban getPermBan(String uuid){
        try{
            String actorName = "Error";
            int id = 0;
            String creatio = "Error";
            long creation = 0;
            String reason = "Unspecified";
            String punishedName = "Error";
            String unitS = "Null";
            TimeUnits unit;
            int amount = 0;

            PreparedStatement sts = getConnection().prepareStatement("SELECT actorname FROM bans WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                actorName = rs.getString("actorname");
            }
            sts.close();

            PreparedStatement sts2 = getConnection().prepareStatement("SELECT id FROM bans WHERE uuid = ?");
            sts2.setString(1, uuid);
            ResultSet rs2 = sts2.executeQuery();
            while(rs2.next()){
                id = rs2.getInt("id");
            }
            sts2.close();

            PreparedStatement sts3 = getConnection().prepareStatement("SELECT creation FROM bans WHERE uuid = ?");
            sts3.setString(1, uuid);
            ResultSet rs3 = sts3.executeQuery();
            while(rs3.next()){
                creatio = rs3.getString("creation");
            }
            sts3.close();

            PreparedStatement sts4 = getConnection().prepareStatement("SELECT reason FROM bans WHERE uuid = ?");
            sts4.setString(1, uuid);
            ResultSet rs4 = sts4.executeQuery();
            while(rs4.next()){
                reason = rs4.getString("reason");
            }
            sts4.close();

            PreparedStatement sts5 = getConnection().prepareStatement("SELECT punishedname FROM bans WHERE uuid = ?");
            sts5.setString(1, uuid);
            ResultSet rs5 = sts5.executeQuery();
            while(rs5.next()){
                punishedName = rs5.getString("punishedname");
            }
            sts5.close();

            PreparedStatement sts6 = getConnection().prepareStatement("SELECT unit FROM bans WHERE uuid = ?");
            sts6.setString(1, uuid);
            ResultSet rs6 = sts6.executeQuery();
            while(rs6.next()){
                unitS = rs6.getString("unit");
            }
            sts6.close();

            PreparedStatement sts7 = getConnection().prepareStatement("SELECT amount FROM bans WHERE uuid = ?");
            sts7.setString(1, uuid);
            ResultSet rs7 = sts7.executeQuery();
            while(rs7.next()){
                amount = rs7.getInt("amount");
            }
            sts7.close();

            creation = Long.valueOf(creatio);
            unit = TimeUnits.getByName(unitS);

            return new Ban(actorName, uuid, id, creation, reason, punishedName, unit, amount);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new Ban("Error", "Error", 0, 0L, "Unspecified", "Error", TimeUnits.SECONDS, 0);
    }

    public TempIPBan getTempIPBan(String ip){
        try{
            String actorName = "Error";
            int id = 0;
            String creatio = "Error";
            long creation;
            String expire = "Error";
            long expires;
            String reason = "Unspecified";
            String punishedName = "Error";
            String unitS = "Null";
            TimeUnits unit;
            int amount = 0;
            String uuid = "Error";

            PreparedStatement sts = getConnection().prepareStatement("SELECT actorname FROM tempipbans WHERE bannedip = ?");
            sts.setString(1, ip);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                actorName = rs.getString("actorname");
            }
            sts.close();

            PreparedStatement sts2 = getConnection().prepareStatement("SELECT id FROM tempipbans WHERE bannedip = ?");
            sts2.setString(1, ip);
            ResultSet rs2 = sts2.executeQuery();
            while(rs2.next()){
                id = rs2.getInt("id");
            }
            sts2.close();

            PreparedStatement sts3 = getConnection().prepareStatement("SELECT creation FROM tempipbans WHERE bannedip = ?");
            sts3.setString(1, ip);
            ResultSet rs3 = sts3.executeQuery();
            while(rs3.next()){
                creatio = rs3.getString("creation");
            }
            sts3.close();

            PreparedStatement sts4 = getConnection().prepareStatement("SELECT expires FROM tempipbans WHERE bannedip = ?");
            sts4.setString(1, ip);
            ResultSet rs4 = sts4.executeQuery();
            while(rs4.next()){
                expire = rs4.getString("expires");
            }
            sts4.close();

            PreparedStatement sts5 = getConnection().prepareStatement("SELECT reason FROM tempipbans WHERE bannedip = ?");
            sts5.setString(1, ip);
            ResultSet rs5 = sts5.executeQuery();
            while(rs5.next()){
                reason = rs5.getString("reason");
            }
            sts5.close();

            PreparedStatement sts6 = getConnection().prepareStatement("SELECT punishedname FROM tempipbans WHERE bannedip = ?");
            sts6.setString(1, ip);
            ResultSet rs6 = sts6.executeQuery();
            while(rs6.next()){
                punishedName = rs6.getString("punishedname");
            }
            sts6.close();

            PreparedStatement sts7 = getConnection().prepareStatement("SELECT unit FROM tempipbans WHERE bannedip = ?");
            sts7.setString(1, ip);
            ResultSet rs7 = sts7.executeQuery();
            while(rs7.next()){
                unitS = rs7.getString("unit");
            }
            sts7.close();

            PreparedStatement sts8 = getConnection().prepareStatement("SELECT amount FROM tempipbans WHERE bannedip = ?");
            sts8.setString(1, ip);
            ResultSet rs8 = sts8.executeQuery();
            while(rs8.next()){
                amount = rs8.getInt("amount");
            }
            sts8.close();

            PreparedStatement sts9 = getConnection().prepareStatement("SELECT uuid FROM tempipbans WHERE bannedip = ?");
            sts9.setString(1, ip);
            ResultSet rs9 = sts9.executeQuery();
            while(rs9.next()){
                uuid = rs9.getString("uuid");
            }
            sts9.close();

            creation = Long.parseLong(creatio);
            expires = Long.valueOf(expire);
            unit = TimeUnits.getByName(unitS);

            return new TempIPBan(actorName, uuid, id, creation, expires, reason, punishedName, unit, amount, ip);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new TempIPBan("Error", "Error", 0, 0, 0, "Unspecified", "Error", TimeUnits.SECONDS, 0, "Error");
    }

    public TempBan getTempBan(String uuid){
        try{
            String actorName = "Error";
            int id = 0;
            String creatio = "Error";
            long creation;
            String expire = "Error";
            long expires;
            String reason = "Unspecified";
            String punishedName = "Error";
            String unitS = "Null";
            TimeUnits unit;
            int amount = 0;

            PreparedStatement sts = getConnection().prepareStatement("SELECT actorname FROM tempbans WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                actorName = rs.getString("actorname");
            }
            sts.close();

            PreparedStatement sts2 = getConnection().prepareStatement("SELECT id FROM tempbans WHERE uuid = ?");
            sts2.setString(1, uuid);
            ResultSet rs2 = sts2.executeQuery();
            while(rs2.next()){
                id = rs2.getInt("id");
            }
            sts2.close();

            PreparedStatement sts3 = getConnection().prepareStatement("SELECT creation FROM tempbans WHERE uuid = ?");
            sts3.setString(1, uuid);
            ResultSet rs3 = sts3.executeQuery();
            while(rs3.next()){
                creatio = rs3.getString("creation");
            }
            sts3.close();

            PreparedStatement sts4 = getConnection().prepareStatement("SELECT expires FROM tempbans WHERE uuid = ?");
            sts4.setString(1, uuid);
            ResultSet rs4 = sts4.executeQuery();
            while(rs4.next()){
                expire = rs4.getString("expires");
            }
            sts4.close();

            PreparedStatement sts5 = getConnection().prepareStatement("SELECT reason FROM tempbans WHERE uuid = ?");
            sts5.setString(1, uuid);
            ResultSet rs5 = sts5.executeQuery();
            while(rs5.next()){
                reason = rs5.getString("reason");
            }
            sts5.close();

            PreparedStatement sts6 = getConnection().prepareStatement("SELECT punishedname FROM tempbans WHERE uuid = ?");
            sts6.setString(1, uuid);
            ResultSet rs6 = sts6.executeQuery();
            while(rs6.next()){
                punishedName = rs6.getString("punishedname");
            }
            sts6.close();

            PreparedStatement sts7 = getConnection().prepareStatement("SELECT unit FROM tempbans WHERE uuid = ?");
            sts7.setString(1, uuid);
            ResultSet rs7 = sts7.executeQuery();
            while(rs7.next()){
                unitS = rs7.getString("unit");
            }
            sts7.close();

            PreparedStatement sts8 = getConnection().prepareStatement("SELECT amount FROM tempbans WHERE uuid = ?");
            sts8.setString(1, uuid);
            ResultSet rs8 = sts8.executeQuery();
            while(rs8.next()){
                amount = rs8.getInt("amount");
            }
            sts8.close();

            creation = Long.parseLong(creatio);
            expires = Long.valueOf(expire);
            unit = TimeUnits.getByName(unitS);

            return new TempBan(actorName, uuid, id, creation, expires, reason, punishedName, unit, amount);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new TempBan("Error", "Error", 0, 0L, 0L, "Unspecified", "Error", TimeUnits.SECONDS, 0);
    }

    public Mute getMute(String uuid){
        try{
            String actorName = "Error";
            String bannedUUID = "Error";
            int id = 0;
            String creatio = "Error";
            long creation = 0;
            String reason = "Unspecified";
            String punishedName = "Error";
            String unitS = "Error";
            TimeUnits unit = TimeUnits.SECONDS;
            int amount = 0;

            PreparedStatement sts = getConnection().prepareStatement("SELECT actorname FROM mutes WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                actorName = rs.getString("actorname");
            }
            sts.close();

            PreparedStatement sts2 = getConnection().prepareStatement("SELECT id FROM mutes WHERE uuid = ?");
            sts2.setString(1, uuid);
            ResultSet rs2 = sts2.executeQuery();
            while(rs2.next()){
                id = rs2.getInt("id");
            }
            sts2.close();

            PreparedStatement sts3 = getConnection().prepareStatement("SELECT creation FROM mutes WHERE uuid = ?");
            sts3.setString(1, uuid);
            ResultSet rs3 = sts3.executeQuery();
            while(rs3.next()){
                creatio = rs3.getString("creation");
            }
            sts3.close();

            PreparedStatement sts4 = getConnection().prepareStatement("SELECT reason FROM mutes WHERE uuid = ?");
            sts4.setString(1, uuid);
            ResultSet rs4 = sts4.executeQuery();
            while(rs4.next()){
                reason = rs4.getString("reason");
            }
            sts4.close();

            PreparedStatement sts5 = getConnection().prepareStatement("SELECT punishedname FROM mutes WHERE uuid = ?");
            sts5.setString(1, uuid);
            ResultSet rs5 = sts5.executeQuery();
            while(rs5.next()){
                punishedName = rs5.getString("punishedname");
            }
            sts5.close();

            PreparedStatement sts6 = getConnection().prepareStatement("SELECT unit FROM mutes WHERE uuid = ?");
            sts6.setString(1, uuid);
            ResultSet rs6 = sts6.executeQuery();
            while(rs6.next()){
                unitS = rs6.getString("unit");
            }
            sts6.close();

            PreparedStatement sts7 = getConnection().prepareStatement("SELECT amount FROM mutes WHERE uuid = ?");
            sts7.setString(1, uuid);
            ResultSet rs7 = sts7.executeQuery();
            while(rs7.next()){
                amount = rs7.getInt("amount");
            }
            sts7.close();

            creation = Long.valueOf(creatio);
            unit = TimeUnits.getByName(unitS);

            return new Mute(actorName, uuid, id, creation, reason, punishedName, unit, amount);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new Mute("Error", "Error", 0, 0L, "Unspecified", "Error", TimeUnits.SECONDS, 0);
    }

    public TempMute getTempMute(String uuid){
        try{
            String actorName = "Error";
            int id = 0;
            String creatio = "Error";
            long creation;
            String expire = "Error";
            long expires;
            String reason = "Unspecified";
            String punishedName = "Error";
            String unitS = "Null";
            TimeUnits unit;
            int amount = 0;

            PreparedStatement sts = getConnection().prepareStatement("SELECT actorname FROM tempmutes WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                actorName = rs.getString("actorname");
            }
            sts.close();

            PreparedStatement sts2 = getConnection().prepareStatement("SELECT id FROM tempmutes WHERE uuid = ?");
            sts2.setString(1, uuid);
            ResultSet rs2 = sts2.executeQuery();
            while(rs2.next()){
                id = rs2.getInt("id");
            }
            sts2.close();

            PreparedStatement sts3 = getConnection().prepareStatement("SELECT creation FROM tempmutes WHERE uuid = ?");
            sts3.setString(1, uuid);
            ResultSet rs3 = sts3.executeQuery();
            while(rs3.next()){
                creatio = rs3.getString("creation");
            }
            sts3.close();

            PreparedStatement sts4 = getConnection().prepareStatement("SELECT expires FROM tempmutes WHERE uuid = ?");
            sts4.setString(1, uuid);
            ResultSet rs4 = sts4.executeQuery();
            while(rs4.next()){
                expire = rs4.getString("expires");
            }
            sts4.close();

            PreparedStatement sts5 = getConnection().prepareStatement("SELECT reason FROM tempmutes WHERE uuid = ?");
            sts5.setString(1, uuid);
            ResultSet rs5 = sts5.executeQuery();
            while(rs5.next()){
                reason = rs5.getString("reason");
            }
            sts5.close();

            PreparedStatement sts6 = getConnection().prepareStatement("SELECT punishedname FROM tempmutes WHERE uuid = ?");
            sts6.setString(1, uuid);
            ResultSet rs6 = sts6.executeQuery();
            while(rs6.next()){
                punishedName = rs6.getString("punishedname");
            }
            sts6.close();

            PreparedStatement sts7 = getConnection().prepareStatement("SELECT unit FROM tempmutes WHERE uuid = ?");
            sts7.setString(1, uuid);
            ResultSet rs7 = sts7.executeQuery();
            while(rs7.next()){
                unitS = rs7.getString("unit");
            }
            sts7.close();

            PreparedStatement sts8 = getConnection().prepareStatement("SELECT amount FROM tempmutes WHERE uuid = ?");
            sts8.setString(1, uuid);
            ResultSet rs8 = sts8.executeQuery();
            while(rs8.next()){
                amount = rs8.getInt("amount");
            }
            sts8.close();

            creation = Long.parseLong(creatio);
            expires = Long.valueOf(expire);
            unit = TimeUnits.getByName(unitS);

            return new TempMute(actorName, uuid, id, creation, expires, reason, punishedName, unit, amount);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new TempMute("Error", "Error", 0, 0L, 0L, "Unspecified", "Error", TimeUnits.SECONDS, 0);
    }

    public void unMute(String uuid, boolean perm){
        try{
            String table = (perm ? "mutes" : "tempmutes");
            PreparedStatement sts = getConnection().prepareStatement("DELETE FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPermBannedNames(){
        ArrayList<String> uuids = new ArrayList<>();
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT punishedname FROM bans");
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                uuids.add(rs.getString("punishedname"));
            }
            sts.close();
            return uuids;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return uuids;
    }

    public ArrayList<String> getTempBannedNames(){
        ArrayList<String> uuids = new ArrayList<>();
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT punishedname FROM tempbans");
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                uuids.add(rs.getString("punishedname"));
            }
            sts.close();
            return uuids;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return uuids;
    }

    public ArrayList<String> getPermIPBannedNames(){
        ArrayList<String> uuids = new ArrayList<>();
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT punishedname FROM ipbans");
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                uuids.add(rs.getString("punishedname"));
            }
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return uuids;
    }

    public ArrayList<String> getTempIPBannedNames(){
        ArrayList<String> uuids = new ArrayList<>();
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT punishedname FROM tempipbans");
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                uuids.add(rs.getString("punishedname"));
            }
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return uuids;
    }

    public ArrayList<String> getTotalBannedNames(){
        ArrayList<String> total = new ArrayList<>();
        total.addAll(getPermBannedNames());
        total.addAll(getTempBannedNames());
        total.addAll(getPermIPBannedNames());
        total.addAll(getTempIPBannedNames());
        return total;
    }

    public boolean isMuted(String uuid){
        if(isTempMuted(uuid) || isPermMuted(uuid)){
            return true;
        }
        return false;
    }

    public boolean isTempMuted(String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM tempmutes WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPermMuted(String uuid){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT id FROM mutes WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            boolean is = rs.next();
            sts.close();
            return is;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void incrementTotalPunishments(String uuid){
        if(!isRegisteredInInfos(uuid)){
            storeInInfos(uuid);
        }

        try{
            int current = getTotalPunishments(uuid);
            PreparedStatement sts = getConnection().prepareStatement("UPDATE infos SET totalpunishments = ?  WHERE uuid = ?");
            sts.setInt(1, current);
            sts.setString(2, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void incrementBans(String uuid){
        if(!isRegisteredInInfos(uuid)){
            storeInInfos(uuid);
        }

        try{
            int current = getTotalBans(uuid);
            PreparedStatement sts = getConnection().prepareStatement("UPDATE infos SET totalbans = ? WHERE uuid = ?");
            sts.setInt(1, current + 1);
            sts.setString(2, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        incrementTotalPunishments(uuid);
    }

    public void incrementMutes(String uuid){
        try{
            int current = getTotalMutes(uuid);
            PreparedStatement sts = getConnection().prepareStatement("UPDATE infos SET totalmutes = ? WHERE uuid = ?");
            sts.setInt(1, current + 1);
            sts.setString(2, uuid);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        incrementTotalPunishments(uuid);
    }

    public int getTotalBans(String uuid){
        int bans = 0;
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT totalbans FROM infos WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                bans = rs.getInt("totalbans");
            }
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return bans;
    }

    public int getTotalMutes(String uuid){
        int mutes = 0;
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT totalmutes FROM infos WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                mutes = rs.getInt("totalmutes");
            }
            sts.close();
            return mutes;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return mutes;
    }

    public int getTotalPunishments(String uuid){
        int total = getTotalBans(uuid) + getTotalMutes(uuid);
        return total;
    }

    public boolean hasBeenPunished(String uuid){
        return getTotalPunishments(uuid) > 0;
    }

    public boolean isTotalBanned(String uuid){
        if(isPermBanned(uuid) || isTempBanned(uuid) || isIPBanned(uuid, true) || isIPBanned(uuid, false)){
            return true;
        }
        return false;
    }

    public boolean isNoIPBannedAnywaysBanned(String uuid){
        if(isTempBanned(uuid) || isPermBanned(uuid)){
            return true;
        }
        return false;
    }

    public boolean isIPBanned(String ip){
        if(isPermIPBanned(ip) || isTempIPBanned(ip)){
            return true;
        }
        return false;
    }

}
