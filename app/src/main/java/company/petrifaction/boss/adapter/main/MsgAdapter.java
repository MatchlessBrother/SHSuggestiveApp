package company.petrifaction.boss.adapter.main;

import java.util.List;
import android.content.Context;
import company.petrifaction.boss.R;
import android.support.annotation.Nullable;
import company.petrifaction.boss.bean.main.MsgBean;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;

public class MsgAdapter extends BaseQuickAdapter<MsgBean,BaseViewHolder>
{
    private Context mContext;
    public MsgAdapter(Context context,@Nullable List<MsgBean> data)
    {
        super(R.layout.item_msg, data);
        this.mContext = mContext;
    }

    protected void convert(BaseViewHolder helper, MsgBean item)
    {

    }
}