package company.petrifaction.boss.ui.main.activity.view_v;

import company.petrifaction.boss.bean.main.MsgBean;
import company.petrifaction.boss.ui.base.BaseMvp_View;

public interface MainAct_V extends BaseMvp_View
{
    void signOutSuccess();
    void signOutFailure();
    void finishRefresh();
    void finishLoadMore();
    void refreshDatas(MsgBean msgPageInfo);
    void loadMoreDatas(MsgBean msgPageInfo);
}