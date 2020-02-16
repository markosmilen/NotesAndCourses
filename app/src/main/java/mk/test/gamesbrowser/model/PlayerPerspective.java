package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerPerspective implements Parcelable {
    private int id;
    private String name;
    private String url;

    public PlayerPerspective() {}

    protected PlayerPerspective(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<PlayerPerspective> CREATOR = new Creator<PlayerPerspective>() {
        @Override
        public PlayerPerspective createFromParcel(Parcel in) {
            return new PlayerPerspective(in);
        }

        @Override
        public PlayerPerspective[] newArray(int size) {
            return new PlayerPerspective[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        parcel.writeString(name);
        parcel.writeString(url);
    }
}
