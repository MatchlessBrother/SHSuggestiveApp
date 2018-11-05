package company.petrifaction.boss.ui.main.activity.view;

import java.io.File;
import java.util.Map;
import android.net.Uri;
import android.os.Build;
import java.util.HashMap;
import android.view.View;
import java.io.IOException;
import java.util.ArrayList;
import android.os.StrictMode;
import android.text.TextUtils;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Context;
import android.provider.Settings;
import android.media.MediaPlayer;
import android.media.AudioManager;
import java.text.SimpleDateFormat;
import android.widget.LinearLayout;
import company.petrifaction.boss.R;
import android.net.wifi.WifiManager;
import android.widget.CompoundButton;
import android.content.ComponentName;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.DownloadTask;
import android.support.v7.widget.RecyclerView;
import company.petrifaction.boss.base.BaseAct;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v4.widget.SwipeRefreshLayout;
import com.liulishuo.okdownload.core.cause.EndCause;
import android.support.v7.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuan.devlibrary._12_______Utils.MemoryUtils;
import company.petrifaction.boss.bean.main.MsgDetailBean;
import com.liulishuo.okdownload.core.listener.DownloadListener2;
import company.petrifaction.boss.adapter.main.MsgDetailImgsAdapter;
import company.petrifaction.boss.adapter.main.MsgDetailFilesAdapter;
import company.petrifaction.boss.ui.main.activity.view_v.MsgDetailAct_V;
import company.petrifaction.boss.ui.main.activity.presenter.MsgDetailPresenter;

public class MsgDetailAct extends BaseAct implements MsgDetailAct_V
{
    private String mMsgId;
    private String mBaseImgPath;
    private TextView mMsgdetailTime;
    private TextView mMsgdetailContent;
    private LinearLayout mMsgdetailMusicall;
    private CheckBox mMsgdetailMusic;
    private TextView mMsgdetailYamc;
    private TextView mMsgdetailYadz;
    private TextView mMsgdetailZzxclxdh;
    private TextView mMsgdetailFzrjdh;
    private TextView mMsgdetailJslxrdh;
    private TextView mMsgdetailSblxrdh;
    private TextView mMsgdetailWhp;
    private RecyclerView mMsgdetailCzzp;
    private RecyclerView mMsgdetailYawj;
    private SimpleDateFormat mSimpleDateFormat;
    private MsgDetailPresenter mMsgDetailPresenter;
    private MsgDetailImgsAdapter mMsgDetailImgsAdapter;
    private NestedScrollView mMsgdetailNestedscrollview;
    private MsgDetailFilesAdapter mMsgDetailFilesAdapter;
    private SwipeRefreshLayout mMsgdetailSwiperefreshlayout;
    /**********************播放语音模块********************/
    private String mMusicDataSource;
    private MediaPlayer mMediaPlayer;
    private Boolean mIsReadyPlayMusic;
    private WifiManager.WifiLock mWifiLock;
    /**********************下载文件模块**********************/
    private String mDownloadPath;
    private DownloadListener2 mDownloadListener;
    private Map<String,DownloadTask> mDownloadTaskMap;

    protected int setLayoutResID()
    {
        return R.layout.activity_msgdetail;
    }

