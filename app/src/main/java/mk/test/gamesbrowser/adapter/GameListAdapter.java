package mk.test.gamesbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.model.Game;

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

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_game, parent, false);
        return new GameListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder holder, int position) {
        Game game = games.get(position);

        Glide
                .with(context)
                .load("https:" + game.getCover().getUrl())
                .centerCrop()
                .placeholder(context.getResources().getDrawable(R.drawable.ic_home))
                .into(holder.gameCover);

        holder.gameTitle.setText(game.getName());
        holder.gameYear.setText(game.getFirst_release_date());
        holder.gamePublisher.setText("Publisher");//TODO Publisher
        holder.gameRating.setText(game.getRating() + "");
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class GameListViewHolder extends RecyclerView.ViewHolder {

        private ImageView gameCover;
        private TextView gameTitle, gameYear, gamePublisher, gameRating;

        public GameListViewHolder(@NonNull View itemView) {
            super(itemView);

            gameCover = itemView.findViewById(R.id.game_cover);
            gameTitle = itemView.findViewById(R.id.game_title);
            gameYear = itemView.findViewById(R.id.game_release_year);
            gamePublisher = itemView.findViewById(R.id.game_publisher);
            gameRating = itemView.findViewById(R.id.game_rating);
        }
    }
}
