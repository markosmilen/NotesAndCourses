package mk.test.gamesbrowser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.model.Game;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView textView = findViewById(R.id.game);

        Game game = getIntent().getParcelableExtra("game");
        textView.setText(game.getName());
    }
}
