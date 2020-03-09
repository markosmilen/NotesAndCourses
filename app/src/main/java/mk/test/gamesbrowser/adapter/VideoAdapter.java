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
import mk.test.gamesbrowser.interfaces.VideoClickInterface;
import mk.test.gamesbrowser.model.GameVideo;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private ArrayList<GameVideo> videos;
    private LayoutInflater inflater;
    private VideoClickInterface clickInterface;

    public VideoAdapter(Context context, VideoClickInterface clickInterface){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.clickInterface = clickInterface;
    }

    public void setVideos(ArrayList<GameVideo> videos){
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        GameVideo video = videos.get(position);

        holder.videoName.setText(video.getName());

        String videoThumbnail = "https://img.youtube.com/vi/" + video.getVideo_id() + "/mqdefault.jpg";

        Glide
                .with(context)
                .load(videoThumbnail)
                .placeholder(R.drawable.placeholderhoriz)
                .into(holder.videoThumbnail);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private TextView videoName;
        private ImageView videoThumbnail;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoName = itemView.findViewById(R.id.video_name);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.onVideoClick(videos.get(getAdapterPosition()));
                }
            });
        }
    }
}
