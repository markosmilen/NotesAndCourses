package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_to_beat")
public class TimeToBeat implements Parcelable {

    @PrimaryKey
    @ColumnInfo
    private Game game;
    @ColumnInfo
    private int completely, normally;

    public TimeToBeat() {}

    private TimeToBeat(Parcel in) {
        game = in.readParcelable(Game.class.getClassLoader());
        completely = in.readInt();
        normally = in.readInt();
    }

    public static final Creator<TimeToBeat> CREATOR = new Creator<TimeToBeat>() {
        @Override
        public TimeToBeat createFromParcel(Parcel in) {
            return new TimeToBeat(in);
        }

        @Override
        public TimeToBeat[] newArray(int size) {
            return new TimeToBeat[size];
        }
    };

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getCompletely() {
        return completely;
    }

    public void setCompletely(int completely) {
        this.completely = completely;
    }

    public int getNormally() {
        return normally;
    }

    public void setNormally(int normally) {
        this.normally = normally;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(game, i);
        parcel.writeInt(completely);
        parcel.writeInt(normally);
    }
}
