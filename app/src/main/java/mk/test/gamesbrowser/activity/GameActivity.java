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
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.adapter.ScreenshotAdapter;
import mk.test.gamesbrowser.adapter.ThemeAdapter;
import mk.test.gamesbrowser.interfaces.ScreenshotClickInterface;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.model.GameImage;
import mk.test.gamesbrowser.model.GamePhrase;
import mk.test.gamesbrowser.model.InvolvedCompany;
import mk.test.gamesbrowser.model.Platform;

public class GameActivity extends AppCompatActivity implements ScreenshotClickInterface {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView topImage, gameCoverAct;
        TextView gameTitle, gamePublisher, gameReleaseYear, gameRating, gameRatingCount, gameDescription;
        TextView screenshotsText, videosText;
        RecyclerView genresRecyclerView, platformsRecyclerView, themesRecyclerView, gameModesRecyclerView, perspectivesRecyclerView;
        RecyclerView screenshotsRecyclerView, videosRecyclerView;
        LinearLayout genresLayout, themesLayout, gameModesLayout;

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
        gameModesRecyclerView = findViewById(R.id.game_modes_recycler_view);
        perspectivesRecyclerView = findViewById(R.id.perspectives_recycler_view);
        screenshotsRecyclerView = findViewById(R.id.screenshots_recycler_view);
        videosRecyclerView = findViewById(R.id.videos_recycler_view);
        screenshotsText = findViewById(R.id.screenshots_text);
        videosText = findViewById(R.id.videos_text);

        genresLayout = findViewById(R.id.layout_genres);
        gameModesLayout = findViewById(R.id.layout_game_modes);
        themesLayout = findViewById(R.id.layout_themes);

        game = getIntent().getParcelableExtra("game");

        if (game != null) {
            if (game.getScreenshots() != null) {
                Random random = new Random();
                int randomScreenshot = random.nextInt(game.getScreenshots().size());

                Glide
                        .with(this)
                        .load(getResources().getString(R.string.screenshot_url) + game.getScreenshots().get(randomScreenshot).getImage_id() + ".jpg")
                        .centerCrop()
                        .placeholder(getResources().getDrawable(R.drawable.placeholderhoriz))
                        .into(topImage);
            } else {
                topImage.setVisibility(View.GONE);
            }

            if (game.getCover() != null) {
                Glide
                        .with(this)
                        .load(getString(R.string.cover_url) + game.getCover().getImage_id() + ".jpg")
                        .centerCrop()
                        .placeholder(getResources().getDrawable(R.drawable.placeholder))
                        .into(gameCoverAct);
            }

            gameTitle.setText(game.getName());

            if (game.getInvolved_companies() != null) {
                for (InvolvedCompany involvedCompany : game.getInvolved_companies()) {
                    if (involvedCompany.isPublisher()) {
                        gamePublisher.setText(involvedCompany.getCompany().getName());
                    }
                }
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
            String dateString = formatter.format((new Date((long) game.getFirst_release_date() * 1000)));
            gameReleaseYear.setText(dateString);

            if (game.getRating() != 0) {
                gameRating.setText((int) game.getRating() + "");
                gameRatingCount.setText((int) game.getRating_count() + " ratings");
            } else {
                gameRating.setText(getString(R.string.not_applicable));
                gameRatingCount.setText(getString(R.string.need_more_ratings));
            }

            gameDescription.setText(game.getSummary());

            ArrayList<String> platforms = getPlatforms(game.getPlatforms());
            platformsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            ThemeAdapter platformsAdapter = new ThemeAdapter(this, platforms);
            platformsRecyclerView.setAdapter(platformsAdapter);

            ArrayList<String> gamePerspectives = getStrings(game.getPlayer_perspectives());
            perspectivesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            ThemeAdapter perspectivesAdapter = new ThemeAdapter(this, gamePerspectives);
            perspectivesRecyclerView.setAdapter(perspectivesAdapter);
            perspectivesRecyclerView.setVisibility(View.GONE);//TO BE PLACED ELSEWHERE

            if (game.getGenres() != null) {
                ArrayList<String> genres = getStrings(game.getGenres());
                genresRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ThemeAdapter genresAdapter = new ThemeAdapter(this, genres);
                genresRecyclerView.setAdapter(genresAdapter);
            } else {
                genresLayout.setVisibility(View.GONE);
            }

            if (game.getThemes() != null) {
                ArrayList<String> themes = getStrings(game.getThemes());
                themesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ThemeAdapter themeAdapter = new ThemeAdapter(this, themes);
                themesRecyclerView.setAdapter(themeAdapter);
            } else {
                themesLayout.setVisibility(View.GONE);
            }

            if (game.getGame_modes() != null) {
                ArrayList<String> gameModes = getStrings(game.getGame_modes());
                gameModesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ThemeAdapter gameModesAdapter = new ThemeAdapter(this, gameModes);
                gameModesRecyclerView.setAdapter(gameModesAdapter);
            } else {
                gameModesLayout.setVisibility(View.GONE);
            }

            if (game.getScreenshots() != null){
                ArrayList<GameImage> images = game.getScreenshots();
                if (game.getArtworks() != null) {
                    images.addAll(game.getArtworks());
                }

                screenshotsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ScreenshotAdapter screenshotAdapter = new ScreenshotAdapter(this, images, GameActivity.this);
                screenshotsRecyclerView.setAdapter(screenshotAdapter);
            } else {
                screenshotsText.setVisibility(View.GONE);
                screenshotsRecyclerView.setVisibility(View.GONE);
            }

            /*if (game.getVideos() != null){
                videosRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ScreenshotAdapter screenshotAdapter = new ScreenshotAdapter(this, game.getScreenshots(), GameActivity.this);
                videosRecyclerView.setAdapter(screenshotAdapter);
            } else {
                videosText.setVisibility(View.GONE);
                videosRecyclerView.setVisibility(View.GONE);
            }*/
        }
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
        if(phrases != null) {
            for (GamePhrase phrase : phrases) {
                strings.add(phrase.getName());
            }
        }
        return strings;
    }

    public ArrayList<String> getPlatforms(ArrayList<Platform> platforms){
        ArrayList<String> strings = new ArrayList<>();
        if (platforms != null) {
            for (Platform platform : platforms) {
                strings.add(platform.getName());
            }
        }
        return strings;
    }

    @Override
    public void onScreenshotClick(final int position) {
        new StfalconImageViewer.Builder<>(this, game.getScreenshots(), new ImageLoader<GameImage>() {
            @Override
            public void loadImage(ImageView imageView, GameImage screenshot) {
                imageView.setBackgroundColor(getResources().getColor(R.color.text_black));
                Glide
                        .with(getApplicationContext())
                        .load(getString(R.string.screenshot_url) + screenshot.getImage_id() + ".jpg")
                        .into(imageView);
            }
        }).withStartPosition(position)
                .withImageMarginPixels(30)
                .show();
    }
}
