package com.example.user.mygamev101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);


        //인텐트 받는 작업을 수행하고

        //게임메인 부를 켠다. 게임이 동작하는 구간.
        GameMain _gGameMain = new GameMain(this);
        _gGameMain.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.0f
        ));
        setContentView(_gGameMain);

    }





}
