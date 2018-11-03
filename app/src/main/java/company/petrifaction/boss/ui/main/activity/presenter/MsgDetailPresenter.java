package company.petrifaction.boss.ui.main.activity.presenter;

import company.petrifaction.boss.bean.BaseReturnData;
import company.petrifaction.boss.bean.main.MsgDetailBean;
import company.petrifaction.boss.ui.base.BaseMvp_Presenter;
import company.petrifaction.boss.ui.base.BaseMvp_EntranceOfModel;
import company.petrifaction.boss.ui.base.BaseMvp_LocalObjCallBack;
import company.petrifaction.boss.ui.main.activity.model.MsgDetailModel;
import company.petrifaction.boss.ui.main.activity.view_v.MsgDetailAct_V;

public class MsgDetailPresenter extends BaseMvp_Presenter<MsgDetailAct_V>
{
    public void getMsgOfDetailDatas(String msgId)
    {
        if(isAttachContextAndViewLayer())
        {
            BaseMvp_EntranceOfModel.requestDatas(MsgDetailModel.class).
            putForm("id",msgId).convertForms().executeOfNet(getContext(),MsgDetailModel.GetDetailDatas,new BaseMvp_LocalObjCallBack<BaseReturnData<MsgDetailBean>>(this)
            {
                public void onSuccess(BaseReturnData<MsgDetailBean> msgDetailBean)
                {
                    if(isAttachContextAndViewLayer())
                    {
                        getViewLayer().successOfGetDatas(msgDetailBean.getData());
                    }
                }

                public void onFailure(String msg)
                {
                    super.onFailure(msg);
                    if(isAttachContextAndViewLayer())
                    {
                        getViewLayer().failOfGetDatas();
                    }
                }

                public void onError(String msg)
                {
                    super.onError(msg);
                    if(isAttachContextAndViewLayer())
                    {
                        getViewLayer().failOfGetDatas();
                    }
                }
            });
        }
    }
}