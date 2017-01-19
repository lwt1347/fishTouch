package com.example.user.mygamev101;

/**
 * Created by USER on 2017-01-19.
 */

//달팽이등 바닥에서 기어 다니는 애들
public class Ground {

    protected int hp;
    protected float groundPoint_x;  //물고기 생성될 좌표
    protected float groundPoint_y;
    protected int width;
    protected int height;

    Ground( float groundPoint_x){

        this.groundPoint_x = groundPoint_x;
        this.groundPoint_y = groundPoint_y;

    }

    public void Moving(){

    }

    public float get_Ground_Point_x(){
        return groundPoint_x;
    }
    public float get_GroundPoint_Width(){
        return width;
    }

    public float get_Ground_Point_y(){
        return groundPoint_y;
    }
    public float get_GroundPoint_Height(){
        return height;
    }

    //체력깍기
    public void set_Ground_Hp_Minus(){
        hp--;
    }
    public int get_Ground_Hp(){
        return hp;
    }


}
