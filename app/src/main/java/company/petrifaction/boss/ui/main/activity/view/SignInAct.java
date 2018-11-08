package company.petrifaction.boss.ui.main.activity.view;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.provider.Settings;
import android.view.WindowManager;
import com.gyf.barlibrary.BarHide;
import company.petrifaction.boss.R;
import android.content.ComponentName;
import android.content.pm.PackageInfo;
import com.gyf.barlibrary.ImmersionBar;
import android.content.pm.PackageManager;
import com.gyf.barlibrary.OnKeyboardListener;
import company.petrifaction.boss.base.BaseAct;
import android.support.v4.app.NotificationManagerCompat;
import com.yuan.devlibrary._12_______Utils.SharepreferenceUtils;
import company.petrifaction.boss.ui.main.activity.view_v.SignInAct_V;
import company.petrifaction.boss.ui.main.activity.presenter.SignInPresenter;

public class SignInAct extends BaseAct implements SignInAct_V,View.OnClickListener
{
    private Button mSigninLogin;
    private TextView mSigninVersion;
    private EditText mSigninAccount;
    private EditText mSigninPassword;
    private SignInPresenter mSignInPresenter;
    private static final String LOG_TAG = SignInAct.class.getSimpleName();

    protected int setLayoutResID()
    {
        return R.layout.activity_signin;

    }

    protected void initWidgets(View rootView)
    {
        super.initWidgets(rootView);
        openNotifycationEnable();
        openNotifycationListenerEnable();
        ImmersionBar.with(this).titleBar(R.id.signin_all).navigationBarColor(R.color.colorPrimary).navigationBarAlpha(0f)
                .hideBar(BarHide.FLAG_SHOW_BAR).navigationBarEnable(true).navigationBarWithKitkatEnable(true)
                .statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.white).fullScreen(false).keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE).setOnKeyboardListener(new OnKeyboardListener()
        {
            public void onKeyboardChange(boolean status,int keyboardHeight)
            {
                if(status)
                    Log.w(LOG_TAG, "SoftKeyBoard：Turn On！");
                else
                    Log.w(LOG_TAG, "SoftKeyBoard：Turn On！");
            }
        }).init();
        mSigninLogin = (Button)rootView.findViewById(R.id.signin_login);
        mSigninVersion = (TextView)rootView.findViewById(R.id.signin_version);
        mSigninAccount = (EditText)rootView.findViewById(R.id.signin_account);
        mSigninPassword = (EditText)rootView.findViewById(R.id.signin_password);
    }

    protected void initDatas()
    {
        mSignInPresenter = new SignInPresenter();
        bindBaseMvpPresenter(mSignInPresenter);
        mSigninAccount.setText(null != SharepreferenceUtils.extractObject(this,"username",String.class) && !"".equals(SharepreferenceUtils.extractObject(this,"username",String.class).trim()) ? SharepreferenceUtils.extractObject(this,"username",String.class).trim() : "");
    }

    protected void initLogic()
    {
        mSigninLogin.setOnClickListener(this);
        mSigninVersion.setText("Version : " + getAppVersionName());
        if(null != SharepreferenceUtils.extractObject(this,"password",String.class) && !"".equals(SharepreferenceUtils.extractObject(this,"password",String.class)) &&
                null != SharepreferenceUtils.extractObject(this,"username",String.class) && !"".equals(SharepreferenceUtils.extractObject(this,"username",String.class)))
        {
            Intent intent = new Intent(this,MainAct.class);
            intent.putExtra("islogined",false);
            startActivity(intent);
            finish();
        }
    }

    public void signInSuccess()
    {
        Intent intent = new Intent(this,MainAct.class);
        intent.putExtra("islogined",true);
        startActivity(intent);
        finish();
    }

    public void signInFailure()
    {

    }

    public void onClick(View view)
    {
        super.onClick(view);
        switch(view.getId())
        {
            case R.id.signin_login:mSignInPresenter.signIn(mSigninAccount.getText().toString().trim(),mSigninPassword.getText().toString().trim());break;
        }
    }

    public String getAppVersionName()
    {
        String versionName = "";
        try
        {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0)
            {
                return "";
            }
        }
        catch (Exception e)
        {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName.trim();
    }

    private void openNotifycationEnable()
    {
        if(!NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled())
        {
            Intent intent = new Intent();
            showToast("请选择通知选项并开启通知权限，否则无法接收应急消息！谢谢");
            Uri uri = Uri.fromParts("package",getPackageName(), null);
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    private void openNotifycationListenerEnable()
    {
        if(!isNotifycationListenerEnable())
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    private boolean isNotifycationListenerEnable()
    {
        String pkgName = getPackageName();
        final String flag = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flag))
        {
            final String[] names = flag.split(":");
            for (int index = 0; index < names.length; index++)
            {
                final ComponentName componentName = ComponentName.unflattenFromString(names[index]);
                if (componentName != null)
                {
                    if (TextUtils.equals(pkgName, componentName.getPackageName()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void quitCrrentAccount(BaseAct baseAct,String noteStr)
    {
        SharepreferenceUtils.storageObject(baseAct,"username",SharepreferenceUtils.extractObject(baseAct,"username",String.class));
        SharepreferenceUtils.storageObject(baseAct,"password","");
        Intent intent = new Intent(baseAct,SignInAct.class);
        baseAct.getBaseApp().finishAllActivity();
        baseAct.startActivity(intent);
        baseAct.showToast(noteStr);
    }
}