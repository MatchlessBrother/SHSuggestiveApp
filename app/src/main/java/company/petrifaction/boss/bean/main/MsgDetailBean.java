package company.petrifaction.boss.bean.main;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

public class MsgDetailBean implements Parcelable
{
    /**
     * planFiles : ["1541243082402-363/1024(2).png"]
     * planName : 带图片文件的测试预案
     * textContent : 带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案带图片文件的测试预案
     * telephone : 13888888888
     * technicalTelephone : 13000000000
     * deviceTelephone : 13000000000
     * deviceName : 章庆文
     * technicalName : 章庆文
     * leaderTelephone : 13000000000
     * audioUrl : /1541243158642-95/1541243125964.wav
     * createTime : 2018-11-03T11:06:12.000+0000
     * leaderName : 章庆文
     * chemical :
     * planId :
     * id :
     * planImages : ["1541243082402-363/zanting-2.png","1541243082402-363/yuyin-2.png","1541243082402-363/1024(2).png"]
     */

    private String planName;
    private String textContent;
    private String telephone;
    private String technicalTelephone;
    private String deviceTelephone;
    private String deviceName;
    private String technicalName;
    private String leaderTelephone;
    private String audioUrl;
    private String createTime;
    private String leaderName;
    private String chemical;
    private String planId;
    private String id;
    private String planAddress;
    private List<String> planFiles;
    private List<String> planImages;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTechnicalTelephone() {
        return technicalTelephone;
    }

    public void setTechnicalTelephone(String technicalTelephone) {
        this.technicalTelephone = technicalTelephone;
    }

    public String getDeviceTelephone() {
        return deviceTelephone;
    }

    public void setDeviceTelephone(String deviceTelephone) {
        this.deviceTelephone = deviceTelephone;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getLeaderTelephone() {
        return leaderTelephone;
    }

    public void setLeaderTelephone(String leaderTelephone) {
        this.leaderTelephone = leaderTelephone;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getChemical() {
        return chemical;
    }

    public void setChemical(String chemical) {
        this.chemical = chemical;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPlanFiles() {
        return planFiles;
    }

    public void setPlanFiles(List<String> planFiles) {
        this.planFiles = planFiles;
    }

    public List<String> getPlanImages() {
        return planImages;
    }

    public void setPlanImages(List<String> planImages) {
        this.planImages = planImages;
    }

    public String getPlanAddress() {
        return planAddress;
    }

    public void setPlanAddress(String planAddress) {
        this.planAddress = planAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.planName);
        dest.writeString(this.textContent);
        dest.writeString(this.telephone);
        dest.writeString(this.technicalTelephone);
        dest.writeString(this.deviceTelephone);
        dest.writeString(this.deviceName);
        dest.writeString(this.technicalName);
        dest.writeString(this.leaderTelephone);
        dest.writeString(this.audioUrl);
        dest.writeString(this.createTime);
        dest.writeString(this.leaderName);
        dest.writeString(this.chemical);
        dest.writeString(this.planId);
        dest.writeString(this.id);
        dest.writeString(this.planAddress);
        dest.writeStringList(this.planFiles);
        dest.writeStringList(this.planImages);
    }

    public MsgDetailBean() {
    }

    protected MsgDetailBean(Parcel in) {
        this.planName = in.readString();
        this.textContent = in.readString();
        this.telephone = in.readString();
        this.technicalTelephone = in.readString();
        this.deviceTelephone = in.readString();
        this.deviceName = in.readString();
        this.technicalName = in.readString();
        this.leaderTelephone = in.readString();
        this.audioUrl = in.readString();
        this.createTime = in.readString();
        this.leaderName = in.readString();
        this.chemical = in.readString();
        this.planId = in.readString();
        this.id = in.readString();
        this.planAddress = in.readString();
        this.planFiles = in.createStringArrayList();
        this.planImages = in.createStringArrayList();
    }

    public static final Creator<MsgDetailBean> CREATOR = new Creator<MsgDetailBean>() {
        @Override
        public MsgDetailBean createFromParcel(Parcel source) {
            return new MsgDetailBean(source);
        }

        @Override
        public MsgDetailBean[] newArray(int size) {
            return new MsgDetailBean[size];
        }
    };
}