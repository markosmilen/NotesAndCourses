package mk.test.gamesbrowser.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import mk.test.gamesbrowser.model.Game;

@Dao
public interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Game game);

    @Delete
    void deleteGame(Game game);

    @Query("DELETE FROM game_table")
    void deleteAllGames();

    @Query("SELECT * FROM game_table WHERE isWanted")
    LiveData<List<Game>> getWantedGames();

    @Query("SELECT * FROM game_table WHERE isPlaying")
    LiveData<List<Game>> getPlayingGames();

    @Query("SELECT * FROM game_table WHERE isPlayed")
    LiveData<List<Game>> getPlayedGames();

    @Query("SELECT * FROM game_table WHERE isVisited")
    LiveData<List<Game>> getVisitedGames();

    @Query("DELETE FROM game_table WHERE isVisited")
    void deleteVisitedGames();
}
