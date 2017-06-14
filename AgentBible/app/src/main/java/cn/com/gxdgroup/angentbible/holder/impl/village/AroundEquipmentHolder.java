package cn.com.gxdgroup.angentbible.holder.impl.village;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.EquipmentActivity;
import cn.com.gxdgroup.angentbible.activities.VillageInfoActivity;
import cn.com.gxdgroup.angentbible.domain.MessageEvent;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description:
 */

public class AroundEquipmentHolder extends BaseHolder {
    @BindView(R.id.mTexturemap)
    TextureMapView mMapView;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_bus)
    TextView tvBus;
    @BindView(R.id.tv_subway)
    TextView tvSubway;
    @BindView(R.id.tv_edu)
    TextView tvEdu;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_shopping)
    TextView tvShopping;
    @BindView(R.id.tv_food)
    TextView tvFood;
    @BindView(R.id.rl_around_equipment)
    RelativeLayout mRlAroundEquipment;
    @BindView(R.id.tv_health)
    TextView mTvHealth;
    private PoiSearch mPoiSearch;
    private OnGetPoiSearchResultListener poiListener;
    private BaiduMap mBaiduMap;
    private String mKeyWord;
    //    private Marker addMarker;
//    private RadarSearchManager mRadarSearchManager;

    public AroundEquipmentHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        View view = View.inflate(mActivity, R.layout.holder_around_equipment, null);
        return view;
    }


    @Override
    public void initView() {

        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);//隐藏缩放按钮
        mPoiSearch = PoiSearch.newInstance();//创建POI检索实例
//        mRadarSearchManager = RadarSearchManager.getInstance();//初始化周边雷达功能

//        mRadarSearchManager.addNearbyInfoListener(new RadarSearchListener() {
//            @Override
//            public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError) {
//                L.v("---onGetNearbyInfoList--");
//
//                if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
//                    Toast.makeText(mActivity, "查询周边成功", Toast.LENGTH_LONG).show();
//                    //获取成功，处理数据
//                } else {
//                    //获取失败
//                    Toast.makeText(mActivity, "查询周边失败", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onGetUploadState(RadarSearchError radarSearchError) {
//                L.v("---onGetUploadState--");
//            }
//
//            @Override
//            public void onGetClearInfoState(RadarSearchError radarSearchError) {
//                L.v("---onGetClearInfoState--");
//            }
//        });

        //创建POI检索监听者
        poiListener = new OnGetPoiSearchResultListener() {
            public void onGetPoiResult(PoiResult result) {
                //获取POI检索结果

                MessageEvent event = new MessageEvent(1, "123");
                EventBus.getDefault().post(event);

                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    L.v("search--error");
                    return;
                }
                L.v("---获取POI检索结果--" + result.getAllPoi().size());
                List<PoiInfo> allPoi = result.getAllPoi();

                for (int i = 0; i < allPoi.size(); i++) {
                    PoiInfo p = allPoi.get(i);
                    L.v(i + "--address:" + p.address + "--name:" + p.name + "--location:" + p.location.longitude + "," + p.location.latitude + "---phoneNum:" + p.phoneNum + "--postCode:" + p.postCode);
                }


                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    mBaiduMap.clear();
                    //创建PoiOverlay
                    PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                    //设置overlay可以处理标注点击事件
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    //设置PoiOverlay数据
                    overlay.setData(result, getMarkerByKeyWord());
                    //添加PoiOverlay到地图中
                    overlay.addToMap();
//                    overlay.zoomToSpan();
                    return;
                }


                //Method 1
//                List<PoiInfo> allPoi = result.getAllPoi();
//                for (int i = 0; i < allPoi.size(); i++) {
//                    double latitude = allPoi.get(i).location.latitude;
//                    double longitude = allPoi.get(i).location.longitude;
//                    LatLng point = new LatLng(latitude, longitude);
//
//                    BitmapDescriptor bitmap = getMarkerByKeyWord();
//
//                    //构建MarkerOption，用于在地图上添加Marker
//                    OverlayOptions option = new MarkerOptions()
//                            .position(point)
//                            .icon(bitmap);
//                    //在地图上添加Marker，并显示
//
//                    mBaiduMap.addOverlay(option);
////                    addMarker = (Marker) mBaiduMap.addOverlay(option);
//                }
            }

            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果
                L.v("---获取Place详情页检索结果--");
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);


        // 设置可改变地图位置
        mBaiduMap.setMyLocationEnabled(true);


        // 解决ScrollView中嵌套百度地图（BaiduMap）的解决方案
        View v = mMapView.getChildAt(0);
        if (mActivity instanceof VillageInfoActivity) {
            v.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    ScrollView scrollView = ((VillageInfoActivity) mActivity).getScrollView();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        scrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        scrollView.requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }


            });
        }

        locationTo(31.1882, 121.433346);
    }


    private void locationTo(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(0)////自定义误差半径
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0)
                .latitude(latitude)
                .longitude(longitude).build();

        int zoom = 15;//地图缩放级别 3~21

        MapStatus sta = new MapStatus.Builder().target(latLng).zoom(zoom).build();

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(sta);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.ico_house);
//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
//        mBaiduMap.setMyLocationConfigeration(config);


        //定义Maker坐标点
        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.ico_house);
//        //构建MarkerOption，用于在地图上添加Marker
//
//        OverlayOptions option = new MarkerOptions()
//                .position(latLng)
//                .icon(bitmap);
//        //在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option);


        // 当不需要定位图层时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);


