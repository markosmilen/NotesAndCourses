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
import mk.test.gamesbrowser.model.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Review> reviews;

    public ReviewsAdapter(Context context, ArrayList<Review> reviews){
        this.inflater = LayoutInflater.from(context);
        this.reviews = reviews;
    }

    public void setReviews(ArrayList<Review> reviews){
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);

        if (review.getContent() != null) {
            holder.positive_points.setText(review.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView positive_points;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            positive_points = itemView.findViewById(R.id.positive_points);
        }
    }
}
