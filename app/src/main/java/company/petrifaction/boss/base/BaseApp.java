package company.petrifaction.boss.base;

import android.content.Context;
import me.jessyan.autosize.AutoSize;
import android.support.multidex.MultiDex;
import me.jessyan.autosize.unit.Subunits;
import me.jessyan.autosize.AutoSizeConfig;
import company.petrifaction.boss.bean.main.UserInfo;

import com.yuan.devlibrary._1App.BaseApplication;

public class BaseApp extends BaseApplication
{
    private BaseApp mBaseApp;
    private UserInfo mUserInfo;

    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void onCreate()
    {
        super.onCreate();
        mBaseApp  = this;
        adapterExternalUi();
        AutoSize.initCompatMultiProcess(this);
        AutoSizeConfig.getInstance().setLog(true).setBaseOnWidth(true).
                       setUseDeviceSize(false).setCustomFragment(true);
        AutoSizeConfig.getInstance().getUnitsManager().setSupportDP(false).
                        setSupportSP(true).setSupportSubunits(Subunits.MM);
    }

    public void adapterExternalUi()
    {
        //AutoSizeConfig.getInstance().getExternalAdaptManager().addExternalAdaptInfoOfActivity();
    }

    public UserInfo getUserInfos()
    {
        return mUserInfo;
    }

    public void setUserInfos(UserInfo userInfo)
    {
        mUserInfo = userInfo;
    }
}