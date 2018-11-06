package company.petrifaction.boss.service.main;

import java.util.List;
import android.net.Uri;
import java.util.Arrays;
import android.os.Build;
import android.os.IBinder;
import android.app.Service;
import android.graphics.Color;
import android.content.Intent;
import io.reactivex.Observable;
import android.content.Context;
import com.hwangjr.rxbus.RxBus;
import android.app.Notification;
import android.app.PendingIntent;
import company.petrifaction.boss.R;
import android.graphics.BitmapFactory;
import android.app.NotificationManager;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import android.support.v4.app.NotificationCompat;
import company.petrifaction.boss.bean.BaseReturnData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import company.petrifaction.boss.bean.BaseReturnListData;
import company.petrifaction.boss.bean.main.RefreshMsgBean;
import company.petrifaction.boss.ui.base.BaseMvp_Presenter;
import company.petrifaction.boss.ui.base.BaseMvp_EntranceOfModel;
import company.petrifaction.boss.ui.base.BaseMvp_LocalObjCallBack;
import company.petrifaction.boss.ui.base.BaseMvp_LocalListCallBack;
import company.petrifaction.boss.ui.main.activity.view.MsgDetailAct;
import company.petrifaction.boss.ui.main.activity.model.RefresgMsgModel;

public class RefreshMsgService extends Service
{
    public static boolean isStop;
    private boolean mIsChangeMsgStatus;
    private String mNotifyChannelNeedMusicId;
    private String mNotifyChannelNotNeedMusicId;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationCompatBuilder;

    public void onCreate()
    {
        super.onCreate();
        isStop = false;
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyChannelNotNeedMusicId = "notneedmusic_emergencynews";
        mNotifyChannelNeedMusicId = "needmusic_emergencynews";
        mIsChangeMsgStatus = false;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            android.app.NotificationChannel mNotificationChannel = null;
            /*************************************************************************Not Need Music******************************************************************/
            mNotificationChannel = new android.app.NotificationChannel(mNotifyChannelNotNeedMusicId,getString(R.string.emergencynews_notneedmusic),NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setShowBadge(true);
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setLightColor(Color.RED);
            mNotificationChannel.setSound(null,null);
            mNotificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            mNotificationChannel.setDescription(getString(R.string.emergencynewsdes_notneedmusic));
            mNotificationManager.createNotificationChannel(mNotificationChannel);
            /*************************************************************************Need Music******************************************************************/
            mNotificationChannel = new android.app.NotificationChannel(mNotifyChannelNeedMusicId,getString(R.string.emergencynews_needmusic),NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setShowBadge(true);
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setLightColor(Color.RED);
            mNotificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            mNotificationChannel.setDescription(getString(R.string.emergencynewsdes_needmusic));
            mNotificationChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.emergencynews), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            mNotificationManager.createNotificationChannel(mNotificationChannel);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Notification notification = new Notification.Builder(this,Integer.MAX_VALUE/2 + "").build();
            startForeground(Integer.MAX_VALUE/2,notification);
        }
    }

