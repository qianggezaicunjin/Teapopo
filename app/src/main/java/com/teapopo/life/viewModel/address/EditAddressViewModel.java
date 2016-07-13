package com.teapopo.life.viewModel.address;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.address.DistrictPickerData;
import com.teapopo.life.model.address.editAddress.EditAddressModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/12.
 */
public class EditAddressViewModel extends BaseViewModel {

    private EditAddressModel mModel;
    @Bindable
    public DistrictPickerData districtPickerData = new DistrictPickerData();
    public EditAddressViewModel(EditAddressModel model){
        mModel = model;
        mModel.setView(this);
    }


    public void getDistrictData(){
        mModel.getDistrictPickerData();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.EditAddressModel_GetDistrictPickerData){
            districtPickerData = (DistrictPickerData) data.t;
            Timber.d("获取地区数据成功");
            Timber.d("省有:%d",districtPickerData.options1Items.size());
            Timber.d("市有：%d",districtPickerData.options2Items.get(0).size());
            Timber.d("区有:%d",districtPickerData.options3Items.get(0).get(0).size());
            notifyPropertyChanged(BR.districtPickerData);
        }
    }
}
