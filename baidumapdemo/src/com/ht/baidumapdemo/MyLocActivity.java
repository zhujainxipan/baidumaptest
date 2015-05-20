package com.ht.baidumapdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.*;

import java.util.List;

/**
 * Created by annuo on 2015/5/19.
 */
public class MyLocActivity extends Activity implements BDLocationListener {
    public LocationClient mLocationClient = null;
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private Marker marker;
    private PoiSearch mPoiSearch;
    private Circle circle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);
        mapView = (MapView) findViewById(R.id.locbmapView);
        mBaiduMap = mapView.getMap();

        //定位
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向

        mLocationClient.setLocOption(option);
        mLocationClient.start();
        if (mLocationClient != null && mLocationClient.isStarted())
            mLocationClient.requestLocation();
        //注册监听函数
        mLocationClient.registerLocationListener(this);
    }

    //获取定位返回的结果
    @Override
    public void onReceiveLocation(BDLocation location) {

        //************************************查找个人的位置并显示***********************
        if (location == null)
            return;
        //定位的纬度、精度
        double latitude = location.getLatitude();
        double lontitude = location.getLongitude();
        //得到城市信息以及具体的位置信息
        String address = location.getAddrStr();
        String city = location.getCity();

        //建立一个marker
        if (marker == null) {
            LatLng point = new LatLng(latitude, lontitude);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.
                    fromResource(R.drawable.ic_launcher);
            Bundle bundle = new Bundle();
            bundle.putString("name", city);
            bundle.putString("address", address);
            OverlayOptions options1 = new MarkerOptions().position(point).
                    icon(bitmapDescriptor).extraInfo(bundle);
            marker = (Marker) mBaiduMap.addOverlay(options1);


            //放在这里可以避免每5秒请求一次时又进行定位，只有第一次的时候才缩放地图，设置中心
            //把地图缩放
            MapStatusUpdate zoomTo = MapStatusUpdateFactory.zoomTo(20);
            mBaiduMap.setMapStatus(zoomTo);
            //把当前位置设置为地图中心
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(new LatLng(latitude, lontitude));
            mBaiduMap.setMapStatus(mapStatusUpdate);
        }

        //以自己为中心画一个半径为1000米的圆，不放这里，就会不断的圆
        if (circle == null) {
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(latitude, lontitude))
                    .radius(1000)
                    .stroke(new Stroke(5, 0xAA00FF00))
                    .fillColor(0x0f87CEFA);
            circle = (Circle) mBaiduMap.addOverlay(circleOptions);
        }

        //保证动态的变动圆的位置
        circle.setCenter(new LatLng(latitude, lontitude));

        //可以实现动态的跟踪我们的位置
        marker.setPosition(new LatLng(latitude, lontitude));

        //poi查询部分
        mPoiSearch = PoiSearch.newInstance();
        //查找周围的美食
        getPoiMeiShi(latitude, lontitude);

        //给marker设置一个监听器，点击marker的时候显示具体的地址信息
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MyLocActivity.this, new StringBuffer()
                                .append("name:" + marker.getExtraInfo().getString("name"))
                                .append("\naddress:" + marker.getExtraInfo().getString("address")).toString(),
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    //查找1000米内的美食
    private void getPoiMeiShi(double latitude, double lontitude) {
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(latitude, lontitude))
                .keyword("美食")
                .radius(1000));
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null) {
                    return;
                }
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                for (PoiInfo poiInfo : allPoi) {
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.
                            fromResource(R.drawable.marker);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", poiInfo.name);
                    bundle.putString("address", poiInfo.address);
                    OverlayOptions options1 = new MarkerOptions()
                            .position(poiInfo.location)
                            .icon(bitmapDescriptor).extraInfo(bundle);
                    marker = (Marker) mBaiduMap.addOverlay(options1);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }
}