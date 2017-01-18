package com.example.user.mygamev101;

import android.graphics.Point;

/**
 * Created by USER on 2017-01-14.
 */

public class MainCharacter {

    Point chPoint;
    int damage = 1;
    int attach_Speed = 9; //최종 진화 = 1
    int attack_Cool_Time = 0;

    public MainCharacter(){
        chPoint = new Point();
        chPoint.x = 300;
        chPoint.y = 700;
    }

    //터치후 쿨타임 돌기
    public void set_Attack_Cool_Time(){
        attack_Cool_Time++;
        if(attack_Cool_Time >= attach_Speed){
            attack_Cool_Time = 0;
        }
    }
    public int get_Attack_Cool_time(){
        return attack_Cool_Time;
    }

    public int get_Attack_Speed(){
        return attach_Speed;
    }

    public void set_Attack_Speed(){
        attach_Speed--;
    }

    public int get_Damage(){
        return damage;
    }

    public void set_Damage_Up(){
        damage++;
    }

    public Point getChPoint(){
        return chPoint;
    }

}
