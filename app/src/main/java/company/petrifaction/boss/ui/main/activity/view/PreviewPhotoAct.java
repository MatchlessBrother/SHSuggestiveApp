package company.petrifaction.boss.ui.main.activity.view;

import android.view.View;
import company.petrifaction.boss.R;
import company.petrifaction.boss.base.BaseAct;
import com.github.chrisbanes.photoview.PhotoView;

public class PreviewPhotoAct extends BaseAct
{
    private PhotoView mPreview;
    protected int setLayoutResID()
    {
        return R.layout.activity_previewphoto;
    }

    protected void initWidgets(View rootView)
    {
        super.initWidgets(rootView);
        setTitleContent("查看图片");
        mPreview = (PhotoView)rootView.findViewById(R.id.previewphoto_photoview);
    }

    protected void initDatas()
    {
        if(null != getIntent().getStringExtra("imgPath") && !"".equals(getIntent().getStringExtra("imgPath").trim()))
            useGlideLoadImg(mPreview,getIntent().getStringExtra("imgPath"));
        else
            mPreview.setVisibility(View.GONE);
    }

    protected void initLogic()
    {

    }
}