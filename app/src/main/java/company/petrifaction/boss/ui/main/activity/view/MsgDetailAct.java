package company.petrifaction.boss.ui.main.activity.view;

import android.view.View;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import android.widget.LinearLayout;
import company.petrifaction.boss.R;
import android.support.v7.widget.RecyclerView;
import company.petrifaction.boss.base.BaseAct;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import company.petrifaction.boss.bean.main.MsgDetailBean;
import company.petrifaction.boss.adapter.main.MsgDetailImgsAdapter;
import company.petrifaction.boss.adapter.main.MsgDetailFilesAdapter;
import company.petrifaction.boss.ui.main.activity.view_v.MsgDetailAct_V;
import company.petrifaction.boss.ui.main.activity.presenter.MsgDetailPresenter;

public class MsgDetailAct extends BaseAct implements MsgDetailAct_V,View.OnClickListener
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

    protected int setLayoutResID()
    {
        return R.layout.activity_msgdetail;
    }

    protected void initWidgets(View rootView)
    {
        super.initWidgets(rootView);
        setTitleContent("消息详情");
        mMsgId = getIntent().getStringExtra("msgid");
        mBaseImgPath = "http://git.yunfanwulian.com:20001/";
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
                    //下载文件并开启第三方软件进行浏览
                }
            }
        });
    }

    protected void initLogic()
    {
        mMsgDetailPresenter.getMsgOfDetailDatas(mMsgId);

    }

    public void failOfGetDatas()
    {
        mMsgdetailSwiperefreshlayout.setRefreshing(false);

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
    }
}