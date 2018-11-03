package company.petrifaction.boss.ui.main.activity.view_v;

import company.petrifaction.boss.ui.base.BaseMvp_View;
import company.petrifaction.boss.bean.main.MsgDetailBean;

public interface MsgDetailAct_V extends BaseMvp_View
{
    void failOfGetDatas();
    void successOfGetDatas(MsgDetailBean msgDetailBean);
}