package com.incorporated.gatti.concentrationgame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScore extends Activity implements View.OnClickListener {

    public void showScores(){
        SharedPreferences prefs = this.getSharedPreferences("Game", Context.MODE_PRIVATE);
        int highScore;
        //int highScore2 = prefs.getInt("hScore2", 0);
        //int highScore3 = prefs.getInt("hScore3", 0);
        String highName;
        //String highName2 = prefs.getString("hName2", getResources().getString(R.string.empty));
        //String highName3 = prefs.getString("hName3", getResources().getString(R.string.empty));
        int highPlayers;
        int highPlays;
        String name;
        int resID;
        TextView tv;
        for(int i=1;i<4;i++){
            highName = prefs.getString("hName" + i, getResources().getString(R.string.empty));
            name = "com.incorporated.gatti.concentrationgame:id/highName"+(i);
            resID = getResources().getIdentifier(name,"string",getPackageName());
            tv = (TextView) findViewById(resID);
            tv.setText(highName);

            highScore = prefs.getInt("hScore" + i, 0);
            name = "com.incorporated.gatti.concentrationgame:id/highScore"+(i);
            resID = getResources().getIdentifier(name,"string",getPackageName());
            tv = (TextView) findViewById(resID);
            tv.setText(highScore+"");

            highPlayers = prefs.getInt("hPlayers"+i, 0);
            name = "com.incorporated.gatti.concentrationgame:id/highPlayers"+(i);
            resID = getResources().getIdentifier(name,"string",getPackageName());
            tv = (TextView) findViewById(resID);
            tv.setText(highPlayers+"");

            highPlays = prefs.getInt("hPlays"+i, 0);
            name = "com.incorporated.gatti.concentrationgame:id/highPlays"+(i);
            resID = getResources().getIdentifier(name,"string",getPackageName());
            tv = (TextView) findViewById(resID);
            tv.setText(highPlays+"");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        showScores();

        Button menu2 = (Button) findViewById(R.id.menu2);
        menu2.setOnClickListener(this);
        Button reset = (Button) findViewById(R.id.resetScores);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.menu2:
                finish();
                break;
            case R.id.resetScores:
                SharedPreferences sp = this.getSharedPreferences("Game", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                showScores();
                Snackbar snack;
                snack = Snackbar.make(v, getResources().getString(R.string.reset), Snackbar.LENGTH_LONG).setAction("Action", null);
                View view = snack.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                //tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snack.show();
        }
    }
}
