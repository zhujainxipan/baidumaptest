package com.ht.baidumapdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.*;

import java.util.List;

/**
 * Created by annuo on 2015/5/20.
 */
public class RouteActivity extends Activity {

    private BaiduMap baiduMap;
    private MapView mapView;
    private RoutePlanSearch routePlanSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        mapView = (MapView) findViewById(R.id.route_map);
        baiduMap = mapView.getMap();

        //创建路径搜索工具
        routePlanSearch = RoutePlanSearch.newInstance();

        //设置结果接收接口
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                List<WalkingRouteLine> routeLines = walkingRouteResult.getRouteLines();
                if (routeLines != null) {
                    for (WalkingRouteLine routeLine : routeLines) {
                        List<WalkingRouteLine.WalkingStep> allStep = routeLine.getAllStep();
                        for (WalkingRouteLine.WalkingStep walkingStep : allStep) {
                            List<LatLng> wayPoints = walkingStep.getWayPoints();
                            if (wayPoints.size() >= 2) {
                                PolylineOptions polylineOptions = new PolylineOptions()
                                        .color(0xAA00FF00)
                                        .width(15);
                                polylineOptions.points(wayPoints);
                                baiduMap.addOverlay(polylineOptions);
                            }
                        }
                    }
                }

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                List<TransitRouteLine> routeLines = transitRouteResult.getRouteLines();
                if (routeLines != null) {
                    for (TransitRouteLine routeLine : routeLines) {
                        List<TransitRouteLine.TransitStep> allStep = routeLine.getAllStep();
                        for (TransitRouteLine.TransitStep transitStep : allStep) {
                            List<LatLng> wayPoints = transitStep.getWayPoints();
                            if (wayPoints.size() >= 2) {
                                PolylineOptions polylineOptions = new PolylineOptions()
                                        .color(0xAF00FFF0)
                                        .width(15);
                                polylineOptions.points(wayPoints);
                                baiduMap.addOverlay(polylineOptions);
                            }
                        }
                    }
                }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                List<DrivingRouteLine> routeLines = drivingRouteResult.getRouteLines();
                if (routeLines != null) {
                    for (DrivingRouteLine routeLine : routeLines) {
                        List<DrivingRouteLine.DrivingStep> allStep = routeLine.getAllStep();
                        for (DrivingRouteLine.DrivingStep drivingStep : allStep) {
                            List<LatLng> wayPoints = drivingStep.getWayPoints();
                            if (wayPoints.size() >= 2) {
                                PolylineOptions polylineOptions = new PolylineOptions()
                                        .color(0xAA00FFF0)
                                        .width(15);
                                polylineOptions.points(wayPoints);
                                baiduMap.addOverlay(polylineOptions);
                            }
                        }
                    }
                }

            }
        });


    }

    /**
     * 步行的规划
     *
     * @param view
     */
    public void walkOnClick(View view) {
        WalkingRoutePlanOption option = new WalkingRoutePlanOption();
        //设置搜索的参数
        //从哪里到哪里
        PlanNode from = PlanNode.withCityNameAndPlaceName("北京", "宝盛西里");
        PlanNode to = PlanNode.withCityNameAndPlaceName("北京", "北京邮电大学");
        option.from(from).to(to);
        //进行步行的路线规划
        routePlanSearch.walkingSearch(option);
    }

    /**
     * 公交的规划
     *
     * @param view
     */
    public void transitOnClick(View view) {
        TransitRoutePlanOption option = new TransitRoutePlanOption();
        PlanNode from = PlanNode.withCityNameAndPlaceName("北京", "北京大学");
        PlanNode to = PlanNode.withCityNameAndPlaceName("北京", "清华大学");
        option.city("北京").from(from).to(to);//必须加city，不然就会崩溃
        routePlanSearch.transitSearch(option);
    }


    /**
     * 自驾路线规划
     *
     * @param view
     */
    public void driverOnClick(View view) {
        DrivingRoutePlanOption option = new DrivingRoutePlanOption();
        PlanNode from = PlanNode.withCityNameAndPlaceName("北京", "北京体育大学");
        PlanNode to = PlanNode.withCityNameAndPlaceName("北京", "清华大学");
        option.from(from).to(to);
        routePlanSearch.drivingSearch(option);
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}