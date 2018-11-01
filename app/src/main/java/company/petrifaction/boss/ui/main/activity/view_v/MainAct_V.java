package company.petrifaction.boss.ui.main.activity.view_v;

import company.petrifaction.boss.bean.main.MsgBean;
import company.petrifaction.boss.ui.base.BaseMvp_View;

public interface MainAct_V extends BaseMvp_View
{
    void getFailOfMsg();
    void signOutSuccess();
    void signOutFailure();
    void getSuccessOfMsg(MsgBean msgBean);
}