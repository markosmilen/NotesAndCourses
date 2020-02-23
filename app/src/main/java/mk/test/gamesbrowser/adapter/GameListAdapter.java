package mk.test.gamesbrowser.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.model.Platform;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Game> games;
    private GameClickInterface gameClickInterface;

    public GameListAdapter(Context context, ArrayList<Game> games, GameClickInterface clickInterface){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.games = games;
        this.gameClickInterface = clickInterface;
    }

    public void setGames (ArrayList<Game> games){
        this.games = games;
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_game, parent, false);
        return new GameListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder holder, int position) {
        Game game = games.get(position);

        if (game.getCover() != null) {
            Glide
                    .with(context)
                    .load(context.getString(R.string.cover_url) + game.getCover().getImage_id() + ".jpg")
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.placeholder))
                    .into(holder.gameCover);
        }
        holder.gameTitle.setText(game.getName());

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String dateString = formatter.format((new Date((long) game.getFirst_release_date() * 1000)));
        holder.gameYear.setText(dateString);

        if (game.getGame_engines() != null) {
            holder.gamePublisher.setText(game.getGame_engines().get(0).getName());
        }

        if (game.getRating() != 0) {
            holder.gameRating.setText((int) game.getRating() + "");
        }else {
            holder.gameRating.setText(context.getString(R.string.not_applicable));
        }

        ArrayList<String> platforms = getPlatforms(game.getPlatforms());
        ThemeAdapter adapter = new ThemeAdapter(context, platforms);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return games.size();
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

    public class GameListViewHolder extends RecyclerView.ViewHolder {

        private ImageView gameCover;
        private TextView gameTitle, gameYear, gamePublisher, gameRating;
        private RecyclerView recyclerView;

        public GameListViewHolder(@NonNull View itemView) {
            super(itemView);

            gameCover = itemView.findViewById(R.id.game_cover);
            gameTitle = itemView.findViewById(R.id.game_title);
            gameYear = itemView.findViewById(R.id.game_release_year);
            gamePublisher = itemView.findViewById(R.id.game_publisher);
            gameRating = itemView.findViewById(R.id.game_rating);
            recyclerView = itemView.findViewById(R.id.rv_platforms);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameClickInterface.onGameClick(games.get(getAdapterPosition()));
                }
            });
        }
    }
}
