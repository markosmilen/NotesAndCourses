package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GameVideo implements Parcelable {
    private int id, game;
    private String name;
    private String video_id;

    public GameVideo() {}

    protected GameVideo(Parcel in) {
        id = in.readInt();
        game = in.readInt();
        name = in.readString();
        video_id = in.readString();
    }

    public static final Creator<GameVideo> CREATOR = new Creator<GameVideo>() {
        @Override
        public GameVideo createFromParcel(Parcel in) {
            return new GameVideo(in);
        }

        @Override
        public GameVideo[] newArray(int size) {
            return new GameVideo[size];
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(game);
        parcel.writeString(name);
        parcel.writeString(video_id);
    }
}
