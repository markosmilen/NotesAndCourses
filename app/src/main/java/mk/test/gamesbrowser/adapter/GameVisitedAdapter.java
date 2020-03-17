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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.interfaces.VisitedGameInterface;
import mk.test.gamesbrowser.model.Game;

public class GameVisitedAdapter extends RecyclerView.Adapter<GameVisitedAdapter.GameVisitedViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Game> games;

    private VisitedGameInterface visitedGameInterface;

    public GameVisitedAdapter(Context context, VisitedGameInterface visitedGameInterface){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.visitedGameInterface = visitedGameInterface;
    }

    public void setGames(List<Game> games){
        this.games = games;
    }

    @NonNull
    @Override
    public GameVisitedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_game_visited, parent, false);
        return new GameVisitedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameVisitedViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class GameVisitedViewHolder extends RecyclerView.ViewHolder {

        private ImageView gameCover, gameDeleteButton;
        private TextView gameTitle, gameYear;

        public GameVisitedViewHolder(@NonNull View itemView) {
            super(itemView);

            gameCover = itemView.findViewById(R.id.game_cover);
            gameTitle = itemView.findViewById(R.id.game_name);
            gameYear = itemView.findViewById(R.id.game_release_year);
            gameDeleteButton = itemView.findViewById(R.id.game_delete_button);

            gameDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visitedGameInterface.onGameDelete(games.get(getAdapterPosition()));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visitedGameInterface.onVisitedGameClick(games.get(getAdapterPosition()));
                }
            });
        }
    }
}
