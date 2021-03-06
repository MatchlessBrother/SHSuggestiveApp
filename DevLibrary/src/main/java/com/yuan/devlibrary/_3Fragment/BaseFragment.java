package com.yuan.devlibrary._3Fragment;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import com.yuan.devlibrary._12_______Utils.ResourceUtils;

public abstract class BaseFragment extends Fragment
{
    protected View mRootView = null;
    protected Activity mActivity = null;

    /***********************************Fragment初始化界面的部分***********************************/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mActivity = getActivity();
        if(mRootView   ==    null)
        {
            mRootView = ResourceUtils.generateView(mActivity,setLayoutResID());
            initWidgets(mRootView);
            initDatas();
            initLogic();
        }
        ViewGroup parentViewGroup = (ViewGroup)mRootView.getParent();
        if(parentViewGroup != null)
            parentViewGroup.removeView(mRootView);
        return mRootView;
    }

    /***********************************初始化布局文件所有控件的函数*******************************/
    protected abstract void initWidgets(View rootView);

    /**************************************设置布局文件的函数**************************************/
    protected abstract int setLayoutResID();

    /**********************************初始化Fragment界面数据的函数********************************/
    protected abstract void initDatas();

    /**********************************初始化Fragment界面逻辑的函数********************************/
    protected abstract void initLogic();

    /************************************Fragment销毁界面的部分************************************/
    public void onDetach()
    {
        mRootView = null;
        super.onDetach();
    }
}