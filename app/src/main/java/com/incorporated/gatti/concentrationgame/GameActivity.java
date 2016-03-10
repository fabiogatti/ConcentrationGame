package com.incorporated.gatti.concentrationgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.input.InputManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Filter;
//import java.util.logging.Handler;


public class GameActivity extends Activity implements View.OnClickListener {

    public int turn;
    public int click; //Variable con valores 0 o 1 para primer o segundo click
    public int clicked; //Ultimo valor clickeado
    public int lastId; //Ultimo id clickeado
    public int lastrow;
    public int lastcolumn;
    public int max;
    public int [][] values;
    public int [][] clickable;
    public int [] scores;
    public int plays;
    public final GradientDrawable gd = new GradientDrawable();


    public void colorear(int value,int id){
        String name = "com.incorporated.gatti.concentrationgame:id/grid"+id;
        int resID = getResources().getIdentifier(name,"string",getPackageName());
        Button btn = (Button) findViewById(resID);
        int color = 0;
        switch (value) {
            case 0:
                color = Color.RED;//RED
                break;
            case 1:
                color = Color.parseColor("#E91E63");//Pink
                break;
            case 2:
                color = Color.parseColor("#9C27B0");//Purple
                break;
            case 3:
                color = Color.parseColor("#3F51B5");//Indigo
                break;
            case 4:
                color = Color.parseColor("#2196F3");//Blue
                break;
            case 5:
                color = Color.parseColor("#009688");//Teal
                break;
            case 6:
                color = Color.parseColor("#4CAF50");//Green
                break;
            case 7:
                color = Color.parseColor("#FFEB3B");//Yellow
                break;
            case 8:
                color = Color.parseColor("#FF9800");//Orange
                break;
            case 9:
                color = Color.parseColor("#795548");//Brown
                break;
            case 10:
                color = Color.parseColor("#9E9E9E");//Grey
                break;
            case 11:
                color = Color.parseColor("#607D8B");//Blue grey
                break;
        }
        GradientDrawable gd2 = new GradientDrawable();
        gd2.setColor(color);
        //gd2.setCornerRadius(10f);
        gd2.setStroke(1, Color.BLACK);
        btn.setBackground(gd2);
    }

    public void firstClick(int id, int row, int column){
        colorear(values[row][column],id);
        click = 1;
        lastrow = row;
        lastcolumn = column;
        lastId = id;
    }

