package com.dannydiao.kuaixing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;

public class MiddleFragment extends Fragment {
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    public List<String> roadResult;
    public LatLonPoint from;
    GeocodeResult geocodeResult1;
    LatLonPoint to;
    LatLonPoint temp;
    EditText from_input;
    EditText to_input;
    int continue_flag = 0;
    ProgressBar progressBar;


    public void setFrom(LatLonPoint from) {
        this.from = from;
    }

    public void setTo(LatLonPoint to) {
        this.to = to;
    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            String from_text = from_input.getText().toString();
            String to_text = to_input.getText().toString();

            GeocodeSearch from_geocodeSearch = new GeocodeSearch(getContext());
            from_geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    setFrom(geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint());
                    continue_flag++;
                }
            });
            GeocodeQuery from_geoquery = new GeocodeQuery(from_text,"028");
            from_geocodeSearch.getFromLocationNameAsyn(from_geoquery);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }



            GeocodeSearch to_geocodeSearch = new GeocodeSearch(getContext());
            to_geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    setTo(geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint());
                    continue_flag++;
                    RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from,to);
                    RouteSearch routeSearch = new RouteSearch(getContext());

                    RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, 0, null, null, "");

                    routeSearch.calculateDriveRouteAsyn(query);

                    routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
                        @Override
                        public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

                        }

                        @Override
                        public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                            List<DrivePath> ListDrivePath = driveRouteResult.getPaths();

                            List<DriveStep> stepList = new ArrayList<>();
                            for(DrivePath drivePath:ListDrivePath){
                                for(int j=0;j<drivePath.getSteps().size();j++){
                                    stepList.add(drivePath.getSteps().get(j));
                                }
                            }

                            List<String> roadList = new ArrayList<>();
                            for (int k=0;k<stepList.size();k++){
                                roadList.add(stepList.get(k).getRoad());
                            }


                            setRoad(roadList);
                            progressBar.setVisibility(View.GONE);



                        }

                        @Override
                        public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

                        }

                        @Override
                        public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

                        }
                    });
                }
            });
            GeocodeQuery to_geoquery = new GeocodeQuery(to_text,"028");
            to_geocodeSearch.getFromLocationNameAsyn(to_geoquery);








        }
    };




    //@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.middle,container,false);

        //绑定页面上的控件
        from_input = view.findViewById(R.id.from_input);
        to_input = view.findViewById(R.id.to_input);
        Button search_button = view.findViewById(R.id.search_button);
        progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);
        Wave wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        progressBar.setVisibility(View.GONE);

        search_button.setOnClickListener(onClickListener);



        //从文本框获取文字信息


        //通过传入起点终点的经纬度，构造两个LatLonPoint对象
       // LatLonPoint from = new LatLonPoint(30.6745204310,104.0973633528);
        //LatLonPoint to = new LatLonPoint(30.7542466697,103.9254498482);
        //传入起点终点这两个LatLonPoint对象，构造FromAndTo对象





        return view;
    }
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MiddleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiddleFragment newInstance(String param1, String param2) {
        MiddleFragment fragment = new MiddleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setRoad(List<String> road){
        roadResult = road;

    }
}
