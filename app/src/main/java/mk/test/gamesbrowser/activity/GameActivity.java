package mk.test.gamesbrowser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.adapter.ThemeAdapter;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.model.GamePhrase;
import mk.test.gamesbrowser.model.Platform;

public class GameActivity extends AppCompatActivity {

    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView topImage, gameCoverAct;
        TextView gameTitle, gamePublisher, gameReleaseYear, gameRating, gameRatingCount, gameDescription;
        RecyclerView genresRecyclerView, platformsRecyclerView, themesRecyclerView;

        topImage = findViewById(R.id.top_image);
        gameCoverAct = findViewById(R.id.game_cover_act);
        gameTitle = findViewById(R.id.game_title);
        gamePublisher = findViewById(R.id.game_publisher);
        gameReleaseYear = findViewById(R.id.game_release_year);
        gameRating = findViewById(R.id.game_rating);
        gameRatingCount = findViewById(R.id.game_rating_count);
        gameDescription = findViewById(R.id.game_description);
        genresRecyclerView = findViewById(R.id.genres_recycler_view);
        platformsRecyclerView = findViewById(R.id.platforms_recycler_view);
        themesRecyclerView = findViewById(R.id.themes_recycler_view);

        game = getIntent().getParcelableExtra("game");

        if (game.getScreenshots() != null) {
            Random random = new Random();
            int randomScreenshot = random.nextInt(game.getScreenshots().size());

            Glide
                    .with(this)
                    .load(getResources().getString(R.string.screenshot_url) + game.getScreenshots().get(randomScreenshot).getImage_id() + ".jpg")
                    .centerCrop()
                    .placeholder(getResources().getDrawable(R.drawable.placeholder))
                    .into(topImage);
        }

        if (game.getCover().getUrl() != null) {
            Glide
                    .with(this)
                    .load(getString(R.string.cover_url) + game.getCover().getImage_id() + ".jpg")
                    .centerCrop()
                    .placeholder(getResources().getDrawable(R.drawable.placeholder))
                    .into(gameCoverAct);
        }

        gameTitle.setText(game.getName());
        gamePublisher.setText("Publisher"); //TODO Publisher

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String dateString = formatter.format((new Date((long) game.getFirst_release_date() * 1000)));
        gameReleaseYear.setText(dateString);

        gameRating.setText((int) game.getRating() + "");
        gameRatingCount.setText((int) game.getRating_count() + " ratings");
        gameDescription.setText(game.getSummary());

        ArrayList<String> platforms = getPlatforms(game.getPlatforms());
        ArrayList<String> genres = getStrings(game.getGenres());
        ArrayList<String> themes = getStrings(game.getThemes());

        platformsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genresRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        themesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        ThemeAdapter platformsAdapter = new ThemeAdapter(this, platforms);
        ThemeAdapter genresAdapter = new ThemeAdapter(this, genres);
        ThemeAdapter themeAdapter = new ThemeAdapter(this, themes);

        platformsRecyclerView.setAdapter(platformsAdapter);
        genresRecyclerView.setAdapter(genresAdapter);
        themesRecyclerView.setAdapter(themeAdapter);

    }

    public void onAddButtonClick(View view) {
        String buttonPressed="";
        switch(view.getId()){
            case R.id.add_want:
                buttonPressed = "WANT";
                break;
            case R.id.add_playing:
                buttonPressed = "PLAYING";
                break;
            case R.id.add_played:
                buttonPressed = "PLAYED";
                break;
        }
        Toast.makeText(this, buttonPressed, Toast.LENGTH_SHORT).show();
    }

    public void onReviewsClick(View view) {
        Toast.makeText(this, "Reviews Button Clicked", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<String> getStrings(ArrayList<GamePhrase> phrases){
        ArrayList<String> strings = new ArrayList<>();
        for(GamePhrase phrase : phrases){
            strings.add(phrase.getName());
        }
        return strings;
    }

    public ArrayList<String> getPlatforms(ArrayList<Platform> platforms){
        ArrayList<String> strings = new ArrayList<>();
        for(Platform platform : platforms){
            strings.add(platform.getName());
        }
        return strings;
    }
}