    public void refreshMsg()
    {
        if(!isStop)
        {
            mIsChangeMsgStatus = false;
            BaseMvp_EntranceOfModel.requestDatas(RefresgMsgModel.class).executeOfNet(this,RefresgMsgModel.RefresgMsg,new BaseMvp_LocalListCallBack<BaseReturnListData<RefreshMsgBean>>(new BaseMvp_Presenter())
            {
                public void onSuccess(BaseReturnListData<RefreshMsgBean> refreshMsgBeans)
                {
                    List<RefreshMsgBean> refreshMsgBeanList = Arrays.asList(refreshMsgBeans.getData());
                    for(RefreshMsgBean refreshMsgBean : refreshMsgBeanList)
                    {
                        if(refreshMsgBean.getAppPlayStatus() == 1)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                            {
                                mNotificationCompatBuilder = new NotificationCompat.Builder(getApplicationContext(),mNotifyChannelNeedMusicId);
                                mNotificationCompatBuilder.setChannelId(mNotifyChannelNeedMusicId);
                            }
                            else
                            {
                                mNotificationCompatBuilder = new NotificationCompat.Builder(getApplicationContext());
                            }
                            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
                            Intent intent = new Intent(getApplicationContext(),MsgDetailAct.class);
                            intent.putExtra("msgid",null != refreshMsgBean.getId() ? refreshMsgBean.getId().trim() : "0");
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0, intent, flags);
                            /******************************************************************************************************************************************************/
                            Notification notification = mNotificationCompatBuilder .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)).
                                    setContentTitle(getString(R.string.emergencynews)).setContentText(null != refreshMsgBean.getTextContent() ? refreshMsgBean.getTextContent().trim() : "").
                                    setShowWhen(true).setWhen(System.currentTimeMillis()).setAutoCancel(true).
                                    setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.emergencynews)).
                                    setLights(0xffff0000,2000,1000).setVibrate(new long[]{1000,1000,1000,1000}).setContentIntent(pendingIntent).build();
                            mNotificationManager.notify(Integer.valueOf(refreshMsgBean.getId()),notification);
                            refreshMsgEnd(null != refreshMsgBean.getId() ? refreshMsgBean.getId().trim() : "0","2");
                            mIsChangeMsgStatus = true;
                        }
                        else if(refreshMsgBean.getAppNotifyStatus() == 1)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                            {
                                mNotificationCompatBuilder = new NotificationCompat.Builder(getApplicationContext(),mNotifyChannelNotNeedMusicId);
                                mNotificationCompatBuilder.setChannelId(mNotifyChannelNotNeedMusicId);
                            }
                            else
                            {
                                mNotificationCompatBuilder = new NotificationCompat.Builder(getApplicationContext());
                            }
                            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
                            Intent intent = new Intent(getApplicationContext(),MsgDetailAct.class);
                            intent.putExtra("msgid",null != refreshMsgBean.getId() ? refreshMsgBean.getId().trim() : "0");
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0, intent, flags);
                            /******************************************************************************************************************************************************/
                            Notification notification = mNotificationCompatBuilder .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)).
                                    setContentTitle(getString(R.string.emergencynews)).setContentText(null != refreshMsgBean.getTextContent() ? refreshMsgBean.getTextContent().trim() : "").
                                    setShowWhen(true).setWhen(System.currentTimeMillis()).setAutoCancel(true).setSound(null).
                                    setLights(0xffff0000,2000,1000).setVibrate(new long[]{1000,1000,1000,1000}).setContentIntent(pendingIntent).build();
                            mNotificationManager.notify(Integer.valueOf(refreshMsgBean.getId()),notification);
                            refreshMsgEnd(null != refreshMsgBean.getId() ? refreshMsgBean.getId().trim() : "0","1");
                            mIsChangeMsgStatus = true;
                        }
                        if(refreshMsgBean == refreshMsgBeanList.get(refreshMsgBeanList.size() - 1) && mIsChangeMsgStatus)
                        {
                            RxBus.get().post(Boolean.valueOf(true));
                        }
                    } tryOnRefreshMsg();
                }

                public void onFailure(String msg)
                {
                    tryOnRefreshMsg();
                }

                public void onError(String msg)
                {
                    tryOnRefreshMsg();
                }
            });
        }
        else
        {
            stopSelf();
        }
    }

    public void tryOnRefreshMsg()
    {
        Observable.just("tryOn").map(new Function<String, String>()
        {
            public String apply(String s) throws Exception
            {
                Thread.sleep(2000);
                return "ok";
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>()
        {
            public void accept(String s) throws Exception
            {
                refreshMsg();
            }
        });
    }

    public IBinder onBind(Intent intent)
    {
        return null;

    }

    public void refreshMsgEnd(String msgId,String notifyCode)
    {
        BaseMvp_EntranceOfModel.requestDatas(RefresgMsgModel.class).putForm("notifyId ",msgId).putForm("type",notifyCode).convertForms().
        executeOfNet(this,RefresgMsgModel.RefresgMsgEnd,new BaseMvp_LocalObjCallBack<BaseReturnData>(new BaseMvp_Presenter())
        {
            public void onSuccess(BaseReturnData baseReturnData)
            {

            }
            public void onFailure(String msg)
            {

            }

            public void onError(String msg)
            {

            }
        });
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        refreshMsg();
        return super.onStartCommand(intent, flags, startId);
    }
}