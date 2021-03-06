package me.drakeet.fingertransparentview;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by drakeet on 8/6/15.
 */
public abstract class OnScaleTouchListener implements View.OnTouchListener {

    private int mTouchNum = 0;
    private float mOldDist;
    private float REDRAW_DIS = 0.05f;

    public abstract void onScale(float scale);

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mTouchNum = 1;
                break;
            case MotionEvent.ACTION_UP:
                mTouchNum = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mTouchNum -= 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mOldDist = (float) spacing(event);
                mTouchNum += 1;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mTouchNum >= 2) {
                    float newDist = (float) spacing(event);
                    if (newDist > mOldDist + REDRAW_DIS) {
                        onScale(newDist / mOldDist);
                    }
                    if (newDist < mOldDist - REDRAW_DIS) {
                        onScale(newDist / mOldDist);
                    }
                }
                break;
        }
        return view.onTouchEvent(event);
    }

    private double spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }
}
