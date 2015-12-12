package com.example.gethostip;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        textView = (TextView) findViewById(R.id.tv);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hostIp = getHostIp();
                //String hostIp = intToIp(getHostIp1());
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + hostIp);
                textView.setText(hostIp);
            }
        });
    }

    /**
     * 从GPRS状态获取手机ip。。。其实加上是否为ipv4格式判断就可以不去判断网络状态
     *
     * @return
     */
    private String getHostIp() {
        try {
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements(); ) {
                NetworkInterface networkInterface = enumeration.nextElement();
                for (Enumeration<InetAddress> ipAddr = networkInterface.getInetAddresses(); ipAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    //inetAddress instanceof Inet4Address 判断ip地址是否为IPV4格式
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        Log.i("cc", inetAddress.toString());
                        // Log.i("cc",inetAddress.getHostName()+">>>>>>>>"+inetAddress.getHostAddress());
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从wifi管理器获取手机当前ip
     *
     * @return
     */
    private int getHostIp1() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return ipAddress;
    }

    /**
     * 将获取到的ip值转换为标准字符串
     *
     * @param ip 初始的ip值
     * @return
     */
    private String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }
}
