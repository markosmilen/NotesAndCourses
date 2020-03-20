package mk.test.gamesbrowser.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.repository.GameRepository;

public class GameViewModel extends AndroidViewModel {

    private GameRepository gameRepository;

    public GameViewModel(@NonNull Application application) {
        super(application);

        gameRepository = new GameRepository(application);
    }

    public void insert(Game game){
        gameRepository.insert(game);
    }

    public void deleteGame(Game game){
        gameRepository.delete(game);
    }

    public void deleteAll(){
        gameRepository.deleteAll();
    }

    public void DeleteVisitedGames(){ gameRepository.deleteVisitedGames();}

    public LiveData<List<Game>> getWantedGames(){
        return gameRepository.getWantedGames();
    }

    public LiveData<List<Game>> getPlayingGames(){
        return gameRepository.getPlayingGames();
    }

    public LiveData<List<Game>> getPlayedGames(){
        return gameRepository.getPlayedGames();
    }

    public LiveData<List<Game>> getVisitedGames(){
        return gameRepository.getVisitedGames();
    }
}
