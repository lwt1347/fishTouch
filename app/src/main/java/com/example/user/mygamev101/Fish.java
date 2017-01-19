package com.example.user.mygamev101;

import java.util.Random;

/**
 * Created by USER on 2017-01-14.
 */


/*
* 기본 물고기 (먹이, 함정 등으로 발전)
*
 */
public class Fish {


    protected Double fishPoint_x;  //물고기 생성될 좌표
    protected Double fishPoint_y;

    //private FishMove fishMove; //물고기 움직임 쓰레드

    private int fishClass; //물고기마다 특성 정하기

    protected int hp; //Hp 마다 물고기 색깔 달라지게 할것

    Random random;

    //물고기 패턴 변수
    int angle = 0; // 물고기 각도
    int angleSpeed = 1; //물고기 각도 스피드 조절 좌우 속도증가
    double fishSpeed = 1; //스피드

    int window_Width = 0;
    Fish(int fishClass,int window_Width, int hp){ //0 = 밥, 5 = 성게

        random = new Random();
        fishPoint_x = 10 + Math.random() * (window_Width-100);  //600 = 화면넓이
        this.window_Width = window_Width; //화면 넘어갈때 방향반대
        fishPoint_y = Math.random() * 30 - 30;

        //물고기 각도
        angle = random.nextInt(180) - 90;
        fishSpeed = 2 + Math.random() * 3;//물고기 스피드


        this.hp = hp;
        this.fishClass = fishClass;
    }


    /**
     * 물고기 hp 깍기 및 반환
     */
    public void steHpMinus(){
        hp--;
    }
    public int getFishHp(){
        return hp;
    }

    private int fishDrawStatus = 0;

    /**
     * 물고기 헤엄칠때 드로우할 그림 int 형 반환
     */
    public int getDrawFishStatus()
    {
        return fishDrawStatus/2; //물고기 헤엄 이미지 2번씩 송출
    }




    /**
     * 물고기 좌표를 반환한다.
     */
    public Double getFishPoint_X(){
        return fishPoint_x;
    }
    public Double getFishPoint_Y(){
        return fishPoint_y;
    }


    /**
     * 물고기 종류를 반환한다.
     */
    public int getFishClass(){
        return fishClass;
    }


    /**
     * 물고기 패턴 변경
     */
    protected void fishPatternChange(){
        angle = random.nextInt(180) - 90;
        angleSpeed = random.nextInt(3);

        //각도에 따라 속도 조절
        if(angle < -45){
            fishSpeed = 2 + Math.random() * 3;
        }else if(angle < 45){
            fishSpeed = 2 + Math.random() * 5;
        }else {
            fishSpeed = 2 + Math.random() * 3;
        }

        //fishSpeed = Math.random() * 3;

    }

    /**
     * 물고기 각도 가져오기
     */
    public int getAngle(){

        return angle;
    }

    /**
     * 물고기를 움직인다.
     */
    public void fish_Object_Move(){
        fishPoint_y += fishSpeed;
        fishPoint_x += (Math.sin(angle * Math.PI / 180)) * angleSpeed;

        if(Math.random() < 0.01) {
            fishPatternChange();
        }

        //왼쪽 오른쪽을 넘어가면 방향 반대로
        if(fishPoint_x <= 0 || fishPoint_x >= window_Width - 150){
            angleSpeed = angleSpeed*-1;
        }

        //헤엄치면서 물고기 그림 상태 변형
        fishDrawStatus++;
        if(fishDrawStatus > 7){

            fishDrawStatus = 0;

        }
    }



}




















//