package com.weswaas.elitebans.data;

/**
 * Created by Weswas on 06/11/2016.
 */
public interface Temporary {

    public long getExpires();

    public boolean hasExpired();

}
