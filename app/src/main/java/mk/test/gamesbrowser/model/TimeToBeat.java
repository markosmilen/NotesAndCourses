package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeToBeat implements Parcelable {
    private int game;
    private int completely, normally;

    public TimeToBeat() {}

    protected TimeToBeat(Parcel in) {
        game = in.readInt();
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

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
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
        parcel.writeInt(game);
        parcel.writeInt(completely);
        parcel.writeInt(normally);
    }
}
