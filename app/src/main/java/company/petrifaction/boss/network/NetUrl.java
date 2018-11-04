package company.petrifaction.boss.network;

import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import io.reactivex.Observable;
import retrofit2.http.Multipart;
import company.petrifaction.boss.bean.main.MsgBean;
import company.petrifaction.boss.bean.main.UserInfo;
import company.petrifaction.boss.bean.BaseReturnData;
import company.petrifaction.boss.bean.main.MsgDetailBean;
import company.petrifaction.boss.bean.BaseReturnListData;
import company.petrifaction.boss.bean.main.RefreshMsgBean;

public interface NetUrl
{
    @POST("/auth/logout.app")
    Observable<BaseReturnData> signOut();

    @POST("/yjfb/notify/newMessage.app")
    Observable<BaseReturnListData<RefreshMsgBean>> refreshMsg();

    @POST("/yjfb/notify/finishAction.app")
    @Multipart
    Observable<BaseReturnData> refreshMsgEnd(@PartMap Map<String, RequestBody> params);

    @POST("/auth/modifyPassword.app")
    @Multipart
    Observable<BaseReturnData> modifyPassword(@PartMap Map<String, RequestBody> params);

    @POST("/yjfb/notify/list.app")
    @Multipart
    Observable<BaseReturnData<MsgBean>> getMsg(@PartMap Map<String, RequestBody> params);

    @POST("/auth/login.app")
    @Multipart
    Observable<BaseReturnData<UserInfo>> signIn(@PartMap Map<String, RequestBody> params);

    @POST("/yjfb/notify/detail.app")
    @Multipart
    Observable<BaseReturnData<MsgDetailBean>> getMsgOfDetailDats(@PartMap Map<String, RequestBody> params);
}