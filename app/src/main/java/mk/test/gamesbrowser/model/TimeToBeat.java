package mk.test.gamesbrowser.model;

import java.sql.Time;

public class TimeToBeat {
    private int game;
    private int completely, normally;

    public TimeToBeat() {}

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
}
