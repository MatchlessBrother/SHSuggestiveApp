package company.petrifaction.boss.service.main;

import android.os.Build;
import android.annotation.SuppressLint;
import me.leolin.shortcutbadger.ShortcutBadger;
import android.service.notification.StatusBarNotification;
import android.service.notification.NotificationListenerService;

@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService
{
    private int mEffectiveNumberOfNotices = 0;
    public void onNotificationPosted(StatusBarNotification sbn)
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

    public void onNotificationRemoved(StatusBarNotification sbn)
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
}