package com.srikar.showboxrxjava.adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.srikar.showboxrxjava.R;
import com.srikar.showboxrxjava.models.Cast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.srikar.showboxrxjava.config.ConfigURL.POSTER_PATH;


public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.CastViewHolder> {

    Context context;
    private List<Cast> mCastList;
    private LayoutInflater mInflater;

    public CastListAdapter(Context context, List<Cast> mMovieList) {
        this.context = context;
        this.mCastList = mMovieList;
        mInflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.cast_list_item,parent,false);
        return new CastViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        String profile = POSTER_PATH + mCastList.get(position).getProfilePath();
        String profileName = mCastList.get(position).getName();
        String charaterName = mCastList.get(position).getCharacter();

        Picasso.with(context).load(profile)
                .placeholder(R.drawable.ic_action_placeholder_white)
                .into(holder.mCastThumbnail);

        holder.mCastName.setText(profileName);
        holder.mCastName.setSelected(true);
        holder.mCharaterName.setText(charaterName);
        holder.mCharaterName.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return mCastList.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cast_thumbnail)
        ImageView mCastThumbnail;
        @BindView(R.id.cast_name_tv)
        TextView mCastName;
        @BindView(R.id.charater_tv)
        TextView mCharaterName;

        public CastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
