package company.petrifaction.boss.bean.main;

import android.os.Parcel;
import android.os.Parcelable;

public class MsgBean implements Parcelable
{
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
    }

    public MsgBean() {
    }

    protected MsgBean(Parcel in) {
        this.content = in.readString();
    }

    public static final Creator<MsgBean> CREATOR = new Creator<MsgBean>() {
        @Override
        public MsgBean createFromParcel(Parcel source) {
            return new MsgBean(source);
        }

        @Override
        public MsgBean[] newArray(int size) {
            return new MsgBean[size];
        }
    };
}