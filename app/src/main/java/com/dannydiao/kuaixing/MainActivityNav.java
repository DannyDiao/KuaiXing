package com.dannydiao.kuaixing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivityNav extends AppCompatActivity {



    FragmentManager fragmentManager = getSupportFragmentManager();

    LeftFragment leftFragment = LeftFragment.newInstance("Context","Context");
    Fragment currentFragment = leftFragment;
    RightFragment rightFragment = RightFragment.newInstance("Context","Context");
    MiddleFragment middleFragment = MiddleFragment.newInstance("Context","Context");

    static String[] roadStringResult = new String[300];



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_map:
                    showFragment(leftFragment);
                    return true;
                case R.id.nav_route:
                    showFragment(middleFragment);
                    return true;
                case R.id.nav_about:
                    showFragment(rightFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //请求权限
        if((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(MainActivityNav.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }

        Toast.makeText(MainActivityNav.this,"这是快行" + Util.getVerName(this) + "版本" ,Toast.LENGTH_LONG).show();
        FragmentTransaction transaction1 = fragmentManager.beginTransaction();
        transaction1.add(R.id.fragment_layout,leftFragment);
        transaction1.commit();

    }

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

    public void Finish(){
        finish();
    }

    public static void setRoadString(List<String> roadList){
        String[] roadString = roadList.toArray(new String[roadList.size()]);
        roadStringResult = roadString;
    }

    private void showFragment(Fragment fg){

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //如果之前没有添加过
        if(!fg.isAdded()){
            transaction
                    .add(R.id.fragment_layout,fg)
                    .hide(currentFragment);
        }else{
            transaction
                    .hide(currentFragment)
                    .show(fg);
        }

        //全局变量，记录当前显示的fragment
        currentFragment = fg;
        //transaction.addToBackStack(null);
        transaction.commit();

    }





}
