package com.ht.baidumapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by annuo on 2015/5/20.
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void basicOnClick(View view) {
        Intent intent = new Intent(this, BasicuseActivity.class);
        startActivity(intent);

    }

    public void mylocOnClick(View view) {
        Intent intent = new Intent(this, MyLocActivity.class);
        startActivity(intent);


    }


    public void routeplanOnClick(View view) {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    public void buslineOnClick(View view) {
        Intent intent = new Intent(this, FindBusActivity.class);
        startActivity(intent);
    }
}