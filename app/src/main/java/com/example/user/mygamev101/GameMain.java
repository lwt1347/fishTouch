package com.example.user.mygamev101;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by USER on 2017-01-14.
 */

public class GameMain extends SurfaceView implements SurfaceHolder.Callback {

    Context _context;

    //디스플레이 넓이 구하기
    Display display;
    public int window_Width = 0;

    private GameThread thread; //스레드 돌릴 클래스 선언
    private GameElementThread threadGameElement; //게임 요소 생성[물고기, 함정 등]쓰레드 생성

    private MainCharacter mainCharacter; //메인 캐릭터 생성

    private boolean mRun = false; //run 함수 제어
    private SurfaceHolder mSurfaceHolder; //쓰레드 외부에서 SurfaceHolder를 얻기 위한 선언

    private BitmapDrawable image = null;//메모리 절약 기법
    //배경이미지
    private Bitmap backGroundImg = null;

    //Hp1 물고기
    private Bitmap defaultFishHp1_img[] = new Bitmap[4]; //기본 물고기 이미지
    //Hp2 물고기
    private Bitmap defaultFishHp2_img[] = new Bitmap[4]; //기본 물고기 이미지
    //Hp3 물고기
    private Bitmap defaultFishHp3_img[] = new Bitmap[4]; //기본 물고기 이미지
    //Hp4 물고기
    private Bitmap defaultFishHp4_img[] = new Bitmap[4]; //기본 물고기 이미지
    //Hp5 물고기
    private Bitmap defaultFishHp5_img[] = new Bitmap[4]; //기본 물고기 이미지


    //그라운드 생물체
    private Bitmap ground_SnailHp1_img[] = new Bitmap[4]; //달팽이
    private Bitmap ground_SnailHp2_img[] = new Bitmap[4]; //달팽이
    private Bitmap ground_SnailHp3_img[] = new Bitmap[4]; //달팽이
    private Bitmap ground_SnailHp4_img[] = new Bitmap[4]; //달팽이
    private Bitmap ground_SnailHp5_img[] = new Bitmap[4]; //달팽이





    //흔들어야 죽는 물고기 이미지
    private Bitmap shakeFish_img[] = new Bitmap[4];    //1번 물고기 이미지

    //메인 캐릭터 이미지
    private Bitmap mainCharacter_img[] = new Bitmap[3]; //메인 캐릭터

    //성게 이미지
    private Bitmap attackFish_img[] = new Bitmap[3]; //기본 물고기 이미지

    //회전 물고기 비트맵 템프 변수
    private Bitmap tempFish = null;

    //달팽이 비트맵 탬프 변수
    private Bitmap tempGround = null;


    //이펙트 오렌지 변수
    private Bitmap effect_Orange_img[] = new Bitmap[5];
    private Bitmap effect_Blue_img[] = new Bitmap[5];
    private Bitmap effect_Yellow_img[] = new Bitmap[5];
    private Bitmap effect_Green_img[] = new Bitmap[5];

    private Bitmap effect_Pop_img[] = new Bitmap[5];

    //물기고 생성
    ArrayList<Fish> fishList = new ArrayList<Fish>();   //물고기를 넣을 어레이 리스트



    int tempInt = 0;
    String tempStr = "";


    public GameMain(Context context) {
        super(context);

        _context = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        thread = new GameThread(mSurfaceHolder);

        //게임 요소 추가 할 쓰레드 [물고기, 함정 등]
        threadGameElement = new GameElementThread();
        threadGameElement.start();

        //메인캐릭터 생성
        mainCharacter = new MainCharacter();


    }




    /**
     * 물고기 생성하는 함수
     */
    Fish fish;
    Fish_Shake fish_Shake;
    public void addFish(){

        fish = new Fish(0, window_Width, 5);
        fishList.add(fish); //기본 밥 물고기 생성, 윈도우 크기 알려줌

        fish_Shake = new Fish_Shake(1, window_Width); //흔들어야 죽는 물고기 (물고기 종류, 윈도우 크기)
        fishList.add(fish_Shake);
        //fish_Shake.startFishMove();
    }

