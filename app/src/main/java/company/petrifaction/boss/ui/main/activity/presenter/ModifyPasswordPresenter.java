package company.petrifaction.boss.ui.main.activity.presenter;

import company.petrifaction.boss.bean.BaseReturnData;
import company.petrifaction.boss.ui.base.BaseMvp_Presenter;
import company.petrifaction.boss.ui.base.BaseMvp_EntranceOfModel;
import company.petrifaction.boss.ui.base.BaseMvp_LocalObjCallBack;
import company.petrifaction.boss.ui.main.activity.model.ModifyPasswordModel;
import company.petrifaction.boss.ui.main.activity.view_v.ModifyPasswordAct_V;

public class ModifyPasswordPresenter extends BaseMvp_Presenter<ModifyPasswordAct_V>
{
    public void modifyPassword(String oldPassword,String newPassword)
    {
        if(isAttachContextAndViewLayer())
        {
            BaseMvp_EntranceOfModel.requestDatas(ModifyPasswordModel.class).
            putForm("oldPassword",oldPassword).putForm("newPassword",newPassword).convertForms().executeOfNet(getContext(),ModifyPasswordModel.ModifyPassword,new BaseMvp_LocalObjCallBack<BaseReturnData>(this)
            {
                public void onSuccess(BaseReturnData baseReturnData)
                {
                    if(isAttachContextAndViewLayer())
                    {
                        getViewLayer().successOfModifyPassword();
                    }
                }
            });
        }
    }
}