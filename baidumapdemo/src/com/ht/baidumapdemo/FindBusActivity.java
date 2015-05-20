package com.ht.baidumapdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.*;

import java.util.List;

/**
 * Created by annuo on 2015/5/20.
 */
public class FindBusActivity extends Activity {

    private PoiSearch poiSearch;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_findbus);
        textView = (TextView) findViewById(R.id.info);
        poiSearch = PoiSearch.newInstance();
        getBus();
    }

    /**
     * 查找指定的公交车列表
     */
    public void getBus() {
        final PoiSearch poiSearch = PoiSearch.newInstance();
        PoiCitySearchOption option = new PoiCitySearchOption();
        option.city("北京").keyword("518");
        poiSearch.searchInCity(option);
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                                                      @Override
                                                      public void onGetPoiResult(PoiResult poiResult) {
                                                          List<PoiInfo> allPoi = poiResult.getAllPoi();
                                                          PoiInfo poiBusLine = null;
                                                          for (PoiInfo poiInfo : allPoi) {
                                                              //检测PoiInfo的类型，是否是公交车
                                                              PoiInfo.POITYPE type = poiInfo.type;
                                                              if (type == PoiInfo.POITYPE.BUS_LINE) {
                                                                  poiBusLine = poiInfo;
                                                                  break;
                                                              }
                                                          }
                                                          if (poiBusLine != null) {
                                                              BusLineSearch busLineSearch = BusLineSearch.newInstance();
                                                              BusLineSearchOption bso = new BusLineSearchOption();
                                                              bso.city("北京").uid(poiBusLine.uid);
                                                              busLineSearch.searchBusLine(bso);
                                                              busLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
                                                                  @Override
                                                                  public void onGetBusLineResult(BusLineResult busLineResult) {
                                                                      List<BusLineResult.BusStation> stations = busLineResult.getStations();
                                                                      StringBuffer stringBuffer = new StringBuffer();
                                                                      if (stations != null) {
                                                                          for (BusLineResult.BusStation station : stations) {
                                                                              String title = station.getTitle();
                                                                              stringBuffer.append(title + "\n");
                                                                          }
                                                                      }
                                                                      stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                                                                      textView.setText(stringBuffer.toString());
                                                                  }
                                                              });
                                                          }

                                                      }

                                                      @Override
                                                      public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                                                          String detailUrl = poiDetailResult.getDetailUrl();
                                                          Log.d("poi", detailUrl);

                                                      }
                                                  }
        );
    }
}
