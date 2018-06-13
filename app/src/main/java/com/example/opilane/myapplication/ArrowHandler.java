package com.example.opilane.myapplication;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;



class ArrowHandler {

    private AnimationDrawable arrow;
    private ImageView view;
    private TextView slide;
    private Resources resources;

    ArrowHandler(Resources resources, ImageView view, TextView slide){
        this.view = view;
        this.slide = slide;
        this.resources = resources;
    }


    void animateArrow(){

        slide.setText(resources.getString(R.string.SlideToRoll));
        view.setBackgroundDrawable(null);
        view.setBackgroundResource(R.drawable.ic_arrow_slide);
        arrow = (AnimationDrawable) view.getBackground();
        arrow.start();
    }


    void stopArrow(){

        arrow.stop();
        view.setBackgroundDrawable(null);

    }


}
