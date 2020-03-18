package mk.test.gamesbrowser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.adapter.GameAdapter;
import mk.test.gamesbrowser.adapter.ScreenshotAdapter;
import mk.test.gamesbrowser.adapter.ThemeAdapter;
import mk.test.gamesbrowser.adapter.VideoAdapter;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.interfaces.ScreenshotClickInterface;
import mk.test.gamesbrowser.interfaces.VideoClickInterface;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.model.GameImage;
import mk.test.gamesbrowser.model.GamePhrase;
import mk.test.gamesbrowser.model.GameVideo;
import mk.test.gamesbrowser.model.InvolvedCompany;
import mk.test.gamesbrowser.model.Platform;
import mk.test.gamesbrowser.viewmodel.GameViewModel;

public class GameActivity extends AppCompatActivity implements ScreenshotClickInterface, VideoClickInterface, GameClickInterface {

    private Game game;
    private GameViewModel gameViewModel;
    private ImageButton wantGame, playingGame, playedGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView topImage, gameCoverAct;
        TextView gameTitle, gamePublisher, gameReleaseYear, gameRating, gameRatingCount, gameDescription;
        TextView screenshotsText, videosText, similarGamesText;
        RecyclerView genresRecyclerView, platformsRecyclerView, themesRecyclerView, gameModesRecyclerView, perspectivesRecyclerView;
        RecyclerView screenshotsRecyclerView, videosRecyclerView, similarGamesRecyclerView;
        LinearLayout genresLayout, themesLayout, gameModesLayout;

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        similarGamesText = findViewById(R.id.similar_games_text);
        similarGamesRecyclerView = findViewById(R.id.similar_games_recycler_view);

        genresLayout = findViewById(R.id.layout_genres);
        gameModesLayout = findViewById(R.id.layout_game_modes);
        themesLayout = findViewById(R.id.layout_themes);

        wantGame = findViewById(R.id.add_want);
        playingGame = findViewById(R.id.add_playing);
        playedGame = findViewById(R.id.add_played);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        game = getIntent().getParcelableExtra("game");

        getSupportActionBar().setTitle(game.getName());

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

            //BUTTONS
            if (game.isWanted()) wantGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_added));
            if (game.isPlaying()) playingGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_added));
            if (game.isPlayed()) playedGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_added));

            gameDescription.setText(game.getSummary());

            ArrayList<String> platforms = getPlatforms(game.getPlatforms());
            platformsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            ThemeAdapter platformsAdapter = new ThemeAdapter(this);
            platformsAdapter.setThemes(platforms);
            platformsRecyclerView.setAdapter(platformsAdapter);

            ArrayList<String> gamePerspectives = getStrings(game.getPlayer_perspectives());
            perspectivesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            ThemeAdapter perspectivesAdapter = new ThemeAdapter(this);
            perspectivesAdapter.setThemes(gamePerspectives);
            perspectivesRecyclerView.setAdapter(perspectivesAdapter);
            perspectivesRecyclerView.setVisibility(View.GONE);//TO BE PLACED ELSEWHERE

            if (game.getGenres() != null) {
                ArrayList<String> genres = getStrings(game.getGenres());
                genresRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ThemeAdapter genresAdapter = new ThemeAdapter(this);
                genresAdapter.setThemes(genres);
                genresRecyclerView.setAdapter(genresAdapter);
            } else {
                genresLayout.setVisibility(View.GONE);
            }

            if (game.getThemes() != null) {
                ArrayList<String> themes = getStrings(game.getThemes());
                themesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ThemeAdapter themeAdapter = new ThemeAdapter(this);
                themeAdapter.setThemes(themes);
                themesRecyclerView.setAdapter(themeAdapter);
            } else {
                themesLayout.setVisibility(View.GONE);
            }

            if (game.getGame_modes() != null) {
                ArrayList<String> gameModes = getStrings(game.getGame_modes());
                gameModesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                ThemeAdapter gameModesAdapter = new ThemeAdapter(this);
                gameModesAdapter.setThemes(gameModes);
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
                ScreenshotAdapter screenshotAdapter = new ScreenshotAdapter(this, GameActivity.this);
                screenshotAdapter.setScreenshots(game.getScreenshots());
                screenshotsRecyclerView.setAdapter(screenshotAdapter);
            } else {
                screenshotsText.setVisibility(View.GONE);
                screenshotsRecyclerView.setVisibility(View.GONE);
            }

            if (game.getVideos() != null){
                videosRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                VideoAdapter videoAdapter = new VideoAdapter(this, this);
                videoAdapter.setVideos(game.getVideos());
                videosRecyclerView.setAdapter(videoAdapter);
            } else {
                videosText.setVisibility(View.GONE);
                videosRecyclerView.setVisibility(View.GONE);
            }

            if (game.getSimilar_games() != null){
                similarGamesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                GameAdapter similarGamesAdapter = new GameAdapter(this, this);
                similarGamesAdapter.setGames(game.getSimilar_games());
                similarGamesRecyclerView.setAdapter(similarGamesAdapter);
            }else {
                similarGamesText.setVisibility(View.GONE);
                similarGamesRecyclerView.setVisibility(View.GONE);
            }
        }
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

    public void onAddButtonClick(View view) {
        String buttonPressed="";
        switch(view.getId()){
            case R.id.add_want:
                if (!game.isWanted()) {
                    game.setWanted(true);
                    wantGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_added));
                    buttonPressed = "WANT";
                }else {
                    game.setWanted(false);
                    wantGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                    buttonPressed = "WANT";
                }
                break;
            case R.id.add_playing:
                if (!game.isPlaying()) {
                    game.setPlaying(true);
                    playingGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_added));
                    buttonPressed = "PLAYING";
                }else {
                    game.setPlaying(false);
                    playingGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                    buttonPressed = "PLAYING";
                }
                break;
            case R.id.add_played:
                if (!game.isPlayed()) {
                    game.setPlayed(true);
                    playedGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_added));
                    buttonPressed = "PLAYED";
                }else {
                    game.setPlayed(false);
                    playedGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                    buttonPressed = "PLAYED";
                }
                break;
        }
        gameViewModel.insert(game);
        Toast.makeText(this, game.getName() + " added to " + buttonPressed, Toast.LENGTH_SHORT).show();
    }

    public void onReviewsClick(View view) {
        Intent intent = new Intent(this, ReviewsActivity.class);
        intent.putExtra("game_id", game.getId());
        intent.putExtra("game_name", game.getName());
        startActivity(intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVideoClick(GameVideo video) {
        String videoId = video.getVideo_id();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }

    @Override
    public void onGameClick(Game game) {
        Toast.makeText(this, game.getName(), Toast.LENGTH_SHORT).show();
    }
}
