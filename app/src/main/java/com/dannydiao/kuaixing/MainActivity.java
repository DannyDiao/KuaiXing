package com.dannydiao.kuaixing;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMyLocationChangeListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    MapView mainMap = null;
    CheckBox traffic_checkbox;
    AMap aMap = null;
    private UiSettings uiSettings;
    int CHANGE_STYLE_FLAG = 0;
    int CHANGE_TRAFFIC_FLAG = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //请求权限
        if((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }



        setContentView(R.layout.activity_main);

        //绑定mainMap控件
        mainMap = (MapView)findViewById(R.id.main_map);
        mainMap.onCreate(savedInstanceState);

        //绑定aMap控件
        if (aMap == null) {
            aMap = mainMap.getMap();
        }

        //高德SDK中设置地图样式的方法
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.showIndoorMap(true);
        UISetiings();
        aMap.animateCamera( CameraUpdateFactory.zoomTo(15));


         //监听FAB按钮实现开/关实时路况信息
         CHANGE_TRAFFIC_FLAG = 0;
         FloatingActionButton fab_traffic;
         fab_traffic = findViewById(R.id.fab_change_traffic);
         fab_traffic.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if((CHANGE_TRAFFIC_FLAG % 2) == 0){
                     aMap.setTrafficEnabled(true);
                     Toast.makeText(MainActivity.this,"实时路况开",Toast.LENGTH_SHORT).show();
                     CHANGE_TRAFFIC_FLAG++;
                     return;
                 }
                 if((CHANGE_TRAFFIC_FLAG % 2) == 1){
                     aMap.setTrafficEnabled(false);
                     Toast.makeText(MainActivity.this,"实时路况关",Toast.LENGTH_SHORT).show();
                     CHANGE_TRAFFIC_FLAG++;
                     return;
                 }

             }
         });

         //监听FAB按钮实现切换地图样式
        FloatingActionButton fab_button;
        fab_button = findViewById(R.id.fab);
        CHANGE_STYLE_FLAG = 0;
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((CHANGE_STYLE_FLAG % 3) == 0){
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                    CHANGE_STYLE_FLAG++;
                    Toast.makeText(MainActivity.this,"卫星地图",Toast.LENGTH_SHORT).show();
                    return;
                }

                if((CHANGE_STYLE_FLAG % 3) == 1){
                    aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                    CHANGE_STYLE_FLAG++;
                    Toast.makeText(MainActivity.this,"夜间地图",Toast.LENGTH_SHORT).show();
                    return;
                }

                if((CHANGE_STYLE_FLAG % 3) == 2){
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    CHANGE_STYLE_FLAG++;
                    Toast.makeText(MainActivity.this,"普通地图",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


    //封装UISettings到方法中
    public void UISetiings(){
        uiSettings = aMap.getUiSettings();

        //uiSettings.setCompassEnabled(true);
        uiSettings.setScaleControlsEnabled(true);
    }

    //监听权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    return;
                }else{
                    Toast.makeText(this,"不给权限还想用？做梦吧哈哈哈",Toast.LENGTH_LONG).show();

                     Finish();
                }
        }
    }
    //封装finish()方法
    public void Finish(){
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainMap.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainMap.onSaveInstanceState(outState);
    }
}
