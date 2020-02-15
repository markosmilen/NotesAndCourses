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

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Game> games;
    private GameClickInterface clickInterface;

    public GameAdapter(Context context, ArrayList<Game> games, GameClickInterface clickInterface){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.games = games;
        this.clickInterface = clickInterface;
    }

    public void setGames (ArrayList<Game> games){
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_game_horizontal, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);

        if (game.getCover().getUrl() != null) {
            Glide
                    .with(context)
                    .load("https:" + game.getCover().getUrl())
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_home))
                    .into(holder.gameCover);
        }
        holder.gameName.setText(game.getName());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {

        private ImageView gameCover;
        private TextView gameName;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameCover = itemView.findViewById(R.id.game_cover);
            gameName = itemView.findViewById(R.id.game_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.onGameClick(games.get(getAdapterPosition()));
                }
            });
        }
    }
}