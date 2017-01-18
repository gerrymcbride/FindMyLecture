package com.example.p170086.findmylecture;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

/**
 * Created by Gerard McBride on 28/11/16.
 * <p> Java class for the loading splash screen.
 *     Uses files from drawable and raw.
 *     Contains thread to initiate timer and catch possible exceptions/interruptions
 *     Then leads into Menu via homePage Activity
 *     </p>
 *
 */

public class On_Loading_Page extends AppCompatActivity {
    /**
     *
     * @param startUp is bundle created
     */

    MediaPlayer sugarPlum;

    @Override
    protected void onCreate(Bundle startUp) {
        super.onCreate(startUp);
        /**
         * content view is corresponding XML file
         */
        setContentView(R.layout.activity_on__loading__page);

        sugarPlum = MediaPlayer.create(On_Loading_Page.this, R.raw.sugarplum);
        sugarPlum.start();
        /**
         * Thread declared as timer
         * Uses run() method
         * Uses sleep() method
         */
        Thread timer = new Thread(){
            public void run(){
                /**
                 * try to sleep() for time
                 */
                try {

                    sleep(9000);
                    /**
                     * if interrupted catch exception as e
                     */
                } catch (InterruptedException e){

                    e.printStackTrace();
                    /**
                     * upon completion of trial without occurrence of e
                     */
                } finally {
                    /**
                     * @param homePage is Intent leading to Menu page
                     */
                    Intent homePage = new Intent("com.example.p170086.findmylecture.MENU");
                    startActivity(homePage);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }

        };
        /**
         * start timer thread
         */
        timer.start();

    }
    /**
     * onPause conatianing ahjfhdshflasdhf
     */
    protected void onPause(){

        super.onPause();
        finish();
    }
}
