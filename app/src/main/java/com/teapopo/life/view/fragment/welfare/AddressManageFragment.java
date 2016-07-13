package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentAddressManageBinding;
import com.teapopo.life.model.welfare.Address;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.AddressManageListAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.address.AddressManageViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/7/1.
 */
public class AddressManageFragment extends SwipeBackBaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener {

    private FragmentAddressManageBinding mBinding;
    @Inject
    AddressManageViewModel mViewModel;
    private AddressManageListAdapter adapter;


    public static AddressManageFragment newInstance(){
        return new AddressManageFragment();
    }
    @Override
    public void onCreateBinding() {
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddressManageBinding.inflate(inflater);
        mBinding.setViewmodel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpAddressList();
        setUpToolBar();
    }

    private void setUpToolBar() {
        mBinding.toobarAddress.inflateMenu(R.menu.menu_add);
        mBinding.toobarAddress.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startForResult(EditAddressFragment.newInstance(),1);
                return true;
            }
        });
    }

    private void setUpAddressList() {
        adapter = new AddressManageListAdapter(_mActivity,mViewModel.data);
        mBinding.rvAddressmanage.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        //获取地址
        mViewModel.getAddressList();
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        Address address = data.getParcelable("address");
        mViewModel.data.add(address);
        adapter.notifyItemInserted(mViewModel.data.size());
    }

    @Override
    public void onItemClick(View view, int position) {
        Address address = (Address) mViewModel.data.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("BaseInfo",address.getBaseInfo());
        bundle.putString("address",address.area_info);
        setFramgentResult(1,bundle);
        pop();
    }


}
