package mk.test.gamesbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        if (!review.getTitle().equals("c")) {
            holder.title.setText(review.getTitle());
        }else {
            holder.title.setVisibility(View.GONE);
        }

        holder.content.setText(review.getContent());

        if (review.getPositive_points() != null && review.getNegative_points() != null) {
            holder.positive_points.setText(review.getPositive_points());
            holder.negative_points.setText(review.getNegative_points());
        }else {
            holder.pointsLayout.setVisibility(View.GONE);
        }

        if (review.getConclusion() != null) {
            holder.conclusion.setText(review.getConclusion());
        }else {
            holder.conclusion.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView title, content, positive_points, negative_points, conclusion;
        private LinearLayout pointsLayout;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.review_title);
            content = itemView.findViewById(R.id.review_content);
            positive_points = itemView.findViewById(R.id.positive_points);
            negative_points = itemView.findViewById(R.id.negative_points);
            conclusion = itemView.findViewById(R.id.review_conclusion);

            pointsLayout = itemView.findViewById(R.id.review_points_layout);
        }
    }
}
