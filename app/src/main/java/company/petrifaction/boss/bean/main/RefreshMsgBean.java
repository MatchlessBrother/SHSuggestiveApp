package company.petrifaction.boss.bean.main;

import android.os.Parcel;
import android.os.Parcelable;

public class RefreshMsgBean implements Parcelable
{
    /**
     * id :
     * createTime :
     * textContent : 消息内容
     * replyStatus : 1
     * appNotifyStatus : 1
     * appPlayStatus : 1
     * planName : 预案名称
     * planId :
     * planAddress : 201cA10001”
     * planChemical : “危化物”
     */
    private String id;
    private String createTime;
    private String textContent;
    private int replyStatus;
    private int appNotifyStatus;
    private int appPlayStatus;
    private String planName;
    private String planId;
    private String planAddress;
    private String planChemical;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public int getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(int replyStatus) {
        this.replyStatus = replyStatus;
    }

    public int getAppNotifyStatus() {
        return appNotifyStatus;
    }

    public void setAppNotifyStatus(int appNotifyStatus) {
        this.appNotifyStatus = appNotifyStatus;
    }

    public int getAppPlayStatus() {
        return appPlayStatus;
    }

    public void setAppPlayStatus(int appPlayStatus) {
        this.appPlayStatus = appPlayStatus;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanAddress() {
        return planAddress;
    }

    public void setPlanAddress(String planAddress) {
        this.planAddress = planAddress;
    }

    public String getPlanChemical() {
        return planChemical;
    }

    public void setPlanChemical(String planChemical) {
        this.planChemical = planChemical;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.createTime);
        dest.writeString(this.textContent);
        dest.writeInt(this.replyStatus);
        dest.writeInt(this.appNotifyStatus);
        dest.writeInt(this.appPlayStatus);
        dest.writeString(this.planName);
        dest.writeString(this.planId);
        dest.writeString(this.planAddress);
        dest.writeString(this.planChemical);
    }

    public RefreshMsgBean() {
    }

    protected RefreshMsgBean(Parcel in) {
        this.id = in.readString();
        this.createTime = in.readString();
        this.textContent = in.readString();
        this.replyStatus = in.readInt();
        this.appNotifyStatus = in.readInt();
        this.appPlayStatus = in.readInt();
        this.planName = in.readString();
        this.planId = in.readString();
        this.planAddress = in.readString();
        this.planChemical = in.readString();
    }

    public static final Creator<RefreshMsgBean> CREATOR = new Creator<RefreshMsgBean>() {
        @Override
        public RefreshMsgBean createFromParcel(Parcel source) {
            return new RefreshMsgBean(source);
        }

        @Override
        public RefreshMsgBean[] newArray(int size) {
            return new RefreshMsgBean[size];
        }
    };
}