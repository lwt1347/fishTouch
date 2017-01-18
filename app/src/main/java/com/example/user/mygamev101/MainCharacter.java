package com.example.user.mygamev101;

import android.graphics.Point;

/**
 * Created by USER on 2017-01-14.
 */

public class MainCharacter {

    Point chPoint;

    public MainCharacter(){
        chPoint = new Point();
        chPoint.x = 300;
        chPoint.y = 700;
    }

    public Point getChPoint(){
        return chPoint;
    }

}
