package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GameImage implements Parcelable {
    private int id;
    private int width, height;
    private String image_id, url;

    public GameImage() {}

    protected GameImage(Parcel in) {
        id = in.readInt();
        width = in.readInt();
        height = in.readInt();
        image_id = in.readString();
        url = in.readString();
    }

    public static final Creator<GameImage> CREATOR = new Creator<GameImage>() {
        @Override
        public GameImage createFromParcel(Parcel in) {
            return new GameImage(in);
        }

        @Override
        public GameImage[] newArray(int size) {
            return new GameImage[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeString(image_id);
        parcel.writeString(url);
    }
}
