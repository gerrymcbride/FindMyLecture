package com.example.p170086.findmylecture;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Gerard McBride on 28/11/16.
 * <p> This is the corresponding file for the homepage
 *      Users make a selection to use either their current location
 *      or a custom location</p>
 */

public class Menu extends AppCompatActivity implements View.OnClickListener {
    /**
     * @param currentLoc is declared as button type
     * @param customLoc is declared as button type
     */
    MediaPlayer boop;
    Button currentLoc;
    Button customLoc;

    /**
     * onCreate Method
     *
     * @param main is Bundle for creation
     *
     */
    protected void onCreate(Bundle main){
        super.onCreate(main); //create bundle
        setContentView(R.layout.selection);
        /**
         * Method initializeButtons() initializes previous button variables
         */
        boop = MediaPlayer.create(Menu.this, R.raw.androidcustomebeep3);
        intializeButtons();

        /**
         * @param currentLoc uses listener method. Method declared below
         * @param customLoc uses listener method ......
         */
        currentLoc.setOnClickListener(this);
        customLoc.setOnClickListener(this);

    }

    /**
     * buttons initialized via method
     */
    private void intializeButtons(){
        /**
         * @param currenLoc references existing button from R.java
         * @param customLoc references existing button from R.java
         */
        currentLoc = (Button)findViewById(R.id.button_current_location);
        customLoc = (Button)findViewById(R.id.button_custom_selection);
    }

    /**
     *
     * @param view onClick id is monitored
     */
    public void onClick(View view){
        /**
         * view id is condition of switch
         */
        switch (view.getId()){
            /**
             * case button 1
             */
            case R.id.button_current_location:
                boop.start();
                Intent currentLocationIntent = new Intent("com.example.p170086.findmylecture.CURRENTSELECTION");
                startActivity(currentLocationIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            /**
             * case button 2
             */
            case R.id.button_custom_selection:
                boop.start();
                Intent customLocationIntent = new Intent("com.example.p170086.findmylecture.MAPRESULT");
                startActivity(customLocationIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.exitButton:
                finish();
                System.exit(0);
        }
    }

}
