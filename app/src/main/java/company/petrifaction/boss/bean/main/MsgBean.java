package company.petrifaction.boss.bean.main;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

public class MsgBean implements Parcelable
{
    /**
     * totalRows : 3
     * totalPages : 1
     * pageIndex : 0
     * pageSize : 20
     * content : [{"createTime":"","replyStatus":2,"textContent":"测试预案一","id":""},{"createTime":1541184585000,"replyStatus":2,"textContent":"直接发布测试","id":6},{"createTime":1541181743000,"replyStatus":1,"textContent":"发布一","id":5}]
     */
    private int totalRows;
    private int totalPages;
    private int pageIndex;
    private int pageSize;
    private List<ContentBean> content;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Parcelable
    {
        /**
         * createTime :
         * replyStatus : 2
         * textContent : 测试预案一
         * id :
         */

        private String createTime;
        private int replyStatus;
        private String textContent;
        private String id;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getReplyStatus() {
            return replyStatus;
        }

        public void setReplyStatus(int replyStatus) {
            this.replyStatus = replyStatus;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.createTime);
            dest.writeInt(this.replyStatus);
            dest.writeString(this.textContent);
            dest.writeString(this.id);
        }

        public ContentBean() {
        }

        protected ContentBean(Parcel in) {
            this.createTime = in.readString();
            this.replyStatus = in.readInt();
            this.textContent = in.readString();
            this.id = in.readString();
        }

        public static final Creator<ContentBean> CREATOR = new Creator<ContentBean>() {
            @Override
            public ContentBean createFromParcel(Parcel source) {
                return new ContentBean(source);
            }

            @Override
            public ContentBean[] newArray(int size) {
                return new ContentBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalRows);
        dest.writeInt(this.totalPages);
        dest.writeInt(this.pageIndex);
        dest.writeInt(this.pageSize);
        dest.writeTypedList(this.content);
    }

    public MsgBean() {
    }

    protected MsgBean(Parcel in) {
        this.totalRows = in.readInt();
        this.totalPages = in.readInt();
        this.pageIndex = in.readInt();
        this.pageSize = in.readInt();
        this.content = in.createTypedArrayList(ContentBean.CREATOR);
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