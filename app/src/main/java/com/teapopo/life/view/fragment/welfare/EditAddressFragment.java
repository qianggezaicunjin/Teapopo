package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.OptionsPickerView;
import com.teapopo.life.MyApplication;
import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentEditAddressBinding;
import com.teapopo.life.model.address.editAddress.EditAddressModel;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.address.EditAddressViewModel;

/**
 * Created by louiszgm on 2016/7/12.
 */
public class EditAddressFragment extends SwipeBackBaseFragment {

    private FragmentEditAddressBinding mBinding;

    EditAddressViewModel mViewModel;

    OptionsPickerView optionsPickerView;
    public static EditAddressFragment newInstance(){
        return new EditAddressFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEditAddressBinding.inflate(inflater);
        mViewModel = new EditAddressViewModel(new EditAddressModel(_mActivity));
        mBinding.setViewmodel(mViewModel);
        mBinding.setHandler(this);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpAddressPicker();
    }

    private void setUpAddressPicker() {
        optionsPickerView  = new OptionsPickerView(_mActivity);

        mViewModel.getDistrictData();
    }

    public void clickSelectAddress(View view){
        //三级联动效果
        optionsPickerView.setPicker(mViewModel.districtPickerData.options1Items, mViewModel.districtPickerData.options2Items, mViewModel.districtPickerData.options3Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        optionsPickerView.setTitle("选择城市");
        optionsPickerView.setCyclic(true, true, true );
        //设置默认选中的三级项目
        //监听确定选择按钮
        optionsPickerView.setSelectOptions(1, 1, 1);
        optionsPickerView.show();
    }
}
