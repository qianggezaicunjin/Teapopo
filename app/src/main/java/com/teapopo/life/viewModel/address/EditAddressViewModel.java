package com.teapopo.life.viewModel.address;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.address.DistrictPickerData;
import com.teapopo.life.model.address.SelectedAddress;
import com.teapopo.life.model.address.district.Area;
import com.teapopo.life.model.address.district.City;
import com.teapopo.life.model.address.district.Province;
import com.teapopo.life.model.address.editAddress.EditAddressModel;
import com.teapopo.life.model.welfare.Address;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/12.
 */
public class EditAddressViewModel extends BaseViewModel {

    private EditAddressModel mModel;

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
        }else if(action == Action.EditAddressModel_AddAdress){
            Address address = (Address) data.t;
            ComponentHolder.getAppComponent().rxbus().post(address);
        }
    }


    public SelectedAddress getSelectedAddress(int options1, int option2, int options3) {
        SelectedAddress selectedAddress = new SelectedAddress();

        StringBuilder address = new StringBuilder();
        Province province = districtPickerData.options1Items.get(options1);
        City city = districtPickerData.options2Items.get(options1).get(option2);
        Area area = new Area();
        address.append(province.name);
        address.append(",");
        address.append(city.name);
        if(districtPickerData.options3Items.get(options1).get(option2)!=null){
            area = districtPickerData.options3Items.get(options1).get(option2).get(options3);
            address.append(",");
            address.append(area.name);
        }else {
            area.id = "0";
        }

        selectedAddress.addressInfo = address.toString();
        selectedAddress.provinceId = province.id;
        selectedAddress.cityId = city.id;
        selectedAddress.areaId = area.id;
        return selectedAddress;
    }

    public void addAddress(String truename, String phonenum, String detailAddress, String zipcode, SelectedAddress selectedAddress) {
        mModel.addAddress(truename,phonenum,selectedAddress,detailAddress,zipcode);
    }
}
