package com.example.p170086.findmylecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by gerard on 29/12/16.
 */

public class CustomSelection extends AppCompatActivity implements View.OnClickListener {

    Button continueToMap;
    Button returnToSelection;

    protected void onCreate(Bundle custom){
        super.onCreate(custom);
        setContentView(R.layout.customselection);

        initializeButtons();

        continueToMap.setOnClickListener(this);
        returnToSelection.setOnClickListener(this);



    }

    public void initializeButtons(){

        continueToMap = (Button)findViewById(R.id.btnGo);
        returnToSelection = (Button)findViewById(R.id.returnButton);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnGo:

                Intent proceed = new Intent("com.example.p170086.findmylecture.MAPRESULT");
                startActivity(proceed);
                break;

            case R.id.returnButton:

                Intent returnTo = new Intent("com.example.p170086.findmylecture.MENU");
                startActivity(returnTo);
                break;
        }
    }
}
