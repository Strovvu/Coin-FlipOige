package com.example.opilane.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String STATE_COUNTER = "counter";

    private int mCounter;
    private GestureDetector gestureDetector;

    private ImageView empty,arrow;
    private TextView slide;
    private ArrowHandler arrowHandler;

    private RadioGroup radio;
    private ImageView coin,match;
    private TextView prediction,outcome,current,high,sequence;
    private CoinHandler coinHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!= null){
            mCounter = savedInstanceState.getInt(STATE_COUNTER,0);
        }
        setContentView(R.layout.activity_main);
        onDetectGesture();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt(STATE_COUNTER,mCounter);
    }


    private void onDetectGesture(){
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        gestureDetector = new GestureDetector(this, customGestureDetector);



        empty = (ImageView) findViewById(R.id.empty_image_view);
        empty.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }


    class CustomGestureDetector  extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (coinHandler.getAllowance()) {
                coinHandler.rollCoin();
                coinHandler.flipCoin();
                arrowHandler.stopArrow();
                slide.setText("");
            }
            return true;
        }

    }


    @Override
    protected void onStart(){
        super.onStart();
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void init(){

        empty = findViewById(R.id.empty_image_view);
        arrow = findViewById(R.id.arrow_image_view);
        coin = findViewById(R.id.coin_image_view);
        slide = findViewById(R.id.slide_text_view);
        match = findViewById(R.id.match_image_view);
        prediction = findViewById(R.id.predict_text_view);
        outcome = findViewById(R.id.outcome_text_view);
        current = findViewById(R.id.current_text_view);
        high = findViewById(R.id.high_text_view);
        radio = findViewById(R.id.predict_radio_group);
        sequence = findViewById(R.id.sequenceTextView);

        arrowHandler = new ArrowHandler(getResources(),arrow,slide);
        coinHandler = new CoinHandler(getResources(),getApplicationContext(),coin,match,prediction,outcome,current,high,radio,sequence);
        high.setText(coinHandler.getHighScore());

    }


    private void start(int predict){

        outcome.setText(null);
        match.setBackgroundResource(R.drawable.arrow_0);

        if (coinHandler.getAllowance()) {
            arrowHandler.animateArrow();
            coinHandler.setPrediction(predict);
        }
    }


    public void selectHeads (View view){
        start(0);

    }
    public void selectTails (View view) {

        start(1);
    }



}
