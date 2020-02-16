package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cover implements Parcelable {
    private int id, game;
    private boolean alpha_channel, animated;
    private int width, height;
    private String image_id;
    private String url;

    public Cover () {}

    protected Cover(Parcel in) {
        id = in.readInt();
        game = in.readInt();
        alpha_channel = in.readByte() != 0;
        animated = in.readByte() != 0;
        width = in.readInt();
        height = in.readInt();
        image_id = in.readString();
        url = in.readString();
    }

    public static final Creator<Cover> CREATOR = new Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel in) {
            return new Cover(in);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public boolean isAlpha_channel() {
        return alpha_channel;
    }

    public void setAlpha_channel(boolean alpha_channel) {
        this.alpha_channel = alpha_channel;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
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
        parcel.writeInt(game);
        parcel.writeByte((byte) (alpha_channel ? 1 : 0));
        parcel.writeByte((byte) (animated ? 1 : 0));
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeString(image_id);
        parcel.writeString(url);
    }
}
