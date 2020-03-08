package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_phrase")
public class GamePhrase implements Parcelable {

    @PrimaryKey
    @ColumnInfo
    private int id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String url;

    public GamePhrase() {}

    protected GamePhrase(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<GamePhrase> CREATOR = new Creator<GamePhrase>() {
        @Override
        public GamePhrase createFromParcel(Parcel in) {
            return new GamePhrase(in);
        }

        @Override
        public GamePhrase[] newArray(int size) {
            return new GamePhrase[size];
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
