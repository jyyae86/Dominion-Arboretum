package com.laggiss.dualmapfragment;

/**
 * Created by Laggiss on 7/5/2015.
 */
import com.google.android.gms.maps.model.LatLng;


public class LatLongOffset {

    private double minLat;
    private double maxLat;
    private double minLong;
    private double maxLong;
    private LatLng inLL;

    public LatLongOffset(LatLng ll,double dist) {
        inLL = ll;
        double latOff;
        double longOff;
        double rlat =inLL.latitude;
        double rLng =inLL.longitude;

        double mDegLat = 111132.92 - 559.82 * Math.cos(2 * Math.toRadians(rlat)) + 1.175*Math.cos(4*Math.toRadians(rlat));
        latOff=dist/mDegLat;

        double mDegLong=111412.84 * Math.cos(Math.toRadians(rlat)) - 93.5 * Math.cos(3 * Math.toRadians(rlat));
        longOff = dist/mDegLong;

        minLat=rlat-latOff;
        maxLat=rlat+latOff;
        minLong=rLng-longOff;
        maxLong=rLng+longOff;
    }


    public double getMaxLat() {
        return maxLat;
    }

    public double getMaxLong() {
        return maxLong;
    }

    public double getMinLat() {
        return minLat;
    }

    public double getMinLong() {
        return minLong;
    }
}
