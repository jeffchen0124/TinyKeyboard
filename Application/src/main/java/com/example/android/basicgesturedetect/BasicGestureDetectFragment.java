/*
* Copyright (C) 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.basicgesturedetect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.lang.Math;

import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;

import static com.example.android.basicgesturedetect.GestureListener.TAG;

public class BasicGestureDetectFragment extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View gestureView = getActivity().findViewById(R.id.imageView2);
        final View textEdit = getActivity().findViewById(R.id.plain_text_input);
        gestureView.setClickable(true);
        gestureView.setFocusable(true);
        // BEGIN_INCLUDE(init_detector)

        // First create the GestureListener that will include all our callbacks.
        // Then create the GestureDetector, which takes that listener as an argument.
        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
        final GestureDetector gd = new GestureDetector(getActivity(), gestureListener);

        /* For the view where gestures will occur, create an onTouchListener that sends
         * all motion events to the gesture detector.  When the gesture detector
         * actually detects an event, it will use the callbacks you created in the
         * SimpleOnGestureListener to alert your application.
        */

        gestureView.setOnTouchListener(new View.OnTouchListener() {

            int lastAngle = 0;
            int firstAngle = 0;

            float startX, startY = 0;
            float midX, midY = 0;
            float lastX, lastY = 0;

            boolean multitouch = false;
            int n=0;

            float distanceThreshold = 10;
            int angleThreshold = 20;

            double angle1=0;
            double angle2=0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch(action) {
                    case (MotionEvent.ACTION_POINTER_DOWN):
                        multitouch = true;
                        Log.i(TAG,"Two Finger Touch");
                        return true;
                    case (MotionEvent.ACTION_DOWN) :
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        Log.i(TAG,"Action was DOWN");
                        Log.i(TAG,"x1:" + String.valueOf(startX) + " y1:" + String.valueOf(startY));
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        if (n==6) {
                            midX = motionEvent.getX();
                            midY = motionEvent.getY();
                            Log.i(TAG,"x2:" + String.valueOf(startX) + " y2:" + String.valueOf(startY));
                            Log.i(TAG,"x2:" + String.valueOf(midX) + " y2:" + String.valueOf(midY));
                        }
                        n=n+1;
                        Log.i(TAG, "History Size: " + String.valueOf(motionEvent.getHistorySize()));
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        lastX = motionEvent.getX();
                        lastY = motionEvent.getY();
                        Log.i(TAG,"Action was UP");
                        Log.i(TAG,"Angle 1 :" + String.valueOf(getAngle(startX, startY, midX, midY)));
                        Log.i(TAG,"Angle 2 :" + String.valueOf(getAngle(startX, startY, lastX, lastY)));

                        if (multitouch) {
                            if (Math.hypot((double)(lastX-startX),(double)(lastY-startY))>=distanceThreshold) {

                                if (lastX-startX > 0) {
                                    //Output 'space'
                                    textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
                                }
                                else {
                                    //Output 'backspace'
                                    textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                                }
                            }
                            else {
                                //Output 't'
                                textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_T));
                            }
                        }
                        else {
                            if (Math.hypot((double)(lastX-startX),(double)(lastY-startY))>=distanceThreshold) {
                                angle1 = getAngle(startX,startY,midX,midY);
                                angle2 = getAngle(startX,startY,lastX,lastY);

                                //
                                if ((angle1 <45/2 && angle1>=0) || (angle1>360-45/2 && angle1<=360)){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2>angle1 && angle2<180)
                                        {
                                            //Output 'g'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_G));
                                        }
                                        else {
                                            //Output 'i'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_I));
                                        }
                                    }
                                    else {
                                        //Output 'h'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_H));
                                    }
                                }
                                else if (angle1 >45/2 && angle1<45+45/2){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2>angle1 && angle2<45+180)
                                        {
                                            //Output 'c'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_C));
                                        }
                                        else {
                                            //Output 'f'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_F));
                                        }
                                    }
                                    else {
                                        //Output 'd'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_D));
                                    }
                                }
                                else if (angle1 >45+45/2 && angle1<90+45/2){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2>angle1 && angle2<90+180)
                                        {
                                            //Output 'z'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_Z));
                                        }
                                        else {
                                            //Output 'b'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_B));
                                        }
                                    }
                                    else {
                                        //Output 'a'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_A));
                                    }
                                }
                                else if (angle1 >90+45/2 && angle1<135+45/2){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2>angle1 && angle2<135+180)
                                        {
                                            //Output 'w'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_W));
                                        }
                                        else {
                                            //Output 'y'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_Y));
                                        }
                                    }
                                    else {
                                        //Output 'x'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_X));
                                    }
                                }
                                else if (angle1 >135+45/2 && angle1<180+45/2){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2>angle1 && angle2<360)
                                        {
                                            //Output 's'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_S));
                                        }
                                        else {
                                            //Output 'v'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_V));
                                        }
                                    }
                                    else {
                                        //Output 'u'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_U));
                                    }
                                }
                                else if (angle1 >180+45/2 && angle1<225+45/2){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2<angle1 && angle2>225-180)
                                        {
                                            //Output 'r'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_R));
                                        }
                                        else {
                                            //Output 'p'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_P));
                                        }
                                    }
                                    else {
                                        //Output 'q'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_Q));
                                    }
                                }
                                else if (angle1 >225+45/2 && angle1<270+45/2){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2<angle1 && angle2>270-180)
                                        {
                                            //Output 'o'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_O));
                                        }
                                        else {
                                            //Output 'm'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_M));
                                        }
                                    }
                                    else {
                                        //Output 'n'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_N));
                                    }
                                }
                                else if (angle1 >270+45/2 && angle1<315+45/2){
                                    if (Math.abs(angle2-angle1)>angleThreshold)
                                    {
                                        if (angle2<angle1 && angle2>315-180)
                                        {
                                            //Output 'l'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_L));
                                        }
                                        else {
                                            //Output 'j'
                                            textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_J));
                                        }
                                    }
                                    else {
                                        //Output 'k'
                                        textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_K));
                                    }
                                }
                            }
                            else {
                                //Output 'e'
                                textEdit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_E));
                            }

                        }

                        n=0;
                        multitouch = false;

                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
                        Log.i(TAG,"Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        Log.i(TAG,"Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default :
                        return false;
                }
            }

            public double getAngle(float x1, float y1, float x2, float y2) {

                double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
                return (rad*180/Math.PI + 180)%360;
            }

        });
        // END_INCLUDE(init_detector)
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sample_action) {
            clearLog();
        }
        return true;
    }

    public void clearLog() {
        LogFragment logFragment =  ((LogFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment));
        logFragment.getLogView().setText("");
    }
}