    public boolean countZeros(){
        for (int i=0; i<6;i++){
            for (int j=0; j<4;j++){
                if(clickable[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }

    public void secondClick(int id, int row, int column){
        int resID;
        colorear(values[row][column],id);
        resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/grid"+(id),"string",getPackageName());
        final Button btn = (Button) findViewById(resID);
        resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/grid"+(lastId),"string",getPackageName());
        final Button btn2 = (Button) findViewById(resID);
        Handler myHandler = new Handler();
        click = 0;
        plays = plays+1;
        if(values[row][column]==values[lastrow][lastcolumn]){
            clickable[row][column] = clickable[row][column]+1;
            clickable[lastrow][lastcolumn] = clickable[lastrow][lastcolumn]+1;
            scores[turn]=scores[turn]+100;
            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/score"+(turn+1),"string",getPackageName());
            TextView txt = (TextView) findViewById(resID);
            txt.setText(""+scores[turn]);
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn.setEnabled(false);
                    btn2.setEnabled(false);
                }
            }, 500);
        }
        else{
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //btn.setBackgroundResource(android.R.drawable.btn_default);
                    //btn2.setBackgroundResource(android.R.drawable.btn_default);
                    btn.setBackground(gd);
                    btn2.setBackground(gd);
                }
            }, 500);
            int next = turn+2;
            if(next>max){
                next=1;
            }
            //Log.d("Tag","Valor del next: "+next);
            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(turn+1),"string",getPackageName());
            EditText txt = (EditText) findViewById(resID);
            txt.setTypeface(null,Typeface.NORMAL);
            txt.clearFocus();
            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/score"+(turn+1),"string",getPackageName());
            TextView txt2 = (TextView) findViewById(resID);
            txt2.setTypeface(null, Typeface.NORMAL);
            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(next),"string",getPackageName());
            txt = (EditText) findViewById(resID);
            txt.setTypeface(null, Typeface.BOLD);
            txt.requestFocus();
            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/score"+(next),"string",getPackageName());
            txt2 = (TextView) findViewById(resID);
            txt2.setTypeface(null, Typeface.BOLD);
            if(turn==max-1){
                turn=0;
            }
            else{
                turn=turn+1;
            }
        }
    }

    public int[] remove(int[] list,int pos){
        int l = list.length;
        int j = 0;
        int[] list2 = new int[l-1];
        for(int i=0;i<l;i++){
            if(i!=pos){
                list2[j] = list[i];
                j++;
            }
        }
        return list2;
    }

    public int[][] random(int n, int m){
        int[][] matrix = new int [n][m];
        int[] values = new int [(n*m)];
        int o = 0;
        for(int i=0;i<(n*m)/2;i++){
            values[o]=i;
            values[o+1]=i;
            o=o+2;
        }
        //Log.v("tag","Array: "+Arrays.toString(values));
        Random rand = new Random();
        int k,r;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                k = values.length;
                r = rand.nextInt(k);
                matrix[i][j] = values[r];
                values = remove(values,r);
                //Log.v("tag","Array: "+Arrays.toString(values));
            }
        }
        return matrix;
    }

    //Antigua función random
    /*public int[][] random(int n, int m){
        int[][] matrix = new int [n][m];
        int[] values = new int [(n*m)/2];
        Random rand = new Random();
        int k,r;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                k=0;
                while(k==0){
                    r=rand.nextInt(12);
                    if(values[r]!=2){
                        values[r]=values[r]+1;
                        matrix[i][j]=r;
                        k=1;
                    }
                }
            }
        }
        return matrix;
    }*/

    private void showSimplePopUp(String s,String str) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle(str);
        //helpBuilder.setMessage(getResources().getString(R.string.info));
        TextView myMsg = new TextView(this);
        myMsg.setText(s);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(12);
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (0 * scale + 0.5f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1f);

        Intent myIntent = getIntent();
        int players = myIntent.getIntExtra("jugadores", 0);
        //Agrega el texto de Jugador 1, Jugador 2 al layout con id namesLayout
        EditText txt1 = new EditText(this);
        txt1.setText(getResources().getString(R.string.p1));
        txt1.setGravity(Gravity.CENTER);
        txt1.setTypeface(null, Typeface.BOLD);
        //txt1.setTextSize(48/(jugadores+2));
        txt1.setTextSize(20);
        txt1.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt1.setSingleLine();
        txt1.setFilters(FilterArray);
        txt1.setId(R.id.player1);
        //txt1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
        txt1.setLayoutParams(lp);
        EditText txt2 = new EditText(this);
        txt2.setText(getResources().getString(R.string.p2));
        txt2.setGravity(Gravity.CENTER);
        txt2.setTextSize(20);
        txt2.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt2.setSingleLine();
        txt2.setFilters(FilterArray);
        txt2.setId(R.id.player2);
        //txt2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));
        txt2.setLayoutParams(lp);
        LinearLayout ly = (LinearLayout) findViewById(R.id.namesLayout);
        ly.addView(txt1);
        ly.addView(txt2);

        //Agrega el texto de los puntajes de los jugadores al layout con id scoresLayout
        TextView score1 = new TextView(this);
        score1.setText("0");
        score1.setGravity(Gravity.CENTER);
        score1.setTypeface(null, Typeface.BOLD);
        score1.setTextSize(20);
        score1.setId(R.id.score1);
        //score1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        score1.setLayoutParams(lp);
        TextView score2 = new TextView(this);
        score2.setText("0");
        score2.setGravity(Gravity.CENTER);
        score2.setTextSize(20);
        score2.setId(R.id.score2);
        //score2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        score2.setLayoutParams(lp);
        LinearLayout ly2 = (LinearLayout) findViewById(R.id.scoresLayout);
        ly2.addView(score1);
        ly2.addView(score2);

        //Variables y lógica del juego
        values = random(6,4);
        clickable = new int [6][4];
        turn = 0;
        click = 0;
        plays = 0;
        //Log.d("this is my array", "arr: " + Arrays.deepToString(values));//Muestra de la matriz
        //Log.d("this is my array", "arr: " + Arrays.deepToString(clickable));//Muestra de la matriz
        switch (players){
            case 0:
                max = 2;
                scores = new int[2];
                //LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,);
                break;
            case 1:
                EditText txt3 = new EditText(this);
                txt3.setText(getResources().getString(R.string.p3));
                txt3.setGravity(Gravity.CENTER);
                txt3.setTextSize(20);
                txt3.setImeOptions(EditorInfo.IME_ACTION_DONE);
                txt3.setSingleLine();
                txt3.setFilters(FilterArray);
                txt3.setId(R.id.player3);
                //txt3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                txt3.setLayoutParams(lp);
                ly.addView(txt3);
                TextView score3 = new TextView(this);
                score3.setText("0");
                score3.setGravity(Gravity.CENTER);
                score3.setTextSize(20);
                score3.setId(R.id.score3);
                //score3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                score3.setLayoutParams(lp);
                ly2.addView(score3);
                max = 3;
                scores = new int[3];
                break;
            case 2:
                EditText txt4 = new EditText(this);
                txt4.setText(getResources().getString(R.string.p3));
                txt4.setGravity(Gravity.CENTER);
                txt4.setTextSize(20);
                txt4.setImeOptions(EditorInfo.IME_ACTION_DONE);
                txt4.setSingleLine();
                txt4.setFilters(FilterArray);
                txt4.setId(R.id.player3);
                //txt4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                txt4.setLayoutParams(lp);
                EditText txt5 = new EditText(this);
                txt5.setText(getResources().getString(R.string.p4));
                txt5.setGravity(Gravity.CENTER);
                txt5.setTextSize(20);
                txt5.setImeOptions(EditorInfo.IME_ACTION_DONE);
                txt5.setSingleLine();
                txt5.setFilters(FilterArray);
                txt5.setId(R.id.player4);
                //txt5.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                txt5.setLayoutParams(lp);

                TextView score4 = new TextView(this);
                score4.setText("0");
                score4.setGravity(Gravity.CENTER);
                score4.setTextSize(20);
                score4.setId(R.id.score3);
                //score4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                score4.setLayoutParams(lp);
                ly2.addView(score4);
                TextView score5 = new TextView(this);
                score5.setText("0");
                score5.setGravity(Gravity.CENTER);
                score5.setTextSize(20);
                score5.setId(R.id.score4);
                //score5.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                score5.setLayoutParams(lp);
                ly2.addView(score5);

                ly.addView(txt4);
                ly.addView(txt5);
                max = 4;
                scores = new int[4];
                break;
        }

        gd.setColor(Color.WHITE);
        //gd.setCornerRadius(10f);
        gd.setStroke(1, Color.BLACK);

        //Listeners de botones
        Button two = (Button) findViewById(R.id.menu);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.newgame);
        three.setOnClickListener(this);
        Button four = (Button) findViewById(R.id.restart);
        four.setOnClickListener(this);
        Button grid0 = (Button) findViewById(R.id.grid0);
        grid0.setBackground(gd);
        grid0.setOnClickListener(this);
        Button grid1 = (Button) findViewById(R.id.grid1);
        grid1.setBackground(gd);
        grid1.setOnClickListener(this);
        Button grid2 = (Button) findViewById(R.id.grid2);
        grid2.setBackground(gd);
        grid2.setOnClickListener(this);
        Button grid3 = (Button) findViewById(R.id.grid3);
        grid3.setBackground(gd);
        grid3.setOnClickListener(this);
        Button grid4 = (Button) findViewById(R.id.grid4);
        grid4.setBackground(gd);
        grid4.setOnClickListener(this);
        Button grid5 = (Button) findViewById(R.id.grid5);
        grid5.setBackground(gd);
        grid5.setOnClickListener(this);
        Button grid6 = (Button) findViewById(R.id.grid6);
        grid6.setBackground(gd);
        grid6.setOnClickListener(this);
        Button grid7 = (Button) findViewById(R.id.grid7);
        grid7.setBackground(gd);
        grid7.setOnClickListener(this);
        Button grid8 = (Button) findViewById(R.id.grid8);
        grid8.setBackground(gd);
        grid8.setOnClickListener(this);
        Button grid9 = (Button) findViewById(R.id.grid9);
        grid9.setBackground(gd);
        grid9.setOnClickListener(this);
        Button grid10 = (Button) findViewById(R.id.grid10);
        grid10.setBackground(gd);
        grid10.setOnClickListener(this);
        Button grid11 = (Button) findViewById(R.id.grid11);
        grid11.setBackground(gd);
        grid11.setOnClickListener(this);
        Button grid12 = (Button) findViewById(R.id.grid12);
        grid12.setBackground(gd);
        grid12.setOnClickListener(this);
        Button grid13 = (Button) findViewById(R.id.grid13);
        grid13.setBackground(gd);
        grid13.setOnClickListener(this);
        Button grid14 = (Button) findViewById(R.id.grid14);
        grid14.setBackground(gd);
        grid14.setOnClickListener(this);
        Button grid15 = (Button) findViewById(R.id.grid15);
        grid15.setBackground(gd);
        grid15.setOnClickListener(this);
        Button grid16 = (Button) findViewById(R.id.grid16);
        grid16.setBackground(gd);
        grid16.setOnClickListener(this);
        Button grid17 = (Button) findViewById(R.id.grid17);
        grid17.setBackground(gd);
        grid17.setOnClickListener(this);
        Button grid18 = (Button) findViewById(R.id.grid18);
        grid18.setBackground(gd);
        grid18.setOnClickListener(this);
        Button grid19 = (Button) findViewById(R.id.grid19);
        grid19.setBackground(gd);
        grid19.setOnClickListener(this);
        Button grid20 = (Button) findViewById(R.id.grid20);
        grid20.setBackground(gd);
        grid20.setOnClickListener(this);
        Button grid21 = (Button) findViewById(R.id.grid21);
        grid21.setBackground(gd);
        grid21.setOnClickListener(this);
        Button grid22 = (Button) findViewById(R.id.grid22);
        grid22.setBackground(gd);
        grid22.setOnClickListener(this);
        Button grid23 = (Button) findViewById(R.id.grid23);
        grid23.setBackground(gd);
        grid23.setOnClickListener(this);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        final EditText edit = (EditText) findViewById(R.id.player1);
        final EditText edit2 = (EditText) findViewById(R.id.player2);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit.focus();
                edit.requestFocus();
                //imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                edit.setCursorVisible(true);
            }
        });
        edit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    int resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player" + (turn + 1), "string", getPackageName());
                    EditText txt = (EditText) findViewById(resID);
                    txt.requestFocus();
                    View view = getCurrentFocus();
                    if(view!=null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    txt.setCursorVisible(false);
                }
                return false;
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit.focus();
                edit2.requestFocus();
                //imm.showSoftInput(edit2, InputMethodManager.SHOW_IMPLICIT);
                edit2.setCursorVisible(true);
            }
        });
        edit2.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    int resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player" + (turn + 1), "string", getPackageName());
                    EditText txt = (EditText) findViewById(resID);
                    txt.requestFocus();
                    View view = getCurrentFocus();
                    if (view != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    txt.setCursorVisible(false);
                }
                return false;
            }
        });
        if(max>=3) {
            final EditText edit3 = (EditText) findViewById(R.id.player3);
            edit3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //edit.focus();
                    edit3.requestFocus();
                    //imm.showSoftInput(edit3, InputMethodManager.SHOW_IMPLICIT);
                    edit3.setCursorVisible(true);
                }
            });
            edit3.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        int resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player" + (turn + 1), "string", getPackageName());
                        EditText txt = (EditText) findViewById(resID);
                        txt.requestFocus();
                        View view = getCurrentFocus();
                        if (view != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        txt.setCursorVisible(false);
                    }
                    return false;
                }
            });
            if(max==4){
                final EditText edit4 = (EditText) findViewById(R.id.player4);
                edit4.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        //edit.focus();
                        edit4.requestFocus();
                        //imm.showSoftInput(edit4,InputMethodManager.SHOW_IMPLICIT);
                        edit4.setCursorVisible(true);
                    }
                });
                edit4.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            int resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player" + (turn + 1), "string", getPackageName());
                            EditText txt = (EditText) findViewById(resID);
                            txt.requestFocus();
                            View view = getCurrentFocus();
                            if(view!=null) {
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            txt.setCursorVisible(false);
                        }
                        return false;
                    }
                });
            }
        }
        /*if(getCurrentFocus()!=null){
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }*/
        /*int resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(turn+1),"string",getPackageName());
        EditText txt = (EditText) findViewById(resID);
        txt.requestFocus();*/

        /*edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66){
                    edit.clearFocus();
                }
                return false;
            }
        });*/
        /*edit.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                //if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                //    if(!event.isShiftPressed()){
                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                //    }
                //}
                return false;
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        //EditText edit = (EditText) findViewById(R.id.player1);
        //edit.clearFocus();
        //edit.setCursorVisible(false);
        String string = ""+getCurrentFocus();
        String s;
        if(string.contains("Edit")){
            View view = getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            for(int i=0;i<max; i++){
                int resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(i+1),"string",getPackageName());
                EditText txt = (EditText) findViewById(resID);
                //txt.clearFocus();
                txt.setCursorVisible(false);
            }
        }
        s = getResources().getResourceName(v.getId());
        if(s.contains("grid")){
            //Log.d("Text","Esta entrando");
            //Log.d("Text",s.replace("com.incorporated.gatti.concentrationgame:id/grid",""));
            s = s.replace("com.incorporated.gatti.concentrationgame:id/grid","");
            int l = Integer.parseInt(s);
            int row = (int) l/4;
            int column = l%4;
            String str;
            String str2;
            if(click == 0){
                firstClick(l,row,column);
            }
            else{
                if(l==lastId){
                    str = "\n"+getResources().getString(R.string.select);
                    str2 = getResources().getString(R.string.warning);
                    showSimplePopUp(str,str2);
                }
                else {
                    secondClick(l,row,column);
                    if(countZeros()==true){
                        int winner = 0;
                        int player = 0;
                        int empate = 0;
                        for(int i=0;i<max;i++){
                            if(scores[i]==winner){
                                empate=1;
                            }
                            else if(scores[i]>winner){
                                winner=scores[i];
                                empate=0;
                                player=i;
                            }
                        }
                        if(empate==1){
                            str = "\n"+getResources().getString(R.string.tie)+"\n"+getResources().getString(R.string.plays)+" "+plays;
                            str2 = getResources().getString(R.string.finish);
                            showSimplePopUp(str,str2);
                            return;
                        }
                        int resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(player+1),"string",getPackageName());
                        EditText txt = (EditText) findViewById(resID);
                        str = "\n"+txt.getText()+" "+getResources().getString(R.string.winner)+"\n"+getResources().getString(R.string.plays)+" "+plays;
                        str2 = getResources().getString(R.string.finish);
                        showSimplePopUp(str,str2);

                        //HIGH SCORE
                        SharedPreferences prefs = this.getSharedPreferences("Game", Context.MODE_PRIVATE);
                        int highScore3 = prefs.getInt("hScore3", 0);
                        if(winner>=highScore3){
                            SharedPreferences.Editor edit = prefs.edit();
                            int highScore2 = prefs.getInt("hScore2", 0);
                            if(winner>=highScore2){
                                int highScore1 = prefs.getInt("hScore1", 0);
                                if(winner>=highScore1){
                                    edit.putInt("hScore1", winner);
                                    edit.putString("hName1", txt.getText() + "");
                                    edit.putInt("hPlayers1", max);
                                    edit.putInt("hPlays1", plays);
                                    edit.commit();
                                }
                                else{
                                    edit.putInt("hScore2", winner);
                                    edit.putString("hName2", txt.getText() + "");
                                    edit.putInt("hPlayers2", max);
                                    edit.putInt("hPlays2", plays);
                                    edit.commit();
                                }
                            }
                            else{
                                edit.putInt("hScore3", winner);
                                edit.putString("hName3", txt.getText() + "");
                                edit.putInt("hPlayers3", max);
                                edit.putInt("hPlays3", plays);
                                edit.commit();
                            }
                        }
                    }
                }
            }
        }
        else{
            TextView txt;
            EditText txt2;
            int resID;
            Snackbar snack;
            switch (v.getId()) {
                case R.id.menu:
                    finish();
                    break;
                case R.id.newgame:
                    values = random(6,4);
                    clickable = new int [6][4];
                    turn = 0;
                    click = 0;
                    scores = new int [max];
                    plays = 0;
                    for(int i=0;i<max;i++){
                        resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/score"+(i+1),"string",getPackageName());
                        txt = (TextView) findViewById(resID);
                        txt.setText("0");
                        if(i==0){
                            txt.setTypeface(null,Typeface.BOLD);
                            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(i+1),"string",getPackageName());
                            txt2 = (EditText) findViewById(resID);
                            txt2.setTypeface(null,Typeface.BOLD);
                            txt2.requestFocus();
                        }
                        else{
                            txt.setTypeface(null, Typeface.NORMAL);
                            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(i+1),"string",getPackageName());
                            txt2 = (EditText) findViewById(resID);
                            txt2.setTypeface(null, Typeface.NORMAL);
                        }
                    }
                    for(int i=0;i<24;i++){
                        resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/grid"+(i),"string",getPackageName());
                        Button btn = (Button) findViewById(resID);
                        btn.setBackground(gd);
                        btn.setEnabled(true);
                    }
                    //Log.d("this is my array", "arr: " + Arrays.deepToString(values));//Muestra de la matriz
                    snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.newGameText), Snackbar.LENGTH_SHORT);
                    View view = snack.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    //tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snack.show();
                    break;
                case R.id.restart:
                    clickable = new int [6][4];
                    turn = 0;
                    click = 0;
                    scores = new int [max];
                    plays = 0;
                    for(int i=0;i<max;i++){
                        resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/score"+(i+1),"string",getPackageName());
                        txt = (TextView) findViewById(resID);
                        txt.setText("0");
                        if(i==0){
                            txt.setTypeface(null,Typeface.BOLD);
                            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(i+1),"string",getPackageName());
                            txt2 = (EditText) findViewById(resID);
                            txt2.setTypeface(null,Typeface.BOLD);
                            txt2.requestFocus();
                        }
                        else{
                            txt.setTypeface(null, Typeface.NORMAL);
                            resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/player"+(i+1),"string",getPackageName());
                            txt2 = (EditText) findViewById(resID);
                            txt2.setTypeface(null, Typeface.NORMAL);
                        }
                    }
                    for(int i=0;i<24;i++){
                        resID = getResources().getIdentifier("com.incorporated.gatti.concentrationgame:id/grid"+(i),"string",getPackageName());
                        Button btn = (Button) findViewById(resID);
                        btn.setBackground(gd);
                        btn.setEnabled(true);
                    }
                    //Log.d("this is my array", "arr: " + Arrays.deepToString(values));//Muestra de la matriz
                    snack = Snackbar.make(v, getResources().getString(R.string.restartText), Snackbar.LENGTH_LONG).setAction("Action", null);
                    view = snack.getView();
                    tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    //tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snack.show();
                    break;
                /*default:
                    Log.d("Text","Esta entrando");
                    String s = ""+v.getId();
                    s.replace("grid","");
                    int l = Integer.parseInt(s);
                    if(click == 0){
                        firstClick(l);
                    }
                    else{
                        secondClick(l);
                    }
                    break;*/
            }
        }
    }
}
