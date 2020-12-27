package com.example.thirdlabmobileapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import androidx.appcompat.widget.AppCompatImageView;

public class AnimationView extends AppCompatImageView {

    private int x;
    private int y;
    private int circleRadius;
    private int height;
    private int width;

    private ShapeDrawable mDraw = new ShapeDrawable(new OvalShape());

    public AnimationView(Context context, int x, int y, int circleRadius, int height, int width) {
        super(context);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.circleRadius = circleRadius;

        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            System.out.println("before height : " + height);
            this.height -= resources.getDimensionPixelSize(resourceId);

            System.out.println("after height : " + height);
        }

        mDraw.getPaint().setColor(Color.parseColor("#1865A9"));
        mDraw.setBounds(x, y, x + circleRadius, y + circleRadius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDraw.setBounds(x, y, x + circleRadius, y + circleRadius);
        mDraw.draw(canvas);
    }

    public void updateDraw(int newX, int newY) {

        if (newX != x || newY != y) {

            if (newY < 0 || newY > height) {
                newY = y;
            }

            if (newX < 0 || newX > width - circleRadius) {
                newX = x;
            }
        }

        if (newX != x || newY != y) {
            x = newX;
            y = newY;
            invalidate();
        }
    }
}
