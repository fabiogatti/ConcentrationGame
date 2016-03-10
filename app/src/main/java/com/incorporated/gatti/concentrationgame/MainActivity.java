package com.incorporated.gatti.concentrationgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.TextView;

/*public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button one = (Button) findViewById(R.id.button1);
        one.setOnClickListener(this); // calling onClick() method
        Button two = (Button) findViewById(R.id.button2);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.button3);
        three.setOnClickListener(this);
        Button four = (Button) findViewById(R.id.button4);
        four.setOnClickListener(this);

        //registerForContextMenu(two);
    }

    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle(getResources().getString(R.string.button2));
        //helpBuilder.setMessage(getResources().getString(R.string.info));
        TextView myMsg = new TextView(this);
        myMsg.setText(getResources().getString(R.string.info));
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        helpBuilder.setView(myMsg);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, R.string.info);
    }*/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button1:

                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                SeekBar bar = (SeekBar) findViewById(R.id.seekbar);
                int value = bar.getProgress();
                myIntent.putExtra("jugadores",value);
                MainActivity.this.startActivity(myIntent);
                //startActivityForResult(myIntent, 0);

                break;

            case R.id.button2:
                /*Button two = (Button) findViewById(R.id.button2);
                registerForContextMenu(two);
                this.openContextMenu(v);*/
                showSimplePopUp();
                break;

            case R.id.button3:
                Intent myIntent2 = new Intent(MainActivity.this, HighScore.class);
                MainActivity.this.startActivity(myIntent2);
                break;

            case R.id.button4:
                finish();
                System.exit(0);
                break;

            default:
                break;
        }

    }
}