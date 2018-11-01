package company.petrifaction.boss.ui.main.activity.view;

import android.view.View;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.TextView;
import android.view.LayoutInflater;
import company.petrifaction.boss.R;
import android.support.v7.widget.RecyclerView;
import company.petrifaction.boss.base.BaseAct;
import company.petrifaction.boss.bean.main.MsgBean;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import company.petrifaction.boss.adapter.main.MsgAdapter;
import com.yuan.devlibrary._12_______Utils.SharepreferenceUtils;
import com.yuan.devlibrary._11___Widget.promptBox.BasePopupWindow;
import company.petrifaction.boss.ui.main.activity.view_v.MainAct_V;
import company.petrifaction.boss.ui.main.activity.view_v.SignInAct_V;
import company.petrifaction.boss.ui.main.activity.presenter.MainPresenter;
import company.petrifaction.boss.ui.main.activity.presenter.SignInPresenter;

public class MainAct extends BaseAct implements MainAct_V,SignInAct_V
{
    private MsgAdapter mMsgAdapter;
    private MainPresenter mMainPresenter;
    private SignInPresenter mSignInPresenter;
    private RecyclerView mMainactRecyclerview;
    private SwipeRefreshLayout mMainactSwiperefreshlayout;

    protected int setLayoutResID()
    {
        return R.layout.activity_main;
    }

    public View getRootView()
    {
        return mRootView;
    }

    protected void initWidgets(View rootView)
    {
        super.initWidgets(rootView);
        setTitleContent("应急消息");
        setTitleBack(R.mipmap.usericon);
        mMainactRecyclerview = (RecyclerView) findViewById(R.id.mainact_recyclerview);
        mMainactSwiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.mainact_swiperefreshlayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMainactRecyclerview.setLayoutManager(linearLayoutManager);
        mMsgAdapter = new MsgAdapter(this,new ArrayList<MsgBean>());
        mMainactRecyclerview.setAdapter(mMsgAdapter);
        mMainactSwiperefreshlayout.setEnabled(true);
        mMsgAdapter.setEnableLoadMore(true);
    }

    protected void initDatas()
    {
        mMainPresenter = new MainPresenter();
        mSignInPresenter = new SignInPresenter();
        bindBaseMvpPresenter(mMainPresenter);
        bindBaseMvpPresenter(mSignInPresenter);
    }

    protected void initLogic()
    {
        if(!getIntent().getBooleanExtra("islogined",false))
            mSignInPresenter.signIn(SharepreferenceUtils.extractObject(this,"username",String.class).trim(),SharepreferenceUtils.extractObject(this,"password",String.class).trim());
    }

    public void signInSuccess()
    {

    }

    public void signInFailure()
    {
        SignInAct.quitCrrentAccount(this,"账号发生异常，请重新登录！");

    }

    public void signOutAction()
    {
        mMainPresenter.signOut();

    }

    public void signOutSuccess()
    {
        SignInAct.quitCrrentAccount((BaseAct)mActivity,"退出登录成功！");

    }

    public void signOutFailure()
    {

    }

    protected void onTitleBackClick()
    {
        final View basePopupWindowContent = LayoutInflater.from(mActivity).inflate(R.layout.dialog_signin_exit,null);
        TextView signInBtn =(TextView)basePopupWindowContent.findViewById(R.id.dialogsigninexit_signin);
        TextView exitBtn =(TextView)basePopupWindowContent.findViewById(R.id.dialogsigninexit_exit);
        final BasePopupWindow basePopupWindow  =  new BasePopupWindow(mActivity);
        basePopupWindow.setContentView(basePopupWindowContent);
        signInBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(mActivity,ModifyPasswordAct.class);
                if(basePopupWindow.isShowing()) basePopupWindow.dismiss();
                startActivity(intent);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(basePopupWindow.isShowing()) basePopupWindow.dismiss();
                ((MainAct)mActivity).signOutAction();
            }
        });
        if(isUseDefaultTitleLine())
            basePopupWindow.showAsDropDown(mTitleBackBtn,12,6);
    }

    @Override
    public void getFailOfMsg() {

    }

    @Override
    public void getSuccessOfMsg(MsgBean msgBean) {

    }
}