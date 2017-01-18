package com.example.user.mygamev101;

/**
 * Created by USER on 2017-01-14.
 */

public class Fish_Attack extends Fish {

    private FishMove fishMove; //물고기 움직임 쓰레드

    Fish_Attack(int fishClass, int window_Width) {
        super(fishClass, window_Width, 1);
    }

    /**
     * 물고기 움지기이기 시작
     */
    public void startFishMove(){
        //쓰레드를 사용하는 방법
        fishMove = new FishMove();
        fishMove.start();
    }

    class FishMove extends Thread{
        @Override
        public void run() {
            while (true){

                try {
                    Thread.sleep(10);
                    fishPoint_y ++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
