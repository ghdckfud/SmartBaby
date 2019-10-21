package com.example.smartbaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;



public class ConnectActivity extends AppCompatActivity {
    public static BluetoothSPP bt;
    //블루투스 연결소켓 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        bt = new BluetoothSPP(this); //Initializing
        if (!bt.isBluetoothAvailable()) { //기기에서 블루투스 사용이 불가능하면 아예 어플 꺼버림. 어플 켜져있을 이유 x
            Toast.makeText(getApplicationContext()
                    , R.string.connect_deny
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //아두이노에서 데이터가 들어올때 수신하는 함수. 이거근데 배터리정보같은거 어떻게 분리시키는지는 아직 생각안함
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(ConnectActivity.this, message, Toast.LENGTH_SHORT).show();
                //아두이노가 데이터 던져줄떄 1바이트씩 보내니까 data에 아두이노에서 온 데이터를 넣어 바이트를 모두 합친 후 message를 통해 return. 데이터 받는거 실험 아직안됨.
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때 성공 메세지 출력
            MainActivity MA=(MainActivity) MainActivity.mainactivity;
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , name + R.string.connect_success + "\n" + address
                        , Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(ConnectActivity.this,MainActivity.class);
                ConnectActivity.this.startActivity(intent);
                ConnectActivity.bt.send("s", false); //데이터 하나보내고 줄바꿈
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , R.string.connect_lost, Toast.LENGTH_SHORT).show();
                finish();
                MA.finish();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , R.string.connect_fail, Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.btnConnect); //연동 버튼 터치리스너
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });

        Button moveButton = (Button)findViewById(R.id.nextButton);


        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ConnectActivity.this,MainActivity.class);
                ConnectActivity.this.startActivity(intent);
            }
        });

    }
    public void setup() {

    }
    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //블루투스 어뎁터로 인텐드 붙인거. 요거 체크하고 시작.
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_OTHER는 타기기와  연결. DEVICE_ANDROID는 안드로이드 기기끼리 연결
                setup();
            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //결과출력용
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , R.string.connect_deny
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    public void onDestroy() {  //소켓 깨기
        super.onDestroy();
        bt.stopService(); //블루투스 꺼버림
    }

}
