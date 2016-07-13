package com.teapopo.life.model.address;

import com.teapopo.life.model.address.district.Area;
import com.teapopo.life.model.address.district.City;
import com.teapopo.life.model.address.district.Province;

import java.util.ArrayList;

/**
 * Created by louiszgm on 2016/7/12.
 * 3级别联动地区选择器需要的数据集
 */
public class DistrictPickerData {
    public ArrayList<Province> options1Items = new ArrayList<Province>();
    public ArrayList<ArrayList<City>> options2Items = new ArrayList<ArrayList<City>>();
    public ArrayList<ArrayList<ArrayList<Area>>> options3Items = new ArrayList<ArrayList<ArrayList<Area>>>();


}
