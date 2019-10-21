package com.example.smartbaby;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


import java.util.HashMap;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity {
    public static HashMap<String, String> sendValue = new HashMap<String, String>();
    public static HashMap<Integer, String> soundValue = new HashMap<Integer, String>();
    public int num = 0;
    private long lastTime;
    public int number=0;
    public TextView outcome;
    public Handler delayHandler=null;
    public Runnable runnable;
    public static Activity mainactivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final ImageButton modeButton = (ImageButton) findViewById(R.id.modeButton);
        final ImageButton languageButton = (ImageButton) findViewById(R.id.languageButton);
        final ImageButton gameButton = (ImageButton) findViewById(R.id.gameButton);
        final TextView modeText = (TextView)findViewById(R.id.modeText);
        final TextView languageText = (TextView)findViewById(R.id.languageText);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);
        final TextView gamevalue = (TextView)findViewById(R.id.game);
        final TextView languagevalue = (TextView)findViewById(R.id.language);
        final TextView modevalue = (TextView)findViewById(R.id.mode);
        final ImageView batteryImage = (ImageView)findViewById(R.id.batteryImage);
        final TextView batteryText = (TextView)findViewById(R.id.batteryTitle);
        final ImageView gameImage = (ImageView)findViewById(R.id.gameImage);
        final TextView gameText = (TextView)findViewById(R.id.gameTitle);
        final ImageButton alarm = (ImageButton) findViewById(R.id.alarmImage);
        final ImageButton sound = (ImageButton)findViewById(R.id.soundImage);
        final RelativeLayout bar = (RelativeLayout)findViewById(R.id.soundbar);
        final SeekBar seekBar =(SeekBar)findViewById(R.id.SeekBar);
        mainactivity = MainActivity.this;

        final CharSequence storeText;
        outcome=(TextView)findViewById(R.id.barOut);
        sendValue.put("단어","l");
        sendValue.put("간단한 문장","m");
        sendValue.put("문장","h");
        sendValue.put("한국어","k");
        sendValue.put("영어","e");
        soundValue.put(0,")");
        soundValue.put(1,"!");
        soundValue.put(2,"@");
        soundValue.put(3,"#");
        soundValue.put(4,"$");
        soundValue.put(5,"%");
        soundValue.put(6,"^");
        soundValue.put(7,"&");
        soundValue.put(8,"*");
        soundValue.put(9,"(");

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectActivity.bt.send("a", false); //데이터 하나보내고 줄바꿈
                Toast.makeText(MainActivity.this,"기기에서 소리가 출력됩니다.",Toast.LENGTH_SHORT).show();
           }
        });


        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                lastTime=System.currentTimeMillis();
                delayHandler = new Handler();
                runnable= new Runnable() {
                    @Override
                    public void run() {
                        bar.setVisibility(View.GONE);
                    }
                };
                delayHandler.postDelayed(runnable,3000);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                number = seekBar.getProgress();
                update();
                ConnectActivity.bt.send(soundValue.get(number), false);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                number = seekBar.getProgress();
                if (delayHandler!=null){
                    delayHandler.removeCallbacks(runnable);
                }


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                number = seekBar.getProgress();
                delayHandler = new Handler();
                runnable= new Runnable() {
                    @Override
                    public void run() {
                        bar.setVisibility(View.GONE);
                    }
                };
                delayHandler.postDelayed(runnable,3000);
            }
        });

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameImage.setVisibility(View.GONE);
                gameText.setVisibility(View.GONE);
                batteryImage.setVisibility(View.VISIBLE);
                batteryText.setVisibility(View.VISIBLE);
                notice.setVisibility(View.GONE);
                modeButton.setBackground(getResources().getDrawable(R.drawable.imple_button));
                modeButton.setImageResource(R.drawable.mode);
                modevalue.setTextColor(getResources().getColor(R.color.colorPrimary));
                languageButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                languageButton.setImageResource(R.drawable.language);
                languagevalue.setTextColor(getResources().getColor(R.color.colorGray));
                gameButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                gameButton.setImageResource(R.drawable.game);
                gamevalue.setTextColor(getResources().getColor(R.color.colorGray));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new ModeFragment());
                fragmentTransaction.commit();
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameImage.setVisibility(View.GONE);
                gameText.setVisibility(View.GONE);
                batteryImage.setVisibility(View.VISIBLE);
                batteryText.setVisibility(View.VISIBLE);
                notice.setVisibility(View.GONE);
                languageButton.setBackground(getResources().getDrawable(R.drawable.imple_button));
                languageButton.setImageResource(R.drawable.ic_language_black_24dp);
                languagevalue.setTextColor(getResources().getColor(R.color.colorPrimary));
                modeButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                modeButton.setImageResource(R.drawable.unmode);
                modevalue.setTextColor(getResources().getColor(R.color.colorGray));
                gameButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                gameButton.setImageResource(R.drawable.game);
                gamevalue.setTextColor(getResources().getColor(R.color.colorGray));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new LanguageFragment());
                fragmentTransaction.commit();



            }
        });

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batteryImage.setVisibility(View.GONE);
                batteryText.setVisibility(View.GONE);
                notice.setVisibility(View.GONE);
                gameButton.setBackground(getResources().getDrawable(R.drawable.imple_button));
                gameButton.setImageResource(R.drawable.ic_videogame_asset_black_24dp);
                gamevalue.setTextColor(getResources().getColor(R.color.colorPrimary));
                modeButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                modeButton.setImageResource(R.drawable.unmode);
                modevalue.setTextColor(getResources().getColor(R.color.colorGray));
                languageButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                languageButton.setImageResource(R.drawable.language);
                languagevalue.setTextColor(getResources().getColor(R.color.colorGray));
                FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                final gameFragment gameFragment = new gameFragment();
                fragmentTransaction.replace(R.id.fragment,gameFragment);
                fragmentTransaction.commit();
                ConnectActivity.bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //아두이노에서 데이터가 들어올때 수신하는 함수. 이거근데 배터리정보같은거 어떻게 분리시키는지는 아직 생각안함
                    public void onDataReceived(byte[] data, String message) {
                        if(message.equals("0")){
                            Toast.makeText(MainActivity.this, "정답입니다.", Toast.LENGTH_SHORT).show();
                            fragmentTransaction.remove(gameFragment);
                            batteryImage.setVisibility(View.VISIBLE);
                            batteryText.setVisibility(View.VISIBLE);
                            gameImage.setVisibility(View.GONE);
                            gameText.setVisibility(View.GONE);
                        }
                        else if(message.equals("1")){
                            Toast.makeText(MainActivity.this, "틀렸어요.", Toast.LENGTH_SHORT).show();
                        }

                        //아두이노가 데이터 던져줄떄 1바이트씩 보내니까 data에 아두이노에서 온 데이터를 넣어 바이트를 모두 합친 후 message를 통해 return. 데이터 받는거 실험 아직안됨.
                    }
                });


            }
        });

        ConnectActivity.bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //아두이노에서 데이터가 들어올때 수신하는 함수. 이거근데 배터리정보같은거 어떻게 분리시키는지는 아직 생각안함
            public void onDataReceived(byte[] data, String message) {
                if(num ==0){
                    if(message.equals("0")){
                        languageText.setText("한국어");
                    }
                    else if(message.equals("1")){
                        languageText.setText("영어");
                    }
                    else{
                        languageText.setText(message);
                    }
                }
                else if(num == 1){
                    if(message.equals("0")){
                        modeText.setText("단어");
                    }
                    else if(message.equals("1")){
                        modeText.setText("간단한 문장");
                    }
                    else if(message.equals("2")){
                        modeText.setText("문장");
                    }
                    else{
                        modeText.setText(message);
                    }
                }
                else if(num == 2){
                    int battery = Integer.parseInt(message);
                    if(battery<=20){
                        batteryImage.setImageResource(R.drawable.b0);
                        batteryText.setText("배터리 부족");
                    }
                    else if(20<battery&&battery<=30){
                        batteryImage.setImageResource(R.drawable.b20);
                        batteryText.setText("배터리 20%");
                    }
                    else if(30<battery&&battery<=50){
                        batteryImage.setImageResource(R.drawable.b30);
                        batteryText.setText("배터리 30%");
                    }
                    else if(50<battery&&battery<=60){
                        batteryImage.setImageResource(R.drawable.b50);
                        batteryText.setText("배터리 50%");
                    }
                    else if(60<battery&&battery<=70){
                        batteryImage.setImageResource(R.drawable.b60);
                        batteryText.setText("배터리 60%");
                    }else if(70<battery&&battery<=80){
                        batteryImage.setImageResource(R.drawable.b80);
                        batteryText.setText("배터리 80%");
                    }else if(80<=battery&&battery<=90){
                        batteryImage.setImageResource(R.drawable.b90);
                        batteryText.setText("배터리 90%");
                    }
                    else if(90<battery){
                        batteryImage.setImageResource(R.drawable.b100);
                        batteryText.setText("배터리 Full%");
                    }

                }


                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                num = num+1;
                //아두이노가 데이터 던져줄떄 1바이트씩 보내니까 data에 아두이노에서 온 데이터를 넣어 바이트를 모두 합친 후 message를 통해 return. 데이터 받는거 실험 아직안됨.
            }
        });

    }
    public void update() {
        outcome.setText(new StringBuilder().append(number));
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-lastTimeBackPressed < 1500){ //1.5초 이내로 다시 한번도 취소 버튼을 누를 시 종료
            finish();

            return ;
        }
        Toast.makeText(this,"뒤로 버튼을 한 번 더 눌러 종료합니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
