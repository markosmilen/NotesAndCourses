package mk.test.gamesbrowser.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import mk.test.gamesbrowser.dao.GameDao;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.type_converter.Converters;

@Database(entities = Game.class, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class GameDatabase extends RoomDatabase {

    public abstract GameDao gameDao();

    private static GameDatabase INSTANCE;

    public static GameDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (GameDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameDatabase.class, "game_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateAsyncTask(INSTANCE).execute();
        }
    };

    public static class PopulateAsyncTask extends AsyncTask<Void, Void, Void>{

        private final GameDao gameDao;

        public PopulateAsyncTask(GameDatabase gameDatabase){
            gameDao = gameDatabase.gameDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
