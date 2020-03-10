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
import java.util.List;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.interfaces.PlatformClickInterface;
import mk.test.gamesbrowser.model.Platform;

public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.PlatformViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Platform> platforms;
    private PlatformClickInterface clickInterface;

    public PlatformAdapter(Context context, PlatformClickInterface clickInterface){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.clickInterface = clickInterface;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    @NonNull
    @Override
    public PlatformViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_platform, parent, false);
        return new PlatformViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformViewHolder holder, int position) {
        Platform platform = platforms.get(position);
        holder.platformName.setText(platform.getName());

        if (platform.getPlatform_logo() != null) {
            Glide
                    .with(context)
                    .load(platform.getPlatform_logo())
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.placeholder))
                    .into(holder.platformLogo);
        }

    }

    @Override
    public int getItemCount() {
        return platforms.size();
    }

    public class PlatformViewHolder extends RecyclerView.ViewHolder {

        private ImageView platformLogo;
        private TextView platformName;

        public PlatformViewHolder(@NonNull View itemView) {
            super(itemView);

            platformLogo = itemView.findViewById(R.id.platform_logo);
            platformName = itemView.findViewById(R.id.platform_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.onPlatformClick(platforms.get(getAdapterPosition()));
                }
            });
        }
    }
}
