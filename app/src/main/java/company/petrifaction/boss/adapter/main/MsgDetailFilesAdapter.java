package company.petrifaction.boss.adapter.main;

import java.util.List;
import android.content.Context;
import company.petrifaction.boss.R;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;

public class MsgDetailFilesAdapter  extends BaseQuickAdapter<String,BaseViewHolder>
{
    private String mBasePath;
    private Context mContext;
    public MsgDetailFilesAdapter(Context context, @Nullable List<String> data)
    {
        super(R.layout.item_msgdetailfiles,data);
        mContext = context;
        mBasePath = "http://git.yunfanwulian.com:20001/";
    }

    protected void convert(BaseViewHolder helper, String path)
    {
        helper.setText(R.id.item_msgdetailfile_content,null != path ? path.trim() : "");
        if(helper.getAdapterPosition() == getData().size() - 1)
            helper.setGone(R.id.item_msgdetailfile_dividerline,false);
        else
            helper.setGone(R.id.item_msgdetailfile_dividerline,true);
    }
}