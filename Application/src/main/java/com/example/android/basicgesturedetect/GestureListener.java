/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.basicgesturedetect;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.common.logger.Log;

public class GestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener{

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    int lastAngle = 0;
    char lastChar = ' ';
    char currentChar = ' ';

    float startX, startY = 0;
    float midX, midY = 0;
    float lastX, lastY = 0;

    public static final String TAG = "GestureListener";

    // BEGIN_INCLUDE(init_gestureListener)
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // Up motion completing a single tap occurred.
        //Log.i(TAG, "Single Tap Up" + getTouchType(e));
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // Touch has been long enough to indicate a long press.
        // Does not indicate motion is complete yet (no up event necessarily)
        Log.i(TAG, " ");
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
    float distanceY) {



        // User attempted to scroll
        // Log.i(TAG, "Scroll" + getTouchType(e1));
        /*
        int angle = (int) getAngle(startX, startY, e2.getX(), e2.getY());

        lastAngle = angle;

        Log.i(TAG, "The angle is " + String.valueOf(lastAngle));

        currentChar = ' ';


        //if(lastAngle == 0)


        //int angle = (int) getAngle(e1.getX(), e1.getY(), e2.getX(), e2.getY());
        //float distanceX = e2.getX() - e1.getX();
        //float distanceY = e2.getY() - e1.getY();
        //A - 90
        if(angle > 85 && angle < 105) {
            currentChar = 'A';
        }

        // D - 45
        else if(angle > 35 && angle < 55){
            currentChar = 'D';
        }
        //H - 0
        else if(angle < 5 && angle >= 0){
            if(lastChar == 'A') {
                currentChar = 'B';
            }
            else
                currentChar = 'H';
        }
        //K - 315
        else if(angle > 305 && angle < 325) {
            currentChar = 'K';
        }
        //N - 270
        else if(angle > 260 && angle < 280) {
            currentChar = 'N';
        }
        //Q - 225
        else if(angle > 215 && angle < 235) {
            currentChar = 'Q';
        }
        //U - 180
        else if(angle > 170 && angle < 190){
            if(lastChar == 'A') {
                    currentChar = 'Z';
            }
            else
                currentChar = 'U';

        }

        //X - 135
        else if(angle > 125 && angle < 145) {
            currentChar = 'X';
        }

        if(lastChar != currentChar) {
            lastChar = currentChar;
        }

        lastAngle = angle;

        */
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
    float velocityY) {
        /*(
        // Fling event occurred.  Notification of this one happens after an "up" event.
        //Log.i(TAG, "Fling" + getTouchType(e1));
        Log.i(TAG, "e1: " + e1.getAction() + " e2: " + e2.getAction());

        int angle = (int) getAngle(startX, startY, e2.getX(), e2.getY());


        lastAngle = angle;

        Log.i(TAG, "The angle is " + String.valueOf(lastAngle));
        */
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // User performed a down event, and hasn't moved yet.
        //Log.i(TAG, "Show Press" + getTouchType(e));
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // "Down" event - User touched the screen.
        //Log.i(TAG, "Down" + getTouchType(e));

        /*
        if(lastAngle > 85 && lastAngle < 95) {
             //   Log.i(TAG, "A");
        }

        if(currentChar != ' ')
            Log.i(TAG, currentChar +"");
        lastAngle = 0;
        lastChar = ' ';

        */

        startX = e.getX();
        startY = e.getY();

        Log.i(TAG, String.valueOf(startX));
        Log.i(TAG, String.valueOf(startY));

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        // User tapped the screen twice.
        Log.i(TAG, "T");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        // Since double-tap is actually several events which are considered one aggregate
        // gesture, there's a separate callback for an individual event within the doubletap
        // occurring.  This occurs for down, up, and move.
        //Log.i(TAG, "Event within double tap" + getTouchType(e));
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        // A confirmed single-tap event has occurred.  Only called when the detector has
        // determined that the first tap stands alone, and is not part of a double tap.
        Log.i(TAG, "E");
        return false;
    }
    // END_INCLUDE(init_gestureListener)


    /**
     * Returns a human-readable string describing the type of touch that triggered a MotionEvent.
     */

    private static String getTouchType(MotionEvent e){

        String touchTypeDescription = " ";
        int touchType = e.getToolType(0);

        switch (touchType) {
            case MotionEvent.TOOL_TYPE_FINGER:
                touchTypeDescription += "(finger)";
                break;
            case MotionEvent.TOOL_TYPE_STYLUS:
                touchTypeDescription += "(stylus, ";
                //Get some additional information about the stylus touch
                float stylusPressure = e.getPressure();
                touchTypeDescription += "pressure: " + stylusPressure;

                if(Build.VERSION.SDK_INT >= 21) {
                    touchTypeDescription += ", buttons pressed: " + getButtonsPressed(e);
                }

                touchTypeDescription += ")";
                break;
            case MotionEvent.TOOL_TYPE_ERASER:
                touchTypeDescription += "(eraser)";
                break;
            case MotionEvent.TOOL_TYPE_MOUSE:
                touchTypeDescription += "(mouse)";
                break;
            default:
                touchTypeDescription += "(unknown tool)";
                break;
        }

        return touchTypeDescription;
    }

    /**
     * Returns a human-readable string listing all the stylus buttons that were pressed when the
     * input MotionEvent occurred.
     */
    @TargetApi(21)
    private static String getButtonsPressed(MotionEvent e){
        String buttons = "";

        if(e.isButtonPressed(MotionEvent.BUTTON_PRIMARY)){
            buttons += " primary";
        }

        if(e.isButtonPressed(MotionEvent.BUTTON_SECONDARY)){
            buttons += " secondary";
        }

        if(e.isButtonPressed(MotionEvent.BUTTON_TERTIARY)){
            buttons += " tertiary";
        }

        if(e.isButtonPressed(MotionEvent.BUTTON_BACK)){
            buttons += " back";
        }

        if(e.isButtonPressed(MotionEvent.BUTTON_FORWARD)){
            buttons += " forward";
        }

        if (buttons.equals("")){
            buttons = "none";
        }

        return buttons;
    }

    public double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }


    @Override
    public boolean onTouch(View view, MotionEvent e) {
        Log.i(TAG, "e: " + e.getActionMasked());
        return true;
    }
}