    protected void initWidgets(View rootView)
    {
        super.initWidgets(rootView);
        setTitleContent("消息详情");
        openNotifycationListenerEnable();
        mMsgId = getIntent().getStringExtra("msgid");
        mBaseImgPath = "http://git.yunfanwulian.com:20001";
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mMsgdetailSwiperefreshlayout = (SwipeRefreshLayout)rootView.findViewById(R.id.msgdetail_swiperefreshlayout);
        mMsgdetailNestedscrollview = (NestedScrollView)rootView.findViewById(R.id.msgdetail_nestedscrollview);
        mMsgdetailTime = (TextView)rootView.findViewById(R.id.msgdetail_time);
        mMsgdetailContent = (TextView)rootView.findViewById(R.id.msgdetail_content);
        mMsgdetailMusicall = (LinearLayout)rootView.findViewById(R.id.msgdetail_musicall);
        mMsgdetailMusic = (CheckBox)rootView.findViewById(R.id.msgdetail_music);
        mMsgdetailYamc = (TextView)rootView.findViewById(R.id.msgdetail_yamc);
        mMsgdetailYadz = (TextView)rootView.findViewById(R.id.msgdetail_yadz);
        mMsgdetailZzxclxdh = (TextView)rootView.findViewById(R.id.msgdetail_zzxclxdh);
        mMsgdetailFzrjdh = (TextView)rootView.findViewById(R.id.msgdetail_fzrjdh);
        mMsgdetailJslxrdh = (TextView)rootView.findViewById(R.id.msgdetail_jslxrdh);
        mMsgdetailSblxrdh = (TextView)rootView.findViewById(R.id.msgdetail_sblxrdh);
        mMsgdetailWhp = (TextView)rootView.findViewById(R.id.msgdetail_whp);
        mMsgdetailCzzp = (RecyclerView)rootView.findViewById(R.id.msgdetail_czzp);
        mMsgdetailYawj = (RecyclerView)rootView.findViewById(R.id.msgdetail_yawj);
        /******************************************************************************************/
        mMsgDetailImgsAdapter = new MsgDetailImgsAdapter(this,new ArrayList<String>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mMsgdetailCzzp.setLayoutManager(gridLayoutManager);
        mMsgdetailCzzp.setAdapter(mMsgDetailImgsAdapter);
        /******************************************************************************************/
        mMsgDetailFilesAdapter = new MsgDetailFilesAdapter(this,new ArrayList<String>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMsgdetailYawj.setLayoutManager(linearLayoutManager);
        mMsgdetailYawj.setAdapter(mMsgDetailFilesAdapter);
        /******************************************************************************************/
        mIsReadyPlayMusic = false;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {public void onPrepared(MediaPlayer mp) {mIsReadyPlayMusic = true;}});
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener()
        {
            public boolean onError(MediaPlayer mp, int what, int extra)
            {
                mIsReadyPlayMusic=false;
                return true;
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
                mMsgdetailMusic.setChecked(false);
            }
        });
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mWifiLock = ((WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE)).
                                         createWifiLock(WifiManager.WIFI_MODE_FULL, "music_lock");
        mWifiLock.acquire();
        /******************************************************************************************/
        mDownloadPath = MemoryUtils.getBestFilesPath(this);
        mDownloadTaskMap = new HashMap<String,DownloadTask>();
        mDownloadListener = new DownloadListener2()
        {
            public void taskStart(@NonNull DownloadTask task)
            {
                showToast("开始下载：" + task.getFilename() + "文件，请稍后操作...");
            }

            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause)
            {
                showToast( "亲，" + task.getFilename() + "下载完毕，可以打开查看了哟！");
            }
        };
        /******************************************************************************************/
        mMsgdetailCzzp.setNestedScrollingEnabled(false);
        mMsgdetailCzzp.setFocusableInTouchMode(false);
        mMsgdetailYawj.setNestedScrollingEnabled(false);
        mMsgdetailYawj.setFocusableInTouchMode(false);
        mMsgdetailSwiperefreshlayout.setEnabled(true);
    }

    protected void initDatas()
    {
        mMsgDetailPresenter = new MsgDetailPresenter();
        bindBaseMvpPresenter(mMsgDetailPresenter);
        mMsgdetailSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            public void onRefresh()
            {
                mMsgDetailPresenter.getMsgOfDetailDatas(mMsgId);
            }
        });

        mMsgDetailImgsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                Intent intent = new Intent(MsgDetailAct.this,PreviewPhotoAct.class);
                intent.putExtra("imgPath",mBaseImgPath + mMsgDetailImgsAdapter.getData().get(position).trim());
                startActivity(intent);
            }
        });

        mMsgDetailFilesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                if(mMsgDetailFilesAdapter.getData().get(position).substring(mMsgDetailFilesAdapter.getData().get(position).lastIndexOf("."),mMsgDetailFilesAdapter.getData().get(position).length()).contains("png") ||
                   mMsgDetailFilesAdapter.getData().get(position).substring(mMsgDetailFilesAdapter.getData().get(position).lastIndexOf("."),mMsgDetailFilesAdapter.getData().get(position).length()).contains("jpg") ||
                   mMsgDetailFilesAdapter.getData().get(position).substring(mMsgDetailFilesAdapter.getData().get(position).lastIndexOf("."),mMsgDetailFilesAdapter.getData().get(position).length()).contains("jpeg"))
                {
                    Intent intent = new Intent(MsgDetailAct.this,PreviewPhotoAct.class);
                    intent.putExtra("imgPath",mBaseImgPath + mMsgDetailFilesAdapter.getData().get(position).trim());
                    startActivity(intent);
                }
                else
                {
                    String fileName = null;
                    String fileIntegrityPath = null;
                    DownloadTask downloadTask = null;
                    if(mDownloadTaskMap.containsKey(mMsgDetailFilesAdapter.getData().get(position).trim()))
                    {
                        downloadTask = mDownloadTaskMap.get(mMsgDetailFilesAdapter.getData().get(position).trim());
                        fileName = downloadTask.getFilename();
                        fileIntegrityPath = mDownloadPath + File.separator + fileName;
                    }
                    else
                    {
                        fileName = mMsgDetailFilesAdapter.getData().get(position).trim().substring(
                        mMsgDetailFilesAdapter.getData().get(position).trim().lastIndexOf("/") + 1,mMsgDetailFilesAdapter.getData().get(position).trim().length());
                        downloadTask = new DownloadTask.Builder(mBaseImgPath + mMsgDetailFilesAdapter.getData().get(position).trim(),mDownloadPath,fileName).
                                                                            setMinIntervalMillisCallbackProcess(30).setPassIfAlreadyCompleted(true).build();
                        mDownloadTaskMap.put(mMsgDetailFilesAdapter.getData().get(position).trim(),downloadTask);
                        fileIntegrityPath = mDownloadPath + File.separator + fileName;
                    }
                    StatusUtil.Status downloadStatus = StatusUtil.getStatus(downloadTask);
                    if(downloadStatus == StatusUtil.Status.PENDING)
                        showToast("当前文件正在等待下载，请稍等...");
                    else if(downloadStatus == StatusUtil.Status.RUNNING)
                        showToast("正在下载当前文件，请稍等...");
                    else if(downloadStatus == StatusUtil.Status.COMPLETED)
                        openFile(fileIntegrityPath,fileIntegrityPath.trim().substring(fileIntegrityPath.trim().lastIndexOf(".") + 1,fileIntegrityPath.trim().length()));
                    else if(downloadStatus ==  StatusUtil.Status.UNKNOWN)
                        downloadTask.enqueue(mDownloadListener);
                }
            }
        });
    }

    protected void initLogic()
    {
        mMsgDetailPresenter.getMsgOfDetailDatas(mMsgId);
        mMsgdetailMusicall.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mMsgdetailMusic.setChecked(!mMsgdetailMusic.isChecked());
            }
        });

        mMsgdetailMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    if(mIsReadyPlayMusic)
                        mMediaPlayer.start();
                    else
                        mMsgdetailMusic.setChecked(false);
                }
                else
                {
                    if(mIsReadyPlayMusic && mMediaPlayer.isPlaying())
                    {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                }
            }
        });
    }

    protected void onDestroy()
    {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mWifiLock.release();
        mMediaPlayer = null;
        mWifiLock = null;
    }

    public void failOfGetDatas()
    {
        mMsgdetailSwiperefreshlayout.setRefreshing(false);

    }

    public void setMediaPlayResources()
    {
        if(null != mMusicDataSource && !"".equals(mMusicDataSource.trim()) && !mBaseImgPath.equals(mMusicDataSource.trim()))
        {
            try
            {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(mMusicDataSource);
                mMediaPlayer.prepareAsync();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("当前应急消息没有可播放的语音文件哟！");
        }
    }

    public String getFileType(String type)
    {
        switch(type.toLowerCase().trim())
        {
            case "rar":return "application/x-rar-compressed";
            case "jpg":return "image/jpeg";
            case "zip":return "application/zip";
            case "pdf":return "application/pdf";
            case "doc":return "application/msword";
            case "docx":return "application/msword";
            case "wps":return "application/msword";
            case "xls":return "application/vnd.ms-excel";
            case "et":return "application/vnd.ms-excel";
            case "xlsx":return "application/vnd.ms-excel";
            case "ppt":return "application/vnd.ms-powerpoint";
            case "html":return "text/html";
            case "htm":return "text/html";
            case "txt":return "text/html";
            case "mp3":return "audio/mpeg";
            case "mp4":return "video/mp4";
            case "3gp":return "video/3gpp";
            case "wav":return "audio/x-wav";
            case "avi":return "video/x-msvideo";
            case "flv":return "flv-application/octet-stream";
            default: return "*/*";
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

    public void openFile(String filePath, String fileType)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        Uri data = Uri.fromFile(new File(filePath));
        intent.setDataAndType(data,getFileType(fileType));
        startActivity(intent);
    }

    public void successOfGetDatas(MsgDetailBean msgDetailBean)
    {
        mMsgdetailSwiperefreshlayout.setRefreshing(false);
        mMsgdetailTime.setText("发送时间 : " + (null != msgDetailBean.getCreateTime() ? msgDetailBean.getCreateTime().trim() : ""));
        mMsgdetailContent.setText("内容 : "+ (null != msgDetailBean.getTextContent() ? msgDetailBean.getTextContent().trim() : ""));
        mMsgdetailYamc.setText(null != msgDetailBean.getPlanName() ? msgDetailBean.getPlanName().trim() : "");
        mMsgdetailYadz.setText(null != msgDetailBean.getPlanAddress() ? msgDetailBean.getPlanAddress().trim() : "");
        mMsgdetailZzxclxdh.setText(null != msgDetailBean.getTelephone() ? msgDetailBean.getTelephone().trim() : "");
        StringBuffer fzrjdhBuffer = new StringBuffer();
        fzrjdhBuffer.append(null != msgDetailBean.getLeaderName() ? msgDetailBean.getLeaderName().trim()  + "(": "(");
        fzrjdhBuffer.append(null != msgDetailBean.getLeaderTelephone() ? msgDetailBean.getLeaderTelephone().trim()  + ")": ")");
        mMsgdetailFzrjdh.setText(fzrjdhBuffer.toString().trim());
        mMsgdetailJslxrdh.setText(null != msgDetailBean.getTechnicalTelephone() ? msgDetailBean.getTechnicalTelephone().trim() : "");
        mMsgdetailSblxrdh.setText(null != msgDetailBean.getDeviceTelephone() ? msgDetailBean.getDeviceTelephone().trim() : "");
        mMsgdetailWhp.setText(null != msgDetailBean.getChemical() ? msgDetailBean.getChemical().trim() : "");
        mMsgDetailImgsAdapter.setNewData(null != msgDetailBean.getPlanImages() ? msgDetailBean.getPlanImages() : new ArrayList<String>());
        mMsgDetailFilesAdapter.setNewData(null != msgDetailBean.getPlanFiles() ? msgDetailBean.getPlanFiles() : new ArrayList<String>());
        mMusicDataSource = null != msgDetailBean.getAudioUrl() ? mBaseImgPath + msgDetailBean.getAudioUrl().trim() : "";
        mMsgdetailMusic.setChecked(false);
        setMediaPlayResources();
    }
}