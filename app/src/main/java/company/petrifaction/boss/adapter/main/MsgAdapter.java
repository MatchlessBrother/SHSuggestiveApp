package company.petrifaction.boss.adapter.main;

import java.util.Date;
import java.util.List;
import android.widget.TextView;
import android.content.Context;
import java.text.SimpleDateFormat;
import company.petrifaction.boss.R;
import android.support.annotation.Nullable;
import company.petrifaction.boss.bean.main.MsgBean;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;

public class MsgAdapter extends BaseQuickAdapter<MsgBean.ContentBean,BaseViewHolder>
{
    private Date mDate;
    private Context mContext;
    private SimpleDateFormat mSimpleDateFormat;

    public MsgAdapter(Context context,@Nullable List<MsgBean.ContentBean> data)
    {
        super(R.layout.item_msg, data);
        mDate = new Date();
        mContext = context;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    protected void convert(BaseViewHolder helper, MsgBean.ContentBean contentBean)
    {
        if(contentBean.getReplyStatus() == 2)
        {
            helper.setText(R.id.itemmsg_status,"已读");
            helper.setTextColor(R.id.itemmsg_time,mContext.getResources().getColor(R.color.default_font_black));
            helper.setTextColor(R.id.itemmsg_status,mContext.getResources().getColor(R.color.default_font_gray));
            helper.setTextColor(R.id.itemmsg_content,mContext.getResources().getColor(R.color.default_font_gray));
            ((TextView)helper.getView(R.id.itemmsg_status)).setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_msg_gray),null,null,null);
        }
        else
        {
            helper.setText(R.id.itemmsg_status,"未读");
            helper.setTextColor(R.id.itemmsg_time,mContext.getResources().getColor(R.color.colorPrimary));
            helper.setTextColor(R.id.itemmsg_status,mContext.getResources().getColor(R.color.default_font_black));
            helper.setTextColor(R.id.itemmsg_content,mContext.getResources().getColor(R.color.default_font_black));
            ((TextView)helper.getView(R.id.itemmsg_status)).setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_msg_blue),null,null,null);
        }
        mDate.setTime(Long.valueOf(contentBean.getCreateTime().trim()));
        helper.setText(R.id.itemmsg_content,"内容 : " + contentBean.getTextContent().trim());
        helper.setText(R.id.itemmsg_time,"发送时间 : " + mSimpleDateFormat.format(mDate));
    }
}