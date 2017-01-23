package com.example.user.mygamev101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PauseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);




    }

    public void onButton1Clicked(View v){
        finish();
    }





}