    /**
     * 물고기 움직임 -> 물고기 각 쓰레드를 주게 되면 부하가 심하대 따라서 한 함수로 모든 물고기를 제어한다.
     */
    public void fishMove(){
        for(int i=0; i<fishList.size(); i++){
            fishList.get(i).fish_Object_Move();
        }
    }

    /**
     * 그라운드 생성구간 (달팽이)
     */
    ArrayList<Ground> ground = new ArrayList<Ground>();   //물고기를 넣을 어레이 리스트
    Ground_Snail ground_snail;
    public void addGround_Snail(){
        ground_snail = new Ground_Snail((float)Math.random() * (window_Width-100), 5);
        ground.add(ground_snail); //달팽이 생성
    }

    /**
     * 그라운드 움직임
     */
    public void groundMove(){
        for(int i=0; i<ground.size(); i++){
            ground.get(i).Moving();
        }
    }





    //내부 클래스 게임 요소 추가 //게임 요소 추가 할 쓰레드 [물고기, 함정 등]
    class GameElementThread extends Thread{
        @Override
        public synchronized void run() {
            while(true){
                try {
                    Thread.sleep(3500);

                    addFish(); //물고기 추가

                    addGround_Snail();//달팽이 추가


                    //tempInt = fishList.size();
                    //tempStr = String.valueOf(tempInt);
                    //Log.d("MyView","쓰레드 시작됨" + tempStr );

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    DrawImage draw = new DrawImage();
    //내부 클래스 게임 스레드
    class GameThread extends Thread{



        public GameThread(SurfaceHolder surfaceHolder){ //더블 버퍼링 같은것
            backGroundImg = Init_Background_Image(_context, 0); //배경

            for(int i = 0; i < 4; i++) {
                defaultFishHp1_img[i] = Init_Hp1_Fish_Image(_context, i); //캐릭터 이미지 추가 hp = 1
                defaultFishHp2_img[i] = Init_Hp2_Fish_Image(_context, i); //캐릭터 이미지 추가
                defaultFishHp3_img[i] = Init_Hp3_Fish_Image(_context, i); //캐릭터 이미지 추가
                defaultFishHp4_img[i] = Init_Hp4_Fish_Image(_context, i); //캐릭터 이미지 추가
                defaultFishHp5_img[i] = Init_Hp5_Fish_Image(_context, i); //캐릭터 이미지 추가 hp = 5

                ground_SnailHp1_img[i] = Init_Snail_Hp1_Image(_context, i);  //달팽이 이미지
                ground_SnailHp2_img[i] = Init_Snail_Hp2_Image(_context, i);  //달팽이 이미지
                ground_SnailHp3_img[i] = Init_Snail_Hp3_Image(_context, i);  //달팽이 이미지
                ground_SnailHp4_img[i] = Init_Snail_Hp4_Image(_context, i);  //달팽이 이미지
                ground_SnailHp5_img[i] = Init_Snail_Hp5_Image(_context, i);  //달팽이 이미지



                shakeFish_img[i] = Init_ShakeFish_Image(_context, i);
            }

            for(int i = 0; i < 3; i++){
                mainCharacter_img[i] = Init_MainCharacter_Image(_context, i); //메인 캐릭터
                attackFish_img[i] = Init_Attack_Fish_Image(_context, i);//성게 이미지

            }
            for(int i=0; i<5; i++){
                effect_Orange_img[i] = Init_Effect_Orange_Image(_context, i); //이펙트
                effect_Blue_img[i] = Init_Effect_Blue_Image(_context, i);
                effect_Yellow_img[i] = Init_Effect_Yellow_Image(_context, i);
                effect_Green_img[i] = Init_Effect_Green_Image(_context, i);
                effect_Pop_img[i] = Init_Effect_Pop_Image(_context, i);
            }

        }

        //배경이미지
        public Bitmap Init_Background_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.background);
            return image.getBitmap();
        }
        //물고기 hp1 이미지
        public Bitmap Init_Hp1_Fish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp2 이미지
        public Bitmap Init_Hp2_Fish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp2_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp3 이미지
        public Bitmap Init_Hp3_Fish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp3_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp4 이미지
        public Bitmap Init_Hp4_Fish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp4_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp5 이미지
        public Bitmap Init_Hp5_Fish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp5_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //달팽이 이미지
        public Bitmap Init_Snail_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snailhp1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Snail_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snailhp2_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Snail_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snailhp3_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Snail_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snailhp4_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Snail_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snailhp5_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }






        //메인 캐릭터 이미지
        public Bitmap Init_MainCharacter_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.mainch_1); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //성게 이미지
        public Bitmap Init_Attack_Fish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.attackfish_1); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //흔들면 죽는 물고기 이미지
        public Bitmap Init_ShakeFish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_shake_1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }





        //이펙트 효과 4가지
        public Bitmap Init_Effect_Orange_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_orange_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Blue_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_blue_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Yellow_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_yellow_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Green_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_green_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_1 + num);
            return image.getBitmap();
        }







        //그리기
        public void doDraw(Canvas canvas){


            draw.drawBmp(canvas, backGroundImg, 0,0);

            /**
             *  물고기 그리기
             */

            for(int i=0; i<fishList.size(); i++){
                if(fishList.get(i).getFishClass() == 0) {
                    /**
                     *  이미지 회전
                     */
                if(fishList.get(i).getFishHp() == 1) {
                    tempFish = draw.rotateImage(defaultFishHp1_img[fishList.get(i).getDrawFishStatus()], -fishList.get(i).getAngle());
                }else if(fishList.get(i).getFishHp() == 2){
                    tempFish = draw.rotateImage(defaultFishHp2_img[fishList.get(i).getDrawFishStatus()], -fishList.get(i).getAngle());
                }else if(fishList.get(i).getFishHp() == 3){
                    tempFish = draw.rotateImage(defaultFishHp3_img[fishList.get(i).getDrawFishStatus()], -fishList.get(i).getAngle());
                }else if(fishList.get(i).getFishHp() == 4){
                    tempFish = draw.rotateImage(defaultFishHp4_img[fishList.get(i).getDrawFishStatus()], -fishList.get(i).getAngle());
                }else if(fishList.get(i).getFishHp() == 5){
                    tempFish = draw.rotateImage(defaultFishHp5_img[fishList.get(i).getDrawFishStatus()], -fishList.get(i).getAngle());
                }
                draw.drawBmp(canvas, tempFish, fishList.get(i).getFishPoint_X(), fishList.get(i).getFishPoint_Y());

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                }else if(fishList.get(i).getFishClass() == 5){  //성게
                    draw.drawBmp(canvas, attackFish_img[0], fishList.get(i).getFishPoint_X(), fishList.get(i).getFishPoint_Y());

                    /**
                     * 드래그로 죽이는 물고기
                     */
                }else if(fishList.get(i).getFishClass() == 1){
                    tempFish = draw.rotateImage(shakeFish_img[fishList.get(i).getDrawFishStatus()], -fishList.get(i).getAngle());
                    draw.drawBmp(canvas, tempFish, fishList.get(i).getFishPoint_X(), fishList.get(i).getFishPoint_Y());
                }


            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            /**
             * 그라운드 그리기 (달팽이)
             */
            for(int i=0; i<ground.size(); i++){

                //달팽이 움직임
                if(ground.get(i) instanceof Ground_Snail){

                    if(ground.get(i).get_Ground_Hp() == 5){
                        draw.drawBmp(canvas, ground_SnailHp5_img[((Ground_Snail) ground.get(i)).getDrawSnailStatus()], ground.get(i).get_Ground_Point_x(), ground.get(i).get_Ground_Point_y());
                    }else if(ground.get(i).get_Ground_Hp() == 4){
                        draw.drawBmp(canvas, ground_SnailHp4_img[((Ground_Snail) ground.get(i)).getDrawSnailStatus()], ground.get(i).get_Ground_Point_x(), ground.get(i).get_Ground_Point_y());
                    }else if(ground.get(i).get_Ground_Hp() == 3){
                        draw.drawBmp(canvas, ground_SnailHp3_img[((Ground_Snail) ground.get(i)).getDrawSnailStatus()], ground.get(i).get_Ground_Point_x(), ground.get(i).get_Ground_Point_y());
                    }else if(ground.get(i).get_Ground_Hp() == 2){
                        draw.drawBmp(canvas, ground_SnailHp2_img[((Ground_Snail) ground.get(i)).getDrawSnailStatus()], ground.get(i).get_Ground_Point_x(), ground.get(i).get_Ground_Point_y());
                    }else if(ground.get(i).get_Ground_Hp() == 1){
                        draw.drawBmp(canvas, ground_SnailHp1_img[((Ground_Snail) ground.get(i)).getDrawSnailStatus()], ground.get(i).get_Ground_Point_x(), ground.get(i).get_Ground_Point_y());
                    }


                }


                /*
                paint.setStrokeWidth(i * 2);

                canvas.drawRect(
                        ground.get(i).get_Ground_Point_x()
                        , ground.get(i).get_Ground_Point_y()
                        , ground.get(i).get_Ground_Point_x() + 75
                        , ground.get(i).get_Ground_Point_y() + 140
                        , paint);*/
            }


            /**
             * 메인 캐릭터 그리기
             */
            draw.drawBmp(canvas, mainCharacter_img[0], mainCharacter.getChPoint().x,mainCharacter.getChPoint().y);

        }
        Paint paint = new Paint();
        /**
         * 게임이 동작하는 구간
         */
        public void run(){
            while(mRun){
                //canvas = null;
                try{
                    canvas = mSurfaceHolder.lockCanvas(null);


                    synchronized (mSurfaceHolder){

                        /**
                         * 그림 그리기 구간
                         */
                        doDraw(canvas);
                        sleep(25);

                        //캐릭터 이동 등

                        deleteFish();

                        //물고기 움직임을 하나의 쓰레드로 작동한다.
                        fishMove();

                        //그라운드 움직임을 하나의 쓰레드로 작동합니다.
                        groundMove();

                        //Log.i("[뷰]", "쓰레드 갱신중");


                        //문어 공격 스피드에 따라서 터치 이벤트 제어
                        if(mainCharacter.get_Attack_Cool_time() != 0){
                            mainCharacter.set_Attack_Cool_Time();
                        }

                    }


                }catch (Exception e){

                }
                finally {
                    if(canvas != null){
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }



        public boolean setRun(boolean run){
            mRun = run;
            return mRun;
        }

        //물고기  삭제
        public void deleteFish(){
            for(int i=0; i<fishList.size();i++){


                //물고기 피가 0 이면 피 검사후에 피가 0 이면
                if(fishList.get(i).getFishHp() == 0){

                    fishList.remove(i);
                    break;
                }


                //물고기가 y축 으로 넘어가면 삭제
                if(fishList.get(i).getFishPoint_Y() >= getHeight() - 30){
                    fishList.remove(i);
                    break;
                }

            }
        }


    }


    Canvas canvas;


    /*
    //물고기 Hp 깍기 메인캐릭터의 공격력
    Paint paint = new Paint();
    ArrayList<Point> damage_Count_View = new ArrayList<Point>(); //공격시 공격력 표시를 자연스럽게 해주기 위해서
    Point fish_Point = new Point();
    */
    //원 이벤트 넣기 위해서

    ArrayList<Float> circly_X_Draw = new ArrayList<Float>(); //지워지는 물고기 위치에 이펙트 넣어야한다.
    ArrayList<Float> circly_Y_Draw = new ArrayList<Float>();
    ArrayList<Integer> effectTempRandomTemp = new ArrayList<Integer>(); //이펙트 효과를 위한 랜덤 리스트

    //어떤 이펙트를 넣을것인가 랜덤 변수
    Bitmap effectTemp;
    Random random = new Random();

    //드래그 이벤트 너무 빨리 일어나지 않도록 변수
    int drag_ACTION_MOVE = 0;

    /**
     * 달팽이 삭제
     */
    public boolean deleteGround(float x, float y){

        for(int i=0; i<ground.size(); i++){



            if(x >= ground.get(i).get_Ground_Point_x()
                    && x <= ground.get(i).get_Ground_Point_x() + ground.get(i).get_GroundPoint_Width()
                    && y >= ground.get(i).get_Ground_Point_y()
                    && y <= ground.get(i).get_Ground_Point_y() + ground.get(i).get_GroundPoint_Height()){

                //클릭된 달팽이의체력을 깍고 0 보다 작거나 같으면 삭제
                ground.get(i).set_Ground_Hp_Minus();
                if(ground.get(i).get_Ground_Hp() <= 0) {
                    ground.remove(i);
                }
                return true;

            }

        }
        return false;

    }


    //터치 이벤트
    @Override
    public synchronized boolean onTouchEvent(MotionEvent event) {

        //터치 이벤트 하고 물고기 생성 쓰레드가 엮여서 순서가 바뀔수도 있다.

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("MyView","View 의 손가락이 눌렸습니다." );


            //달팽이 체크 먼저 한다. 누른곳의 좌표가 달팽이와 겹치면 달팽이먼저 제거
            Log.d("MyView","좌표 x = " + event.getX() + "y =  " +event.getY());


            //달팽이 삭제
            if(deleteGround(event.getX(), event.getY())){

            }else {
                //문어 공격 속도로 제어한다. 쿨타임 효과
                if (mainCharacter.get_Attack_Cool_time() == 0) {
                    remove_Fish_chose(0);
                    mainCharacter.set_Attack_Cool_Time();
                }
            }







        } else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.d("MyView","View 의 손가락이 때졌습니다.." );

        } else if(event.getAction() == MotionEvent.ACTION_MOVE){ //이때 기본 물고기 안뒤지게 해야함

                Log.d("MyView", "a");

            drag_ACTION_MOVE++;
            if(drag_ACTION_MOVE > 2) {
                remove_Fish_chose(1);
                drag_ACTION_MOVE = 0;
            }



        }


        return true;
    }

    //터치 이벤트시 물고기 클래스 넘버를 받아서 상황에 맞추어서 삭제
    public void remove_Fish_chose(final int fish_Class){
        try {
            //물고기 삭제 1번 먼저
            if(fishList.size() != 0){ //물고기가 존재할때 눌러짐
                eraser_Fish = false;


                for(int i=0; i<fishList.size(); i++){

                    //전달받은 물고기 인자가 아닐때 생깜
                    if(fishList.get(i).getFishClass() != fish_Class){
                        continue;
                    }

                    //대각선 길이를 통해 가장 가까운 거리를 찾는다.
                    smallMathResult = pythagoras(fishList.get(i).getFishPoint_X() , fishList.get(i).getFishPoint_Y());


                    if(smallMathResult < smallFishTemp){
                        smallFishTemp = smallMathResult;
                        smallFishIndex = i; //for 문안에서 가장 가까운 물고기를 찾는다.
                        eraser_Fish = true;
                    }
                }

                smallFishTemp = 1000; //제일 가까운 물고기 찾기위한 템프변수




                if(eraser_Fish) {

                    //물고기 터치할때 이벤트 발생
                    //draw.drawBmp(canvas, defaultFish_img[0]  , fishList.get(smallFishIndex).getFishPoint_X() , fishList.get(smallFishIndex).getFishPoint_Y());

                    //이펙트 그리기
                    circly_X_Draw.add(fishList.get(smallFishIndex).getFishPoint_X().floatValue());
                    circly_Y_Draw.add(fishList.get(smallFishIndex).getFishPoint_Y().floatValue());
                    effectTempRandomTemp.add(random.nextInt(4));

                    //paint.setStyle(Paint.Style.STROKE);
                    //paint.setColor(Color.argb(255, 255, 255, 0));

                    //지워지는 좌표 어레이 리스트로 받아서 그 위치에 쓰레드 돌림
                    new Thread(new Runnable() {

                        @Override
                        public void run() {


                            if(fish_Class == 0){ //기본 물고기 일때만 이펙트 효과
                                for (int i = 0; i < 5; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    for (int j = 0; j<circly_X_Draw.size(); j++) {


                                       /* paint.setStrokeWidth(i * 2);
                                        canvas.drawCircle(
                                                circly_X_Draw.get(j)
                                                , circly_Y_Draw.get(j)

                                                , i * 15, paint);*/
                                        //랜덤 이팩트
                                        if(effectTempRandomTemp.get(0) == 0) {
                                            effectTemp = effect_Orange_img[i];
                                        }else if(effectTempRandomTemp.get(0) == 1){
                                            effectTemp = effect_Blue_img[i];
                                        }else if(effectTempRandomTemp.get(0) == 2){
                                            effectTemp = effect_Yellow_img[i];
                                        }else{
                                            effectTemp = effect_Green_img[i];
                                        }



                                        draw.drawBmp(canvas, effectTemp, circly_X_Draw.get(j),circly_Y_Draw.get(j));

                                    }
                                }
                            }



                            circly_X_Draw.remove(0);
                            circly_Y_Draw.remove(0);
                            effectTempRandomTemp.remove(0);
                        }
                    }).start();

                    //fishList.remove(smallFishIndex); //가장 가까운 물고기 삭제 / 성게가 아니고 가장 가까운 물고기 부터 삭제하는 루틴을 만들어야함
                    if(fishList.get(smallFishIndex).getFishHp() > 0) { //드래그 속도까 빨라서 0밑으로내려감 방지
                        fishList.get(smallFishIndex).steHpMinus(); //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                        if(fish_Class == 1) {
                            //드래그시 공격당한다는 느낌 주기 위해
                            draw.drawBmp(canvas, effect_Pop_img[random.nextInt(6)],
                                    fishList.get(smallFishIndex).getFishPoint_X() + random.nextInt(50),
                                    fishList.get(smallFishIndex).getFishPoint_Y() + random.nextInt(30));
                        }
                    }
                }else {
                    Log.i("발 터짐","밭 터짐");
                }
            }

        }catch (Exception e){

        }
    }



    //두점 사이의 거리를 구하기위한 변수
    double pointXBig = 0;
    double pointXSmall = 0;
    double pointYBig = 0;
    double pointYSmall = 0;
    double smallFishTemp = 1000; //가장 가까운 물고기 찾기 위한 변수
    double smallMathResult = 0;  //가장 가까운 물고기 찾기 위한 변수
    int smallFishIndex = 0; //
    boolean eraser_Fish = false; //물고기를 지우기 허가가 떨어졌을때

    //피타 고라스 함수 정의, 핸드폰 최하단 좌표 400, 1000 이랑 비교
    public double pythagoras(double x, double y){

        //피타고라스 정의 사용하기 위해 큰 x,y 값 도출
        if(400 >= x){
            pointXBig = 400;
            pointXSmall = x;
        }else if(400 <= x){
            pointXBig = x;
            pointXSmall = 400;
        }


        if(1000 >= y){
            pointYBig = 1000;
            pointYSmall = y;
        }else if(1000 <= y){
            pointYBig = y;
            pointYSmall = 1000;
        }



        return Math.sqrt(Math.pow((pointXBig - pointXSmall), 2) + Math.pow((pointYBig - pointYSmall), 2));
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("[뷰]", "구성");
        if(thread.getState() == Thread.State.TERMINATED) {
            thread = new GameThread(holder);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
            thread.start(); //게임

        }else {
            thread.start();
            //gameMainThread.start();
        }
        thread.setRun(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("[뷰]", "교체");
        window_Width = width; //화면의 크기
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("[뷰]", "파괴");
        try{
            thread.join(); //쓰레드 종료
            //gameMainThread.join();
        }catch (Exception e){

        }
        thread.setRun(false);
    }

}
































//
