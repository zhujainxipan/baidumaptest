package com.ht.baidumapdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class BasicuseActivity extends Activity {

    private MapView mapView;
    private BaiduMap mBaiduMap;
    private InfoWindow infoWindow;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        mapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mapView.getMap();


        //建立第一个marker
        LatLng point = new LatLng(40.963175, 116.400344);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.
                fromResource(R.drawable.marker);
        OverlayOptions options1 = new MarkerOptions().position(point).
                icon(bitmapDescriptor).title("1");
        mBaiduMap.addOverlay(options1);

        //建立第2个marker
        LatLng point2 = new LatLng(39.963175, 116.40024);
        BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.
                fromResource(R.drawable.ic_launcher);
        OverlayOptions options2 = new MarkerOptions().position(point2).
                icon(bitmapDescriptor2).title("2");
        mBaiduMap.addOverlay(options2);


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                switch (marker.getTitle()) {
                    case "1" :
                        // 创建LinearLayout，垂直的，再添加一个TextView
                        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                        // 垂直布局
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        TextView textView = new TextView(getApplicationContext());
                        textView.setText("11111");
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(30);
                        linearLayout.addView(textView);
                        // 构造InfoWindow 用于显示内容
                        LatLng location = new LatLng(40.025814, 116.394558);
                        infoWindow = new InfoWindow(linearLayout, location, 47);
                        // 显示信息窗口
                        mBaiduMap.showInfoWindow(infoWindow);
                        break;
                    case "2":
                        // 创建LinearLayout，垂直的，再添加一个TextView
                        LinearLayout linearLayout2 = new LinearLayout(getApplicationContext());
                        // 垂直布局
                        linearLayout2.setOrientation(LinearLayout.VERTICAL);
                        TextView textView2 = new TextView(getApplicationContext());
                        textView2.setText("22222");
                        textView2.setTextColor(Color.BLACK);
                        textView2.setTextSize(30);
                        linearLayout2.addView(textView2);
                        // 构造InfoWindow 用于显示内容
                        LatLng location2 = new LatLng(40.025814, 116.394558);
                        infoWindow = new InfoWindow(linearLayout2, location2, 47);
                        // 显示信息窗口
                        mBaiduMap.showInfoWindow(infoWindow);
                        break;
                }
                return true;
            }
        });

        //定义多边形的五个顶点
        LatLng pt1 = new LatLng(39.93923, 116.357428);
        LatLng pt2 = new LatLng(39.91923, 116.327428);
        LatLng pt3 = new LatLng(39.89923, 116.347428);
        LatLng pt4 = new LatLng(39.89923, 116.367428);
        LatLng pt5 = new LatLng(39.91923, 116.387428);
        List<LatLng> pts = new ArrayList<LatLng>();
        pts.add(pt1);
        pts.add(pt2);
        pts.add(pt3);
        pts.add(pt4);
        pts.add(pt5);
        //构建用户绘制多边形的Option对象
        OverlayOptions polyoptions = new PolygonOptions()
                .points(pts)
                .stroke(new Stroke(5, 0XAA00FF00))
                .fillColor(0XAAFFFF00);
        mBaiduMap.addOverlay(polyoptions);
        LatLng pt11 = new LatLng(40.93923, 116.357428);
        LatLng pt21 = new LatLng(40.91923, 116.327428);
        LatLng pt31 = new LatLng(40.89923, 116.347428);
        ArcOptions arcOptions = new ArcOptions().points(pt11, pt21, pt31).color(0XAAFFFF00);
        mBaiduMap.addOverlay(arcOptions);


        //定义文字所显示的坐标点
        LatLng llText = new LatLng(39.86923, 116.397428);
        //构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(24)
                .fontColor(0xFFFF00FF)
                .text("百度地图SDK")
                .rotate(-30)
                .position(llText);
        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);
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
}
