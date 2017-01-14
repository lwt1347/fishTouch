package com.example.user.mygamev101;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by USER on 2017-01-14.
 */

public class GameMain extends SurfaceView implements SurfaceHolder.Callback {

    Context _context;

    private GameThread thread; //스레드 돌릴 클래스 선언
    private boolean mRun = false; //run 함수 제어
    private SurfaceHolder mSurfaceHolder; //쓰레드 외부에서 SurfaceHolder를 얻기 위한 선언

    private BitmapDrawable image = null;//메모리 절약 기법
    //배경이미지
    private Bitmap backGroundImg = null;
    //캐릭터
    private Bitmap defaultFish_img[] = new Bitmap[2]; //기본 물고기 이미지

    //물기고 생성
    ArrayList<Fish> fishList = new ArrayList<Fish>();   //물고기를 넣을 어레이 리스트
    Fish defaultFish;   //물고기

    int tempInt = 0;
    String tempStr = "";

    public GameMain(Context context) {
        super(context);

        _context = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        thread = new GameThread(mSurfaceHolder);




        //게임 요소 추가 할 쓰레드 [물고기, 함정 등]
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);

                        addFish(); //물고기 추가

                        tempInt = fishList.size();
                        tempStr = String.valueOf(tempInt);

                        Log.d("MyView","쓰레드 시작됨" + tempStr );

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();



    }


    //물고기 생성 하는 함수
    public void addFish(){
        fishList.add(new Fish());
    }














    //내부 클래스 게임 스레드
    class GameThread extends Thread{

        DrawImage draw = new DrawImage();

        public GameThread(SurfaceHolder surfaceHolder){ //더블 버퍼링 같은것
            backGroundImg = InitBackgrounImage(_context, 0); //배경
            for(int i = 0; i < 2; i++){
                defaultFish_img[i] = InitCharacterImage(_context, i); //캐릭터 이미지 추가
            }
        }

        //배경이미지
        public Bitmap InitBackgrounImage(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ocean);
            return image.getBitmap();
        }
        //캐릭터 이미지
        public Bitmap InitCharacterImage(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fisg_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //그리기
        public void doDraw(Canvas canvas){


            draw.drawBmp(canvas, backGroundImg, 0,0);

            //물고기 그리기
            for(int i=0; i<fishList.size(); i++){
                draw.drawBmp(canvas, defaultFish_img[0], fishList.get(i).getFishMove().x,fishList.get(i).getFishMove().y );
            }




        }

        public void run(){
            while(mRun){ //게임이 동작하는 구간
                Canvas canvas = null;
                try{
                    canvas = mSurfaceHolder.lockCanvas(null);


                    synchronized (mSurfaceHolder){

                        doDraw(canvas);  //이미지 그리기 함수 호출
                        sleep(500);

                        //캐릭터 이동 등


                        Log.i("[뷰]", "쓰레드 갱신중");
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






    }

    //터치 이벤트
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("MyView","View 의 손가락이 눌렸습니다." );

            //물고기 삭제




        } else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.d("MyView","View 의 손가락이 때졌습니다.." );

        } else if(event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("MyView","View 의 손가락이 움직입니다." );
        }
        return true;
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
