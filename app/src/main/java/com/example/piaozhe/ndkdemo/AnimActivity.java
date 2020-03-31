package com.example.piaozhe.ndkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.lang.String;

import com.example.piaozhe.ndkdemo.animui.StartAnimActivity;

public class AnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
    }

    public void startAnimBtn(View view) {
        startActivity(new Intent(AnimActivity.this, StartAnimActivity.class));
    }
}
