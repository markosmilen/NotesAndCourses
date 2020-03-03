package mk.test.gamesbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.interfaces.ScreenshotClickInterface;
import mk.test.gamesbrowser.model.GameImage;

public class ScreenshotAdapter extends RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder> {

    private ArrayList<GameImage> screenshots;
    private Context context;
    private LayoutInflater inflater;
    private ScreenshotClickInterface clickInterface;

    public ScreenshotAdapter(Context context, ArrayList<GameImage> screenshots, ScreenshotClickInterface clickInterface) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.screenshots = screenshots;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_screenshot, parent, false);
        return new ScreenshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        GameImage screenshot = screenshots.get(position);

        if (screenshot.getImage_id() != null){
            Glide
                    .with(context)
                    .load(context.getResources().getString(R.string.screenshot_url) + screenshot.getImage_id() + ".jpg")
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.placeholderhoriz))
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return screenshots.size();
    }

    public class ScreenshotViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.screenshot_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.onScreenshotClick(getAdapterPosition());
                }
            });
        }
    }
}
