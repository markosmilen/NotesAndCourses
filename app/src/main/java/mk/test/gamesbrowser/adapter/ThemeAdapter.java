package mk.test.gamesbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mk.test.gamesbrowser.R;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> themes;

    public ThemeAdapter(Context context, ArrayList<String> data){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.themes = data;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_theme, parent, false);
        return new ThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        String theme = themes.get(position);
        holder.themeName.setText(theme);
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder {

        private TextView themeName;

        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);

            themeName = itemView.findViewById(R.id.theme_name);
        }
    }
}
