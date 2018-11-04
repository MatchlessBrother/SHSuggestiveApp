package company.petrifaction.boss.ui.main.activity.model;

import android.content.Context;
import io.reactivex.schedulers.Schedulers;
import company.petrifaction.boss.network.NetClient;
import company.petrifaction.boss.ui.base.BaseMvp_PVModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import company.petrifaction.boss.ui.base.BaseMvp_NetObjCallBack;
import company.petrifaction.boss.ui.base.BaseMvp_NetListCallBack;
import company.petrifaction.boss.ui.base.BaseMvp_LocalObjCallBack;
import company.petrifaction.boss.ui.base.BaseMvp_LocalListCallBack;

public class RefresgMsgModel extends BaseMvp_PVModel
{
    public static final int RefresgMsg = 0x0001;
    public static final int RefresgMsgEnd = 0x0002;

    public void executeOfNet(Context context, int netRequestCode, BaseMvp_LocalObjCallBack localCallBack)
    {
        switch(netRequestCode)
        {
            case RefresgMsgEnd:NetClient.getInstance(context).getNetUrl().refreshMsgEnd(getMultipartForms()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseMvp_NetObjCallBack(context,localCallBack));break;
        }
    }

    public void executeOfNet(Context context, int netRequestCode, BaseMvp_LocalListCallBack localCallBack)
    {
        switch(netRequestCode)
        {
            case RefresgMsg:NetClient.getInstance(context).getNetUrl().refreshMsg().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseMvp_NetListCallBack(context,localCallBack));break;
        }
    }

    public void executeOfLocal(Context context, int localRequestCode, BaseMvp_LocalObjCallBack localCallBack)
    {
        localCallBack.onStart();
        localCallBack.onFinish();
    }
}