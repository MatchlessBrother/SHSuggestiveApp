package company.petrifaction.boss.ui.main.activity.view;

import android.net.Uri;
import android.view.View;
import java.util.ArrayList;
import android.text.TextUtils;
import android.content.Intent;
import com.hwangjr.rxbus.RxBus;
import android.widget.TextView;
import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import company.petrifaction.boss.R;
import android.content.ComponentName;
import android.app.NotificationManager;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.hwangjr.rxbus.annotation.Subscribe;
import android.support.v7.widget.RecyclerView;
import company.petrifaction.boss.base.BaseAct;
import company.petrifaction.boss.bean.main.MsgBean;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import android.support.v4.app.NotificationManagerCompat;
import company.petrifaction.boss.adapter.main.MsgAdapter;
import com.yuan.devlibrary._12_______Utils.SharepreferenceUtils;
import com.yuan.devlibrary._11___Widget.promptBox.BasePopupWindow;
import company.petrifaction.boss.ui.main.activity.view_v.MainAct_V;
import company.petrifaction.boss.ui.main.activity.view_v.SignInAct_V;
import company.petrifaction.boss.service.main.ProtectNotifycationService;
import company.petrifaction.boss.ui.main.activity.presenter.MainPresenter;
import company.petrifaction.boss.ui.main.activity.presenter.SignInPresenter;

public class MainAct extends BaseAct implements MainAct_V,SignInAct_V
{
    private MsgAdapter mMsgAdapter;
    private MainPresenter mMainPresenter;
    private SignInPresenter mSignInPresenter;
    private RecyclerView mMainactRecyclerview;
    private SwipeRefreshLayout mMainactSwiperefreshlayout;
    //public static final int StartMsgDetailAct = 0x0001;

    protected int setLayoutResID()
    {
        return R.layout.activity_main;
    }

    public View getRootView()
    {
        return mRootView;
    }

    @Subscribe
    public void refreshDatas(Boolean needRefresh)
    {
        mMainPresenter.refreshDatas();

    }

    protected void initWidgets(View rootView)
    {
        super.initWidgets(rootView);
        setTitleContent("应急消息");
        openNotifycationEnable();
        setTitleBack(R.mipmap.usericon);
        openNotifycationListenerEnable();
        mMainactRecyclerview = (RecyclerView) findViewById(R.id.mainact_recyclerview);
        mMainactSwiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.mainact_swiperefreshlayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMainactRecyclerview.setLayoutManager(linearLayoutManager);
        mMsgAdapter = new MsgAdapter(this,new ArrayList<MsgBean.ContentBean>());
        mMainactRecyclerview.setAdapter(mMsgAdapter);
        mMainactSwiperefreshlayout.setEnabled(true);
        mMsgAdapter.setEnableLoadMore(true);
        RxBus.get().register(this);
    }

    protected void onResume()
    {
        super.onResume();
        mMainPresenter.refreshDatas();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        RxBus.get().unregister(this);
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
        /*startService(new Intent(this, RefreshMsgService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            startService(new Intent(this, NotificationMonitorService.class));*/
        if(!getIntent().getBooleanExtra("islogined",false))
            mSignInPresenter.signIn(SharepreferenceUtils.extractObject(this,"username",String.class).trim(),SharepreferenceUtils.extractObject(this,"password",String.class).trim());
        else
        {
            DaemonEnv.initialize(this, ProtectNotifycationService.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
            ProtectNotifycationService.sShouldStopService = false;
            startService(new Intent(this,ProtectNotifycationService.class));
        }
        mMainPresenter.refreshDatas();
        mMainactSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            public void onRefresh()
            {
                mMainPresenter.refreshDatas();
            }
        });

        mMsgAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            public void onLoadMoreRequested()
            {
                mMainPresenter.loadMoreDatas();
            }
        },mMainactRecyclerview);

        mMsgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                Intent intent = new Intent(mActivity,MsgDetailAct.class);
                intent.putExtra("msgid",mMsgAdapter.getData().get(position).getId());
                startActivity(intent);
                //startActivityForResult(intent,StartMsgDetailAct);
            }
        });
    }

    public void signInSuccess()
    {
        DaemonEnv.initialize(this, ProtectNotifycationService.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        ProtectNotifycationService.sShouldStopService = false;
        startService(new Intent(this,ProtectNotifycationService.class));
    }

    public void signInFailure()
    {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        ProtectNotifycationService.stopService();
        SignInAct.quitCrrentAccount(this,"账号发生异常，请重新登录！");
    }

    public void finishRefresh()
    {
        mMainactSwiperefreshlayout.setRefreshing(false);

    }

    public void finishLoadMore()
    {
        mMsgAdapter.loadMoreComplete();

    }

    public void signOutAction()
    {
        mMainPresenter.signOut();

    }

    public void signOutSuccess()
    {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        ProtectNotifycationService.stopService();
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

    private void openNotifycationEnable()
    {
        if(!NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled())
        {
            Intent intent = new Intent();
            showToast("请选择通知选项并开启通知权限，否则无法接收应急消息！谢谢");
            Uri uri = Uri.fromParts("package",getPackageName(), null);
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    public void refreshDatas(MsgBean msgPageInfo)
    {
        mMsgAdapter.setNewData(msgPageInfo.getContent());
        if(msgPageInfo.getContent().size() < msgPageInfo.getPageSize())
            mMsgAdapter.setEnableLoadMore(false);
        else
            mMsgAdapter.setEnableLoadMore(true);
    }

    public void loadMoreDatas(MsgBean msgPageInfo)
    {
        mMsgAdapter.addData(msgPageInfo.getContent());
        mMsgAdapter.notifyDataSetChanged();
        if(msgPageInfo.getContent().size() < msgPageInfo.getPageSize())
            mMsgAdapter.setEnableLoadMore(false);
        else
            mMsgAdapter.setEnableLoadMore(true);
    }

    private void openNotifycationListenerEnable()
    {
        if(!isNotifycationListenerEnable())
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    private boolean isNotifycationListenerEnable()
    {
        String pkgName = getPackageName();
        final String flag = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flag))
        {
            final String[] names = flag.split(":");
            for (int index = 0; index < names.length; index++)
            {
                final ComponentName componentName = ComponentName.unflattenFromString(names[index]);
                if (componentName != null)
                {
                    if (TextUtils.equals(pkgName, componentName.getPackageName()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

   /* protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case StartMsgDetailAct:mMainPresenter.refreshDatas();break;
        }
    }*/
}