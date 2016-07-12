package com.teapopo.life.model.address.editAddress;

import com.bluelinelabs.logansquare.LoganSquare;
import com.teapopo.life.BuildConfig;
import com.teapopo.life.model.address.district.Province;
import com.teapopo.life.util.FileUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/12.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EditAddressModelTest {

    private String JSON_ROOT_PATH = "/json/";
    private String jsonFullPath;
    @Before
    public void setUp() throws Exception {
        //输出日志
        ShadowLog.stream = System.out;
        //获取测试json文件地址
        jsonFullPath = getClass().getResource(JSON_ROOT_PATH).toURI().getPath();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDistrictData()throws Exception{
        StringBuilder stringBuilder = FileUtils.readFile(jsonFullPath + "district.json", "UTF-8");
       List<Province> provinceList = LoganSquare.parseList(stringBuilder.toString(),Province.class);
        Timber.d("省份有%d",provinceList.size());
        for(Province p:provinceList){
            Timber.d("对应的城市有:%d",p.cityList.size());
        }
    }
}