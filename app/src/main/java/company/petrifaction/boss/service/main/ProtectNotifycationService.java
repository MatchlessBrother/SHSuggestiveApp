package company.petrifaction.boss.service.main;

import java.util.List;
import android.os.Build;
import android.os.IBinder;
import android.content.Intent;
import android.content.Context;
import io.reactivex.Observable;
import android.app.ActivityManager;
import io.reactivex.functions.Action;
import java.util.concurrent.TimeUnit;
import io.reactivex.functions.Consumer;
import io.reactivex.disposables.Disposable;
import com.xdandroid.hellodaemon.AbsWorkService;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProtectNotifycationService extends AbsWorkService
{
    public static Disposable sDisposable;
    public static boolean sShouldStopService;

    private void executeNotifiesService()
    {
        boolean isRunningForNotifies = isServiceRunning("company.petrifaction.boss.service.main.RefreshMsgService");
        if(!isRunningForNotifies)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                startForegroundService(new Intent(ProtectNotifycationService.this, RefreshMsgService.class));
            }
            else
            {
                startService(new Intent(ProtectNotifycationService.this, RefreshMsgService.class));
            }
        }
    }

    private void executeNotifiesListenerService()
    {
        boolean isRunningForNotifiesListener = isServiceRunning("company.petrifaction.boss.service.main.NotificationMonitorService");
        if(!isRunningForNotifiesListener)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                startForegroundService(new Intent(ProtectNotifycationService.this, NotificationMonitorService.class));
            }
            else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                startService(new Intent(ProtectNotifycationService.this, NotificationMonitorService.class));
            }
        }
    }

    private boolean isServiceRunning(String serviceName)
    {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        for(int index = 0;index <serviceList.size();index++)
        {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(index);
            if(serviceInfo.service.getClassName().toString().contains(serviceName))
                return true;
        }
        return false;
    }

    /*************************************************************/
    /*************************************************************/

    public static void stopService()
    {
        cancelJobAlarmSub();
        RefreshMsgService.isStop = true;
        NotificationMonitorService.isStop = true;
        sShouldStopService = true;
        if (sDisposable != null)
            sDisposable.dispose();
    }

    public IBinder onBind(Intent intent, Void v)
    {
        return null;

    }

    public void onServiceKilled(Intent rootIntent)
    {

    }

    public void startWork(Intent intent, int flags, int startId)
    {
        sDisposable = Observable.interval(3, TimeUnit.SECONDS).doOnDispose(new Action()
        {
            public void run() throws Exception
            {
                cancelJobAlarmSub();
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>()
        {
            public void accept(Long aLong) throws Exception
            {
                executeNotifiesService();
                executeNotifiesListenerService();
            }
        });
    }

    public void stopWork(Intent intent, int flags, int startId)
    {
        stopService();

    }

    public Boolean isWorkRunning(Intent intent, int flags, int startId)
    {
        return sDisposable != null && !sDisposable.isDisposed();
    }

    public Boolean shouldStopService(Intent intent, int flags, int startId)
    {
        return sShouldStopService;
    }
}