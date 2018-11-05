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
import android.app.Notification;
import android.app.PendingIntent;
import company.petrifaction.boss.R;
import android.graphics.BitmapFactory;
import android.app.NotificationManager;
import android.app.NotificationChannel;
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

public class RefreshMsg extends Service
{
    /*********************************************music********************************************//*
    private MediaPlayer mMediaPlayer;
    private Boolean mIsReadyPlayMusic;
    private WifiManager.WifiLock mWifiLock;*/
    /******************************************notifycation****************************************/
    private String mNotifyChannelNeedMusicId;
    private String mNotifyChannelNotNeedMusicId;
    private NotificationManager mNotificationManager;
    private NotificationChannel mNotificationChannel;
    private NotificationCompat.Builder mNotificationCompatBuilder;

    public void onCreate()
    {
        super.onCreate();
        /*mMediaPlayer = new MediaPlayer();
        AssetFileDescriptor descriptor = null;
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try
        {
            descriptor = getAssets().openFd("raw/EmergencyNews.mp3");
            mMediaPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
        }
        catch (IOException e) {e.printStackTrace();}
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            public void onPrepared(MediaPlayer mp)
            {
                mIsReadyPlayMusic = true;
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener()
        {
            public boolean onError(MediaPlayer mp, int what, int extra)
            {
                mIsReadyPlayMusic = false;
                return true;
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        });
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mWifiLock = ((WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE)).
                                         createWifiLock(WifiManager.WIFI_MODE_FULL, "music_lock");
        mWifiLock.acquire();*/
        /******************************************************************************************/
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyChannelNotNeedMusicId = "notneedmusic_emergencynews";
        mNotifyChannelNeedMusicId = "needmusic_emergencynews";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            // Not Need Music
            mNotificationChannel = new NotificationChannel(mNotifyChannelNotNeedMusicId,getString(R.string.emergencynews_notneedmusic),NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setShowBadge(true);
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setLightColor(Color.RED);
            mNotificationChannel.setSound(null,null);
            mNotificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            mNotificationChannel.setDescription(getString(R.string.emergencynewsdes_notneedmusic));
            mNotificationManager.createNotificationChannel(mNotificationChannel);
            //Need Music
            mNotificationChannel = new NotificationChannel(mNotifyChannelNeedMusicId,getString(R.string.emergencynews_needmusic),NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setShowBadge(true);
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setLightColor(Color.RED);
            mNotificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            mNotificationChannel.setDescription(getString(R.string.emergencynewsdes_needmusic));
            mNotificationChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.EmergencyNews), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            mNotificationManager.createNotificationChannel(mNotificationChannel);
        }
    }

    public void refreshMsg()
    {
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
                            mNotificationCompatBuilder.setChannelId(mNotifyChannelNeedMusicId);
                        }
                        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
                        Intent intent = new Intent(getApplicationContext(),MsgDetailAct.class);
                        intent.putExtra("msgid",null != refreshMsgBean.getId() ? refreshMsgBean.getId().trim() : "0");
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0, intent, flags);
                        /******************************************************************************************************************************************************/
                        Notification notification = mNotificationCompatBuilder .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)).
                                setContentTitle(getString(R.string.emergencynews)).setContentText(null != refreshMsgBean.getTextContent() ? refreshMsgBean.getTextContent().trim() : "").
                                setShowWhen(true).setWhen(System.currentTimeMillis()).setAutoCancel(true).
                                setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.EmergencyNews)).
                                setLights(0xffff0000,2000,1000).setVibrate(new long[]{1000,1000,1000,1000}).setContentIntent(pendingIntent).build();
                        mNotificationManager.notify(Integer.valueOf(refreshMsgBean.getId()),notification);
                        refreshMsgEnd(null != refreshMsgBean.getId() ? refreshMsgBean.getId().trim() : "0","2");
                        /*if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi"))
                        {
                            ShortcutBadger.applyNotification(getApplicationContext(),notification,1);
                        }
                        else
                        {
                            ShortcutBadger.applyCount(getApplicationContext(),1);
                        }*/
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
                            mNotificationCompatBuilder.setChannelId(mNotifyChannelNotNeedMusicId);
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