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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Game game);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ArrayList<Game> games);

    @Delete
    void deleteGame(Game game);

    @Query("DELETE FROM game_table")
    void deleteAllGames();

    @Query("SELECT * FROM game_table")
    LiveData<List<Game>> getWantedGames();
}
