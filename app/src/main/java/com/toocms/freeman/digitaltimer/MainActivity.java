package com.toocms.freeman.digitaltimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DigitalTimer digitalTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        digitalTimer = (DigitalTimer) findViewById(R.id.dt);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitalTimer.setBaseTime("2017-07-08 13:13:42");
                digitalTimer.start();
            }
        });
    }
}
