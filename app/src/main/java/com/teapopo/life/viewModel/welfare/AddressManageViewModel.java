package com.teapopo.life.viewModel.welfare;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.welfare.Address;
import com.teapopo.life.model.welfare.AddressManageModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/1.
 */
public class AddressManageViewModel extends BaseRecyclerViewModel {

    private AddressManageModel mModel;

    public AddressManageViewModel(AddressManageModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getAddressList(){
        mModel.getAddressList();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.AddressManageModel_GetAddressLsit){
            List<Address> list = (List<Address>) data.t;
            super.data.addAll(list);
            notifyPropertyChanged(BR.data);
        }
    }
}
