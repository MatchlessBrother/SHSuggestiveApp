package company.petrifaction.boss.ui.main.activity.model;

import android.content.Context;
import io.reactivex.schedulers.Schedulers;
import company.petrifaction.boss.network.NetClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import company.petrifaction.boss.ui.base.BaseMvp_PVModel;
import company.petrifaction.boss.ui.base.BaseMvp_NetObjCallBack;
import company.petrifaction.boss.ui.base.BaseMvp_LocalObjCallBack;

public class MainModel extends BaseMvp_PVModel
{
    public static final int SignOut = 0x0001;
    public static final int GetMsg = 0x0002;

    public void executeOfNet(Context context, int netRequestCode, BaseMvp_LocalObjCallBack localCallBack)
    {
        localCallBack.onStart();
        switch(netRequestCode)
        {
            case SignOut:NetClient.getInstance(context).getNetUrl().signOut().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseMvp_NetObjCallBack(context,localCallBack));break;
            case GetMsg:NetClient.getInstance(context).getNetUrl().getMsg(getMultipartForms()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseMvp_NetObjCallBack(context,localCallBack));break;
        }
    }

    public void executeOfLocal(Context context, int localRequestCode, BaseMvp_LocalObjCallBack localCallBack)
    {
        localCallBack.onStart();
        localCallBack.onFinish();
    }
}