package company.petrifaction.boss.adapter.main;

import java.util.List;
import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import company.petrifaction.boss.R;
import android.support.annotation.Nullable;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MsgDetailImgsAdapter extends BaseQuickAdapter<String,BaseViewHolder>
{
    private String mBasePath;
    private Context mContext;
    public MsgDetailImgsAdapter(Context context, @Nullable List<String> data)
    {
        super(R.layout.item_msgdetailimgs,data);
        mContext = context;
        mBasePath = "http://git.yunfanwulian.com:20001/";
    }

    protected void convert(BaseViewHolder helper,String path)
    {
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.defaultimage);
        options.placeholder(R.mipmap.defaultimage);
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(null != path ? mBasePath + path.trim() : "").apply(options).into((ImageView) helper.getView(R.id.item_msgdetailimg_img));
    }
}