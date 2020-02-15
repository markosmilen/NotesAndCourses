package mk.test.gamesbrowser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.model.Game;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView topImage, gameCover;
        TextView gameTitle, gamePublisher, gameReleaseYear, gameRating, gameRatingCount, gameDescription;
        RecyclerView genresRecyclerView, platformsRecyclerView, themesRecyclerView;

        topImage = findViewById(R.id.top_image);
        gameCover = findViewById(R.id.game_cover);
        gameTitle = findViewById(R.id.game_title);
        gamePublisher = findViewById(R.id.game_publisher);
        gameReleaseYear = findViewById(R.id.game_release_year);
        gameRating = findViewById(R.id.game_rating);
        gameRatingCount = findViewById(R.id.game_rating_count);
        gameDescription = findViewById(R.id.game_description);
        genresRecyclerView = findViewById(R.id.genres_recycler_view);
        platformsRecyclerView = findViewById(R.id.platforms_recycler_view);
        themesRecyclerView = findViewById(R.id.themes_recycler_view);

        Game game = getIntent().getParcelableExtra("game");

        /*Glide
                .with(this)
                .load("https:" + game.getScreenshots().get(0).getUrl())
                .centerCrop()
                .into(topImage);

        Glide
                .with(this)
                .load("https:" + game.getCover().getUrl())
                .centerCrop()
                .into(gameCover);*/

        gameTitle.setText(game.getName());
        gamePublisher.setText("Publisher");
        gameReleaseYear.setText(game.getFirst_release_date() + "");
        gameRating.setText((int) game.getRating() + "");
        gameRatingCount.setText("Based on " + game.getRating_count() + "ratings");
        gameDescription.setText(game.getStoryline());

        platformsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genresRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        themesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


    }

    public void onAddButtonClick(View view) {
        Toast.makeText(this, "Add Button Clicked", Toast.LENGTH_SHORT).show();
    }

    public void onReviewsClick(View view) {
        Toast.makeText(this, "Reviews Button Clicked", Toast.LENGTH_SHORT).show();
    }
}
