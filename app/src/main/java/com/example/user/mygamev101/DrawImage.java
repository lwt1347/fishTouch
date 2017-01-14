package com.example.user.mygamev101;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by USER on 2017-01-14.
 */

public class DrawImage {

    //이미지 그리기
    public void drawBmp(Canvas cvs, Bitmap bitmap, float x, float y){
        if(bitmap != null){
            cvs.drawBitmap(bitmap, x, y, null);
        }
    }

    //이미지 회전
    public void drawRotate(Canvas cvs, Bitmap bitmap, Rect show, Rect point, Paint p, float m){
        Matrix matrix = new Matrix();
        matrix.setRotate(m);
        Bitmap change_bitmap = Bitmap.createBitmap(bitmap, show.left, show.top, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if(bitmap != change_bitmap){
            bitmap = change_bitmap;
        }
        cvs.drawBitmap(bitmap, show, point, p);
        change_bitmap.recycle();
    }

    //이미지 자르기
    public  void drawPiece(Canvas cvs, Bitmap bitmap, Rect show, Rect point, Paint p){
        cvs.drawBitmap(bitmap, show,point, p); //show = 원본 이미지에서 조각낼 부분/ point = 조각이 그려질 위치
    }

}
