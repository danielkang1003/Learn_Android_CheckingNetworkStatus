package com.example.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //public이여서 공개되어있고 static이여서 다른 클래스에서도 부를 수 있음
    public static TextView tv_state;
    private NetworkReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_state = findViewById(R.id.tv_state);

        // 브로드 캐스트 리시버 등록 부분
        //intent와 intentfilter의 차이:
        //intent는 기능을 가져다 쓰려고 하는 목적이 명시적(뚜렷함). intent쪽으로 이동을 하겠다는 것
        //IntentFilter은 약간 반대되는 개념
        //브로드캐스트 리시버를 만들었으니까 너네가 와서 써! 이런 느낌
        IntentFilter filter = new IntentFilter();
        receiver = new NetworkReceiver();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(receiver, filter);
        //여기까지가 브로드캐스트 리시버가 등록이 됬고 NetworkReceiver.java의 onReceive가 작동을 시작하게 됨
    }


    //이 구문을 해주지 않으면 앱이 종료되기 전에는 계속 체크를 하게 됨
    //해주지 않으면 휴대폰 과부하가 올 수도 있을 정도로 위험
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //브로드 캐스트 리시버 해제
        unregisterReceiver(receiver);
    }
}
