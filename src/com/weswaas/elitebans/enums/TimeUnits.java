package com.weswaas.elitebans.enums;

/**
 * Created by Weswas on 06/11/2016.
 */
public enum TimeUnits {

    SECONDS("Seconds", 1000L, "s"),
    MINUTES("Minutes", 1000L*60L, "min"),
    HOURS("Hours", 60L*60L*1000L, "h"),
    DAYS("Days", 60L*60L*24L*1000L, "d"),
    WEEKS("Weeks", 60L*60L*24L*7L*1000L, "w"),
    MONTHS("Months", 60L*60L*24L*30L*1000L, "m"),
    YEARS("Years", 60L*60L*24L*30L*12L*1000L, "y"),
    INFINITE("Infinite", Long.MAX_VALUE, "i");

    private String name;
    private long duration;
    private String symbol;

    TimeUnits(String name, long duration, String symbol){
        this.name = name;
        this.duration = duration;
        this.symbol = symbol;
    }

    public String getName(){
        return this.name;
    }

    public long getDuration(){
        return this.duration;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public static TimeUnits getByName(String name){
        for(TimeUnits units : TimeUnits.values()){
            if(units.getName().equalsIgnoreCase(name)){
                return units;
            }
        }
        return null;
    }

    public static boolean isTimeUnit(String s){
        for(TimeUnits units : TimeUnits.values()){
            if(units.getSymbol().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    public static TimeUnits getBySymbol(String name){
        for(TimeUnits unit : TimeUnits.values()){
            if(unit.getSymbol().equalsIgnoreCase(name)){
                return unit;
            }
        }
        return null;
    }

}
