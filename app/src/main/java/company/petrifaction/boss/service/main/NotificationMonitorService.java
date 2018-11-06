package company.petrifaction.boss.service.main;

import android.os.Build;
import android.app.Notification;
import android.annotation.SuppressLint;
import me.leolin.shortcutbadger.ShortcutBadger;
import android.service.notification.StatusBarNotification;
import android.service.notification.NotificationListenerService;

@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService
{
    public static boolean isStop;
    private int mEffectiveNumberOfNotices = 0;
    public void onCreate()
    {
        super.onCreate();
        isStop = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Notification notification = new Notification.Builder(this,String.valueOf(Integer.MAX_VALUE / 2 + 1)).build();
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
                if(statusBarNotification.getPackageName().toLowerCase().trim().contains(getPackageName().toLowerCase().trim()))
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
                if(statusBarNotification.getPackageName().toLowerCase().trim().contains(getPackageName().toLowerCase().trim()))
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