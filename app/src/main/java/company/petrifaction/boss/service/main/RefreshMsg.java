package company.petrifaction.boss.service.main;

import android.os.IBinder;
import java.io.IOException;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.PowerManager;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.content.res.AssetFileDescriptor;
import company.petrifaction.boss.bean.BaseReturnData;
import company.petrifaction.boss.bean.BaseReturnListData;
import company.petrifaction.boss.bean.main.RefreshMsgBean;
import company.petrifaction.boss.ui.base.BaseMvp_EntranceOfModel;
import company.petrifaction.boss.ui.base.BaseMvp_LocalListCallBack;
import company.petrifaction.boss.ui.base.BaseMvp_LocalObjCallBack;
import company.petrifaction.boss.ui.main.activity.model.RefresgMsgModel;

public class RefreshMsg extends Service
{
    /*********************************************music********************************************/
    private MediaPlayer mMediaPlayer;
    private Boolean mIsReadyPlayMusic;
    private WifiManager.WifiLock mWifiLock;
    /******************************************notifycation****************************************/

    public void onCreate()
    {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        AssetFileDescriptor descriptor = null;
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try
        {
            descriptor = getAssets().openFd("EmergencyNews.mp3");
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
        mWifiLock.acquire();
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }


    public void refreshMsg()
    {
        BaseMvp_EntranceOfModel.requestDatas(RefresgMsgModel.class).executeOfNet(this,RefresgMsgModel.RefresgMsg,new BaseMvp_LocalListCallBack<BaseReturnListData<RefreshMsgBean>>(this)
        {
            public void onSuccess(BaseReturnListData<RefreshMsgBean> refreshMsgBeans)
            {
                //根据每项数据的配置播放语音和弹出提示框
            }

            public void onFailure(String msg)
            {}

            public void onError(String msg)
            {}
        });
    }

    public void refreshMsgEnd(String msgId)
    {
        BaseMvp_EntranceOfModel.requestDatas(RefresgMsgModel.class).putForm("notifyId ",msgId).convertForms().
        executeOfNet(this,RefresgMsgModel.RefresgMsgEnd,new BaseMvp_LocalObjCallBack<BaseReturnData>(this)
        {
            public void onSuccess(BaseReturnData baseReturnData)
            {
                //根据每项数据的配置播放语音和弹出提示框后提醒服务器操作成功
            }

            public void onFailure(String msg)
            {}

            public void onError(String msg)
            {}
        });
    }
}