//        // 按照经纬度确定地图位置
//        if (ifFrist) {
//            LatLng ll = new LatLng(latitude, longitude);
//            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
//            // 移动到某经纬度
//            mBaiduMap.animateMapStatus(update);
//            update = MapStatusUpdateFactory.zoomBy(5f);
//            // 放大
//            mBaiduMap.animateMapStatus(update);
//
//            ifFrist = false;
//        }
//        // 显示个人位置图标
//        MyLocationData.Builder builder = new MyLocationData.Builder();
//        builder.latitude(latitude);
//        builder.longitude(longitude);
//        MyLocationData data = builder.build();
//        mBaiduMap.setMyLocationData(data);
    }

    @OnClick(R.id.rl_around_equipment)
    public void onClick() {
        mActivity.startActivity(new Intent(mActivity, EquipmentActivity.class));
    }

    public void showMapTitleBar(boolean b) {
        mRlAroundEquipment.setVisibility(b ? View.VISIBLE : View.GONE);
    }


    private class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);

            PoiInfo info = getPoiResult().getAllPoi().get(index);
            L.v("--click--" + index + "," + info.location.longitude);


            //创建InfoWindow展示的view

            View view = UIUtils.inflate(R.layout.view_map_pop_maker);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            tvName.setText(info.name);
            //定义用于显示该InfoWindow的坐标点
            LatLng pt = info.location;
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            InfoWindow mInfoWindow = new InfoWindow(view, pt, -47);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);

            return true;
        }
    }


    /**
     * 通过keyword返回标注
     *
     * @return
     */
    private int getMarkerByKeyWord() {
        int sourceId = R.drawable.bank2;
        switch (mKeyWord) {
            case "银行":
                sourceId = R.drawable.bank2;
                break;
            case "公交":
                sourceId = R.drawable.bus2;
                break;
            case "地铁":
                sourceId = R.drawable.subway2;
                break;
            case "学校":
                sourceId = R.drawable.school2;
                break;
            case "医院":
                sourceId = R.drawable.hospital2;
                break;
            case "购物":
                sourceId = R.drawable.supermaket2;
                break;
            case "美食":
                sourceId = R.drawable.food2;
                break;
            case "健身":
                sourceId = R.drawable.btn_health2;
                break;
        }
        return sourceId;
//        return BitmapDescriptorFactory.fromResource(sourceId);
    }

    @Override
    public void setData() {


    }

    @OnClick({R.id.tv_bank, R.id.tv_bus, R.id.tv_subway, R.id.tv_edu, R.id.tv_hospital, R.id.tv_shopping, R.id.tv_food, R.id.tv_health})
    public void onClick(View view) {

        // 恢复状态
        int colorLight = UIUtils.getColor(R.color.text_light);
        int colorBlue = UIUtils.getColor(R.color.common_blue);
        tvBank.setTextColor(colorLight);
        tvBus.setTextColor(colorLight);
        tvSubway.setTextColor(colorLight);
        tvEdu.setTextColor(colorLight);
        tvHospital.setTextColor(colorLight);
        tvShopping.setTextColor(colorLight);
        tvFood.setTextColor(colorLight);
        mTvHealth.setTextColor(colorLight);


        tvBank.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.bank), null, null);
        tvBus.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.taxi), null, null);
        tvSubway.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.subway), null, null);
        tvEdu.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.school), null, null);
        tvHospital.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.hospital), null, null);
        tvShopping.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.supermaket), null, null);
        tvFood.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.food), null, null);
        mTvHealth.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.btn_health), null, null);

        mKeyWord = "银行";
        switch (view.getId()) {
            case R.id.tv_bank:
                mKeyWord = "银行";
                tvBank.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.bank_press), null, null);
                tvBank.setTextColor(colorBlue);
                break;
            case R.id.tv_bus:
                mKeyWord = "公交";
                tvBus.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.taxi_press), null, null);
                tvBus.setTextColor(colorBlue);
                break;
            case R.id.tv_subway:
                mKeyWord = "地铁";
//                mKeyWord = "公交";
                tvSubway.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.subwa_press), null, null);
                tvSubway.setTextColor(colorBlue);
                break;
            case R.id.tv_edu:
                mKeyWord = "学校";
                tvEdu.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.schoo_press), null, null);
                tvEdu.setTextColor(colorBlue);
                break;
            case R.id.tv_hospital:
                mKeyWord = "医院";
                tvHospital.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.hospital_press), null, null);
                tvHospital.setTextColor(colorBlue);
                break;
            case R.id.tv_shopping:
                mKeyWord = "购物";
                tvShopping.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.supermaket_press), null, null);
                tvShopping.setTextColor(colorBlue);
                break;
            case R.id.tv_food:
                mKeyWord = "美食";
                tvFood.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.food_press), null, null);
                tvFood.setTextColor(colorBlue);
                break;
            case R.id.tv_health:
                mKeyWord = "健身";
                mTvHealth.setCompoundDrawables(null, UIUtils.getDrawable(R.drawable.btn_health_press), null, null);
                mTvHealth.setTextColor(colorBlue);
                break;
        }

        L.v("search.1..." + mKeyWord);

        LatLng pt = new LatLng(31.1882, 121.433346);
        PoiNearbySearchOption searchOption = new PoiNearbySearchOption();
        searchOption.keyword(mKeyWord)
                .location(pt)
                .pageCapacity(10)
                .pageNum(1)
                .sortType(PoiSortType.distance_from_near_to_far)
                .radius(5000);


        mPoiSearch.searchNearby(searchOption);


//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city("北京")
//                .keyword(mKeyWord)
//                .pageNum(10));

        //周边位置检索
//
        //构造请求参数，其中centerPt是自己的位置坐标
//        RadarNearbySearchOption option = new RadarNearbySearchOption().centerPt(pt).pageNum(10).radius(2000);
        //发起查询请求
//        mRadarSearchManager.nearbyInfoRequest(option);

    }

    public TextureMapView getMapView() {
        return mMapView;
    }
}
