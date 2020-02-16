package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Platform implements Parcelable {
    private int id;
    private String abbreviation;
    private String name;
    private int platform_logo;
    private String url;

    public Platform() {}

    protected Platform(Parcel in) {
        id = in.readInt();
        abbreviation = in.readString();
        name = in.readString();
        platform_logo = in.readInt();
        url = in.readString();
    }

    public static final Creator<Platform> CREATOR = new Creator<Platform>() {
        @Override
        public Platform createFromParcel(Parcel in) {
            return new Platform(in);
        }

        @Override
        public Platform[] newArray(int size) {
            return new Platform[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public int getPlatform_logo() {
        return platform_logo;
    }

    public void setPlatform_logo(int platform_logo) {
        this.platform_logo = platform_logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(abbreviation);
        parcel.writeString(name);
        parcel.writeInt(platform_logo);
        parcel.writeString(url);
    }
}
