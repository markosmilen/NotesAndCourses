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

import de.hdodenhof.circleimageview.CircleImageView;
import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.interfaces.GenreClickInterface;
import mk.test.gamesbrowser.model.GamePhrase;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<GamePhrase> genres = new ArrayList<>();
    private GenreClickInterface clickInterface;

    public GenreAdapter(Context context, GenreClickInterface clickInterface){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.clickInterface = clickInterface;
    }

    public void setGenres(ArrayList<GamePhrase> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        GamePhrase genre = genres.get(position);

        holder.genreLogo.setImageResource(genre.getLogo());

        holder.genreName.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }


    public class GenreViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView genreLogo;
        private TextView genreName;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);

            genreLogo = itemView.findViewById(R.id.genre_logo);
            genreName = itemView.findViewById(R.id.genre_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.onGenreClick(genres.get(getAdapterPosition()));
                }
            });
        }
    }
}
