package com.example.user.mygamev101;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButton1Clicked(View v){

        //Toast.makeText(getApplicationContext(),"a",Toast.LENGTH_SHORT).show();
        //게임 시작 버튼을 눌렀을때 셋팅 정보를 넘긴다.
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("cha","aa");
        startActivityForResult(intent, 1001);

    }


}
