package mk.test.gamesbrowser.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import mk.test.gamesbrowser.dao.GameDao;
import mk.test.gamesbrowser.database.GameDatabase;
import mk.test.gamesbrowser.model.Game;

public class GameRepository {

    private GameDao gameDao;
    private LiveData<List<Game>> wantedGames;

    public GameRepository(Application application){
        GameDatabase gameDatabase = GameDatabase.getInstance(application);
        gameDao = gameDatabase.gameDao();
        wantedGames = gameDao.getWantedGames();
    }

    public LiveData<List<Game>> getWantedGames(){
        return gameDao.getWantedGames();
    }


    public void insert(Game game){
        new InsertAsyncTask(gameDao).execute(game);
    }

    public void delete(Game game){
        new DeleteItemAsyncTask(gameDao).execute(game);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(gameDao).execute();
    }

    //-------------INSERT GAME ASYNC TASK
    private static class InsertAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao asyncDao;

        public InsertAsyncTask(GameDao asyncDao){
            this.asyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            asyncDao.insert(games[0]);
            return null;
        }
    }

    //----------DELETE GAME ASYNC TASK
    private static class DeleteItemAsyncTask extends AsyncTask <Game, Void, Void> {

        private GameDao asyncDao;

        public DeleteItemAsyncTask(GameDao asyncDao){
            this.asyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            asyncDao.deleteGame(games[0]);
            return null;
        }
    }

    //-------------DELETE ALL ASYNC TASK
    private static class DeleteAllAsyncTask extends AsyncTask <Void, Void, Void>{
        private GameDao asyncDao;

        public DeleteAllAsyncTask(GameDao asyncDao){
            this.asyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncDao.deleteAllGames();
            return null;
        }
    }
}
