package com.dannydiao.kuaixing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;

public class LeftFragment extends Fragment {
    @Nullable
    MapView mainMap = null;
    CheckBox traffic_checkbox;
    AMap aMap = null;
    private UiSettings uiSettings;
    int CHANGE_STYLE_FLAG = 0;
    int CHANGE_TRAFFIC_FLAG = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public LeftFragment() {
        // Required empty public constructor
    }

    public static LeftFragment newInstance(String param1, String param2) {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    public void UISetiings(){
        uiSettings = aMap.getUiSettings();

        //uiSettings.setCompassEnabled(true);
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setZoomControlsEnabled(false);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left,container,false);


        //绑定mainMap控件
        mainMap = view.findViewById(R.id.main_map);
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
        fab_traffic = view.findViewById(R.id.fab_change_traffic);
        fab_traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((CHANGE_TRAFFIC_FLAG % 2) == 0){
                    aMap.setTrafficEnabled(true);
                    Toast.makeText(getActivity(),"实时路况开",Toast.LENGTH_SHORT).show();
                    CHANGE_TRAFFIC_FLAG++;
                    return;
                }
                if((CHANGE_TRAFFIC_FLAG % 2) == 1){
                    aMap.setTrafficEnabled(false);
                    Toast.makeText(getActivity(),"实时路况关",Toast.LENGTH_SHORT).show();
                    CHANGE_TRAFFIC_FLAG++;
                    return;
                }

            }
        });




        //监听FAB按钮实现切换地图样式
        FloatingActionButton fab_button;
        fab_button = view.findViewById(R.id.fab);
        CHANGE_STYLE_FLAG = 0;
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((CHANGE_STYLE_FLAG % 3) == 0){
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                    CHANGE_STYLE_FLAG++;
                    Toast.makeText(getActivity(),"卫星地图",Toast.LENGTH_SHORT).show();
                    return;
                }

                if((CHANGE_STYLE_FLAG % 3) == 1){
                    aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                    CHANGE_STYLE_FLAG++;
                    Toast.makeText(getActivity(),"夜间地图",Toast.LENGTH_SHORT).show();
                    return;
                }

                if((CHANGE_STYLE_FLAG % 3) == 2){
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    CHANGE_STYLE_FLAG++;
                    Toast.makeText(getActivity(),"普通地图",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    return view;
}


    @Override
    public void onDestroy() {
        super.onDestroy();
        mainMap.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mainMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mainMap.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainMap.onSaveInstanceState(outState);
    }


}
