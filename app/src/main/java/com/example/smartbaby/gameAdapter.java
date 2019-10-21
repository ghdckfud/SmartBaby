package com.example.smartbaby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class gameAdapter extends BaseAdapter {
    public Random random = new Random();
    public static int value;
    private Context context;
    private List<String> gameList;
    private TextView textTitle;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private TextView textView;

    public gameAdapter(Context context, List<String> gameList, TextView textTitle, LinearLayout linearLayout, ImageView imageView, TextView textView) {
        this.context = context;
        this.gameList = gameList;
        this.textTitle = textTitle;
        this.linearLayout = linearLayout;
        this.imageView = imageView;
        this.textView = textView;
    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int position) {
        return gameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View v =View.inflate(context, R.layout.mode, null);
        final TextView gameNotice = (TextView)v.findViewById(R.id.modeNotice);

        gameNotice.setText(gameList.get(position));

        v.setTag(gameList.get(position));

        gameNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] gameRandom = {"01", "02", "03","04","05","06","07","08","09","10"};
                String[] animalRandom = {"11", "12", "13","14","15","16","17","18","19","20"};
                parent.setVisibility(View.GONE);
                textTitle.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                value = random.nextInt(10);
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                if(gameNotice.getText().equals("사물 찾기")){
                    ConnectActivity.bt.send(gameRandom[value], false); //데이터 하나보내고 줄바꿈
                    if(value==0){
                        imageView.setImageResource(R.drawable.desk);
                        textView.setText("책상");
                    }
                    else if(value==1){
                        imageView.setImageResource(R.drawable.chair);
                        textView.setText("의자");
                    }
                    else if(value==2){
                        imageView.setImageResource(R.drawable.sofa);
                        textView.setText("소파");
                    }else if(value==3){
                        imageView.setImageResource(R.drawable.bed);
                        textView.setText("침대");
                    }else if(value==4){
                        imageView.setImageResource(R.drawable.drawer);
                        textView.setText("서랍");
                    }else if(value==5){
                        imageView.setImageResource(R.drawable.bookself);
                        textView.setText("책장");
                    }else if(value==6){
                        imageView.setImageResource(R.drawable.trash);
                        textView.setText("휴지통");
                    }else if(value==7){
                        imageView.setImageResource(R.drawable.box);
                        textView.setText("상자");
                    }else if(value==8){
                        imageView.setImageResource(R.drawable.cleaner);
                        textView.setText("청소기");
                    }else if(value==9) {
                        imageView.setImageResource(R.drawable.door);
                        textView.setText("문");
                    }
                }
                else{
                    ConnectActivity.bt.send(animalRandom[value], false); //데이터 하나보내고 줄바꿈
                    if(value==0){
                        imageView.setImageResource(R.drawable.dolpin);
                        textView.setText("돌고래");
                    }
                    else if(value==1){
                        imageView.setImageResource(R.drawable.turtle);
                        textView.setText("거북이");
                    }
                    else if(value==2){
                        imageView.setImageResource(R.drawable.jellyfish);
                        textView.setText("해파리");
                    }else if(value==3){
                        imageView.setImageResource(R.drawable.lobster);
                        textView.setText("가재");
                    }else if(value==4){
                        imageView.setImageResource(R.drawable.shark);
                        textView.setText("상어");
                    }else if(value==5){
                        imageView.setImageResource(R.drawable.octopus);
                        textView.setText("문어");
                    }else if(value==6){
                        imageView.setImageResource(R.drawable.penguin);
                        textView.setText("펭귄");
                    }else if(value==7){
                        imageView.setImageResource(R.drawable.polarbear);
                        textView.setText("북극곰");
                    }else if(value==8){
                        imageView.setImageResource(R.drawable.nemo);
                        textView.setText("니모");
                    }else if(value==9) {
                        imageView.setImageResource(R.drawable.seahorse);
                        textView.setText("해마");
                    }
                }

                //테스트용으로 test 데이터를 보내봄.




            }
        });
        return v;
    }
}
