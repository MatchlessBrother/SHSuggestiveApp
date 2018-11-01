package com.yuan.devlibrary._12_______Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**判定安装包的BuildVariants值*/
public class BuildVariantsUtils
{
    /**判定当前运行的软件是Debug模式还是Release模式,true为Debug,false为Release**/
    public static boolean isDebug(Context context)
    {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if(null != applicationInfo)
            return (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return false;
    }
}