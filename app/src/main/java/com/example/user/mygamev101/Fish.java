package com.example.user.mygamev101;

import android.graphics.Point;

import java.util.Random;

/**
 * Created by USER on 2017-01-14.
 */


/*
* 기본 물고기 (먹이, 함정 등으로 발전)
*
 */
public class Fish {

    Point fishPoint; //물고기 생성될 좌표

    Fish(){
        fishPoint = new Point();
        Random random = new Random();
        fishPoint.x = random.nextInt(300);
        fishPoint.y = random.nextInt(300);


    }

    /**
     * 물고기 좌표를 변경한다.
     */
    public Point getFishMove(){
        return fishPoint;
    }

}
