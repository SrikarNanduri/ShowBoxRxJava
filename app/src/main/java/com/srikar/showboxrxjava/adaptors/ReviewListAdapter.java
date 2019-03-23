package com.srikar.showboxrxjava.adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srikar.showboxrxjava.R;
import com.srikar.showboxrxjava.models.ReviewsResults;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    Context context;
    private List<ReviewsResults> mReviewList;
    private LayoutInflater mInflater;

    public ReviewListAdapter(Context context, List<ReviewsResults> mReviewList) {
        this.context = context;
        this.mReviewList = mReviewList;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.review_list_item,parent,false);
        return new ReviewViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder holder, int position) {

        if(mReviewList.size() == 1){
            holder.borderView.setVisibility(View.GONE);
        } else if(position == 5){
            holder.borderView.setVisibility(View.GONE);
        }
        holder.mAuthor.setText(mReviewList.get(position).getAuthor());
        holder.mContent.setText(mReviewList.get(position).getContent());
        holder.mContent.setEllipsize(TextUtils.TruncateAt.END);
        holder.mContent.setOnClickListener(v -> {
            if(holder.mContent.getMaxLines() == 3) {
                holder.mContent.setEllipsize(null);
                holder.mContent.setMaxLines(100);
            } else {
                if(holder.mContent.getMaxLines() == 100){
                    holder.mContent.setEllipsize(TextUtils.TruncateAt.END);
                    holder.mContent.setMaxLines(3);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.author_tv)
        TextView mAuthor;
        @BindView(R.id.content_tv)
        TextView mContent;
        @BindView(R.id.view_reviews_list)
        View borderView;
    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
}
