package company.petrifaction.boss.service.main;

import android.net.Uri;
import android.os.Build;
import android.graphics.Color;
import android.content.Context;
import android.app.Notification;
import android.provider.Settings;
import company.petrifaction.boss.R;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import me.leolin.shortcutbadger.ShortcutBadger;
import android.support.v4.app.NotificationCompat;
import android.service.notification.StatusBarNotification;
import android.service.notification.NotificationListenerService;

@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService
{
    public static boolean isStop;
    private int mEffectiveNumberOfNotices = 0;
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            android.app.NotificationChannel mNotificationChannel = null;
            /*************************************************************************Not Need Music******************************************************************/
            mNotificationChannel = new android.app.NotificationChannel(mNotifyChannelNotNeedMusicId,getString(R.string.emergencynews_notneedmusic),NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setShowBadge(true);
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setLightColor(Color.RED);
            mNotificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI,Notification.AUDIO_ATTRIBUTES_DEFAULT);
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
            Notification notification = new Notification.Builder(this,mNotifyChannelNotNeedMusicId).build();
            startForeground(Integer.MAX_VALUE / 2 + 1,notification);
        }
    }

    public void onNotificationPosted(StatusBarNotification sbn)
    {
        if(!isStop)
        {
            mEffectiveNumberOfNotices = 0;
            StatusBarNotification[]  statusBarNotifications = getActiveNotifications();
            for(StatusBarNotification statusBarNotification : statusBarNotifications)
            {
                if(statusBarNotification.getPackageName().toLowerCase().trim().contains(getPackageName().toLowerCase().trim()) && null != statusBarNotification.getNotification().extras.getString(Notification.EXTRA_TITLE) && statusBarNotification.getNotification().extras.getString(Notification.EXTRA_TITLE).contains(getString(R.string.emergencynews)))
                {
                    mEffectiveNumberOfNotices++;
                }
            }
            if(Build.MANUFACTURER.equalsIgnoreCase("Xiaomi"))
                ShortcutBadger.applyNotification(getApplicationContext(),sbn.getNotification(), mEffectiveNumberOfNotices);
            else
                ShortcutBadger.applyCount(getApplicationContext(), mEffectiveNumberOfNotices);
        }
        else
        {
            if(Build.MANUFACTURER.equalsIgnoreCase("Xiaomi"))
                ShortcutBadger.applyNotification(getApplicationContext(),sbn.getNotification(), 0);
            else
                ShortcutBadger.applyCount(getApplicationContext(), 0);
            stopSelf();
        }
    }

    public void onNotificationRemoved(StatusBarNotification sbn)
    {
        if(!isStop)
        {
            mEffectiveNumberOfNotices = 0;
            StatusBarNotification[]  statusBarNotifications = getActiveNotifications();
            for(StatusBarNotification statusBarNotification : statusBarNotifications)
            {
                if(statusBarNotification.getPackageName().toLowerCase().trim().contains(getPackageName().toLowerCase().trim()) && null != statusBarNotification.getNotification().extras.getString(Notification.EXTRA_TITLE) && statusBarNotification.getNotification().extras.getString(Notification.EXTRA_TITLE).contains(getString(R.string.emergencynews)))
                {
                    mEffectiveNumberOfNotices++;
                }
            }
            if(Build.MANUFACTURER.equalsIgnoreCase("Xiaomi"))
                ShortcutBadger.applyNotification(getApplicationContext(),sbn.getNotification(), mEffectiveNumberOfNotices);
            else
                ShortcutBadger.applyCount(getApplicationContext(), mEffectiveNumberOfNotices);
        }
        else
        {
            if(Build.MANUFACTURER.equalsIgnoreCase("Xiaomi"))
                ShortcutBadger.applyNotification(getApplicationContext(),sbn.getNotification(), 0);
            else
                ShortcutBadger.applyCount(getApplicationContext(), 0);
            stopSelf();
        }
    }
}