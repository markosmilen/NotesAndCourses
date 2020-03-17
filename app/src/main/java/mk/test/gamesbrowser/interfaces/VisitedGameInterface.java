package mk.test.gamesbrowser.interfaces;

import mk.test.gamesbrowser.model.Game;

public interface VisitedGameInterface {
    void onGameDelete(Game game);
    void onVisitedGameClick(Game game);
}
