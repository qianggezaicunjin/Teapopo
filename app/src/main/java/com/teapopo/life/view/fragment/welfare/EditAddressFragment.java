package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.teapopo.life.MyApplication;
import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentEditAddressBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.address.SelectedAddress;
import com.teapopo.life.model.address.editAddress.EditAddressModel;
import com.teapopo.life.model.welfare.Address;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.WheelView.PickerView.OptionsPickerView;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.address.EditAddressViewModel;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/12.
 */
public class EditAddressFragment extends SwipeBackBaseFragment implements OptionsPickerView.OnOptionsSelectListener{

    private FragmentEditAddressBinding mBinding;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    @Inject
    EditAddressViewModel mViewModel;

    @Inject
    RxBus mRxBus;
    OptionsPickerView optionsPickerView;
    private SelectedAddress selectedAddress;

    public static EditAddressFragment newInstance(){
        return new EditAddressFragment();
    }
    @Override
    public void onCreateBinding() {
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);
        observWhenAddAddressDone();
    }

    private void observWhenAddAddressDone() {
        Observable<Address> observable = mRxBus.toObserverable(Address.class);
        compositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<Address>() {
                    @Override
                    public void _onNext(Address address) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("address",address);
                        setFramgentResult(1,bundle);
                        pop();
                    }

                    @Override
                    public void _onError(String s) {

                    }
                }));
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEditAddressBinding.inflate(inflater);
        mBinding.setViewmodel(mViewModel);
        mBinding.setHandler(this);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpAddressPicker();
    }

    private void setUpAddressPicker() {
        //获取地区数据
        mViewModel.getDistrictData();
    }

    private void showAddressPicker(){
        optionsPickerView  = new OptionsPickerView(_mActivity);
        optionsPickerView.setPicker(mViewModel.districtPickerData.options1Items, mViewModel.districtPickerData.options2Items, mViewModel.districtPickerData.options3Items, true);
        optionsPickerView.setTitle("选择城市");
        optionsPickerView.setCyclic(false, false, false );
        //设置默认选中的三级项目
        //监听确定选择按钮
        optionsPickerView.setSelectOptions(1, 1, 1);
        optionsPickerView.setOnoptionsSelectListener(this);
        optionsPickerView.show();
    }

    public void clickDistrict(View view){
        showAddressPicker();
    }
    public void clickAddAddress(View view){
        String truename = mBinding.etTruename.getEditText().getText().toString();
        String phonenum = mBinding.etPhone.getEditText().getText().toString();
        String detailAddress = mBinding.etDetailAddress.getEditText().getText().toString();
        String zipcode = mBinding.etZipcode.getEditText().getText().toString();
        mViewModel.addAddress(truename,phonenum,detailAddress,zipcode,selectedAddress);
    }
    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        selectedAddress = mViewModel.getSelectedAddress(options1,option2,options3);
        mBinding.tvDistrict.setText(selectedAddress.addressInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
