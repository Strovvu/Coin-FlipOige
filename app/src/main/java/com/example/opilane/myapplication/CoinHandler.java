package com.example.opilane.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 *          This class Handles the Coin animation and updates the attributes
 *                   and views so the game works properly.
 *
 */

class CoinHandler {

    private SharedPreferences mSharedPreferences;
    private Context context;
    private ImageView view,match;
    private TextView prediction,outcome,current,high,sequence;
    private RadioGroup radio;
    private AnimationDrawable coin;
    private StringBuilder sequenceBuilder;
    private String predictionString;


    private int highScore, currentScore;

    private Random random;
    private int predict,side;
    private boolean allow;
    private Resources resources;


    CoinHandler(Resources resources, Context context, ImageView view, ImageView match, TextView prediction,
                TextView outcome, TextView current, TextView high, RadioGroup radio, TextView sequence)
    {


        this.resources=resources;
        this.context=context;
        mSharedPreferences = context.getSharedPreferences("mSharedHighscore", Context.MODE_PRIVATE);
        this.view = view;
        this.match = match;
        this.prediction = prediction;
        this.outcome = outcome;
        this.current = current;
        this.high = high;
        this.radio = radio;
        this.sequence = sequence;
        sequenceBuilder = new StringBuilder();
        highScore = mSharedPreferences.getInt("mSharedHighscore",Context.MODE_PRIVATE);
        currentScore = side = 0;
        allow = true;
        random = new Random();

    }


    /* This function is used to create the rolling coin */
    void rollCoin(){

        view.setImageDrawable(null);
        view.setBackgroundResource(R.drawable.ic_coin_roll);
        coin = (AnimationDrawable) view.getBackground();
        coin.start();
    }

    /* This function is used to stop the rolling coin */
    private void stopCoin(){

        coin.stop();
        view.setBackgroundDrawable(null);
    }

    /* This function is used to simulate a rolling coin moving in the air*/
    void flipCoin(){

        TranslateAnimation flip = new TranslateAnimation(0,0,0,-100);
        flip.setDuration(1000);
        flip.setRepeatCount(1);
        flip.setStartOffset(0);
        flip.setRepeatMode(2);
        flip.setFillAfter(true);
        view.startAnimation(flip);
        flip.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {

                allow = false;
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {

                setCoin();
                setOutcome();
                prediction.setText(resources.getString(R.string.tapToPredict));
                radio.clearCheck();
                allow = true;


            }
        });

    }

    /* This function is used to set the value of the coin*/
    private void setCoin(){

        stopCoin();
        side = random.nextInt(2);
        if(side == 0){
            view.setBackgroundResource(R.drawable.heads);
            outcome.setText(resources.getString(R.string.heads));
            predictionString = resources.getString(R.string.headsCharacter);

        }
        else{
            view.setBackgroundResource(R.drawable.tails);
            outcome.setText(resources.getString(R.string.tails));
            predictionString = resources.getString(R.string.tailsCharacter);
        }

    }

    /* This function is used to set the user's prediction*/
    void setPrediction(int p){

        predict = p;
        if(predict == 0){
            prediction.setText(R.string.youPredictedHeads);

        }
        else{
            prediction.setText(R.string.youPredictedTails);
        }


    }

    /* This function is used to set the outcome of the prediction */
    private void setOutcome(){

        if(predict == side){
            sequenceBuilder.append(predictionString);
            currentScore++;
            if(currentScore > highScore){
                highScore = currentScore;
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putInt("mSharedHighscore",highScore);
                editor.commit();
                Toast toast = Toast.makeText(context,resources.getString(R.string.CongratulationsNewHighscore), Toast.LENGTH_SHORT);
                toast.show();
            }
            current.setText(Integer.toString(currentScore));
            high.setText(Integer.toString(highScore));
            match.setBackgroundResource(R.drawable.won);
            sequence.setText(sequenceBuilder.toString());

        }
        else{
            if(currentScore > highScore){
                highScore = currentScore;
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putInt("mSharedHighscore",highScore);
                editor.commit();
                Toast toast = Toast.makeText(context,resources.getString(R.string.CongratulationsNewHighscore), Toast.LENGTH_SHORT);
                toast.show();
            }
            sequenceBuilder.setLength(0);
            currentScore = 0;
            current.setText(Integer.toString(currentScore));
            high.setText(Integer.toString(highScore));
            match.setBackgroundResource(R.drawable.lost);
            sequence.setText(sequenceBuilder.toString());
        }
    }

    /* This function is used to disable some function while the coin animates */
    public boolean getAllowance(){
        return allow;
    }

    /*This function is used for saving the passes the HighScore in the MainActivity*/
    public String getHighScore(){
        return String.valueOf(highScore);
    }

}
