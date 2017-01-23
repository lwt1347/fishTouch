package com.example.user.mygamev101;

import java.util.Random;

/**
 * Created by USER on 2017-01-19.
 */

public class Ground_Snail extends Ground{


    int speed = 1;

    Random random;
    Ground_Snail(float groundPoint_x, int hp, int width, int height) {
        super( groundPoint_x, width, height);
        random = new Random();

        this.hp = hp;
    }


    /**
     * 달팽이를 움직인다.
     */
    @Override
    public void Moving() {
        groundPoint_y += speed;
        //groundPoint_y += groundPoint_y;
        if(Math.random() < 0.01) {
            //속도 변화 주기
            speed = random.nextInt(3);
        }

        snail_DrawStatus++;
        if(snail_DrawStatus > 7){
            snail_DrawStatus = 0;
        }

    }

    private int snail_DrawStatus = 0;

    /**
     * 물고기 헤엄칠때 드로우할 그림 int 형 반환
     */
    public int getDrawSnailStatus()
    {
        return snail_DrawStatus/2; //물고기 헤엄 이미지 2번씩 송출
    }





}
