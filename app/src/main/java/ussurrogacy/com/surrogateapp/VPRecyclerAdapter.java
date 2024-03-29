package ussurrogacy.com.surrogateapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * RecyclerAdapter for ViewProfile. Populates the recyclerView with cardViews
 * of an individual surrogate's information that is not displayed at the top
 * of the ViewProfile activity  fragment.
 * Created by Tom on 3/21/2018.
 */

public class VPRecyclerAdapter extends RecyclerView.Adapter<VPRecyclerAdapter.MyViewHolder> {
    private Profile profile;
    private List <String> mKeys;
    private LayoutInflater mInflater;

    VPRecyclerAdapter(Context context, Profile profile, List<String> keys){
        this.mInflater = LayoutInflater.from(context);
        this.profile = profile;
        mKeys = keys;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_cards, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder" + position);

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mKeys.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout profileLayout;
        TextView answer, key;
        ImageView imgThumb;
        int position;

        MyViewHolder(View itemView){
            super(itemView);
            profileLayout = itemView.findViewById(R.id.profile_layout);
            answer = (TextView) itemView.findViewById(R.id.tvAnswer);
            key = (TextView) itemView.findViewById(R.id.tvKey);
            imgThumb = (ImageView) itemView.findViewById(R.id.img_row);
        }

        public void setData(int position){
            //this.key.setText(profile.getKey(position));
            this.answer.setText(profile.getData(key.toString()));
            //this.imgThumb.setImageResource();
            this.position = position;
        }
    }
}
