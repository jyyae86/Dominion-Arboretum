package com.laggiss.arboretumexplorer;

import android.graphics.Color;

import com.google.maps.android.heatmaps.Gradient;

import java.util.Random;

/**
 * Created by laggi on 3/19/2016.
 */
public class HeatMapColors {

    private Gradient gradient;
    private int[] highcolorlist =
            {
                    Color.RED,
                    Color.BLUE,
                    Color.rgb(240, 59, 32),
                    Color.rgb(49, 163, 84),
                    Color.rgb(197, 27, 138),
                    Color.rgb(117, 107, 177)
            };
    private int[] lowcolorlist =
            {
                    Color.YELLOW,
                    Color.GREEN,
                    Color.WHITE,
                    Color.rgb(0, 200, 0),
                    Color.rgb(255, 237, 160),
                    Color.rgb(247, 252, 185)
            };

    public HeatMapColors() {

    }

    private static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public Gradient getGradient() {


        int[] colors = {
                lowcolorlist[randInt(0, 5)], // green
                highcolorlist[randInt(0, 5)]    // red
        };

        float[] startPoints = {
                0.05f, 0.75f//,.75f,  .85f//0.75f, 1f
        };

        gradient = new Gradient(colors, startPoints, 500);

        return gradient;
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
    }
}
