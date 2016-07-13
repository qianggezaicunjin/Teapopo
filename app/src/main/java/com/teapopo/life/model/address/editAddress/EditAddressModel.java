package com.teapopo.life.model.address.editAddress;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.address.DistrictPickerData;
import com.teapopo.life.model.address.district.Area;
import com.teapopo.life.model.address.district.City;
import com.teapopo.life.model.address.district.Province;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.FileUtils;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/7/12.
 */
public class EditAddressModel extends BaseModel {

    public EditAddressModel(Context context) {
        super(context);
    }

    public void getDistrictPickerData(){
         Observable.just(getDistrictData())
                 .subscribeOn(Schedulers.io())
                 .observeOn(mDataManager.getScheduler())
                 .flatMap(new Func1<ArrayList<Province>, Observable<DistrictPickerData>>() {
                     @Override
                     public Observable<DistrictPickerData> call(ArrayList<Province> provinces) {
                         DistrictPickerData districtPickerData = tranformToDistrictPickerData(provinces);
                         return Observable.just(districtPickerData);
                     }
                 })
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new RxSubscriber<DistrictPickerData>() {
                     @Override
                     public void _onNext(DistrictPickerData districtPickerData) {
                         ModelAction modelAction = new ModelAction();
                         modelAction.action = Action.EditAddressModel_GetDistrictPickerData;
                         modelAction.t = districtPickerData;
                         mRequestView.onRequestSuccess(modelAction);
                     }

                     @Override
                     public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                     }
                 });
    }

    /**
     * 获得3级联动地区选择器所需要的数据
     * @return
     */
    private DistrictPickerData tranformToDistrictPickerData(ArrayList<Province> provinceList){
        DistrictPickerData districtPickerData = new DistrictPickerData();
         ArrayList<ArrayList<City>> options2Items = new ArrayList<ArrayList<City>>();
         ArrayList<ArrayList<ArrayList<Area>>> options3Items = new ArrayList<ArrayList<ArrayList<Area>>>();

        for(Province province:provinceList){
            options2Items.add(province.cityList);
            ArrayList<ArrayList<Area>> options3Items_province = new ArrayList<ArrayList<Area>>();
            for(City city:province.cityList){
                options3Items_province.add(city.areaList);
            }
            options3Items.add(options3Items_province);
        }
        districtPickerData.options1Items = provinceList;
        districtPickerData.options2Items = options2Items;
        districtPickerData.options3Items = options3Items;
        return districtPickerData;
    }

    /**
     * 解析存储在assert下面的  district.json文件
     * @return
     */
    private ArrayList<Province> getDistrictData(){
        ArrayList<Province> provinceList = new ArrayList<>();
        try {
            String json = FileUtils.readFromInputStream(mContext.getAssets().open("district.json"),"UTF-8").toString();
            provinceList = (ArrayList<Province>) LoganSquare.parseList(json,Province.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return provinceList;
    }
}
