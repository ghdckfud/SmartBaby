package com.example.smartbaby;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;



public class ModeAdapter extends BaseAdapter {

    private Context context;
    private List<String> modeList;
    private TextView textView,textTitle;
    private LinearLayout linearLayout;

    public ModeAdapter(Context context, List<String> modeList, TextView textView, TextView textTitle, LinearLayout linearLayout) {
        this.context = context;
        this.modeList = modeList;
        this.textView = textView;
        this.textTitle = textTitle;
        this.linearLayout = linearLayout;
    }

    @Override
    public int getCount() {
        return modeList.size();
    }

    @Override
    public Object getItem(int position) {
        return modeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View v =View.inflate(context, R.layout.mode, null);
        final TextView modeNotice = (TextView)v.findViewById(R.id.modeNotice);

        modeNotice.setText(modeList.get(position));

        v.setTag(modeList.get(position));

        modeNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText(modeNotice.getText());
                parent.setVisibility(View.GONE);
                textTitle.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                //테스트용으로 test 데이터를 보내봄.
                ConnectActivity.bt.send(MainActivity.sendValue.get(modeNotice.getText()), false); //데이터 하나보내고 줄바꿈
                Toast.makeText(v.getContext().getApplicationContext()
                        , MainActivity.sendValue.get(modeNotice.getText())+"", Toast.LENGTH_SHORT).show();



            }
        });
        return v;
    }
}
