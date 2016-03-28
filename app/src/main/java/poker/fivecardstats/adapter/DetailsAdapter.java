package poker.fivecardstats.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import poker.fivecardstats.R;
import poker.fivecardstats.model.Detail;

/**
 * Created by Victor on 2016-03-28.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private ArrayList<Detail> mDetails;

    /**
     *  Provide a reference to the views for each data item
     *  Complex data items may need more than one view per item, and
     *  you provide access to all the views for a data item in a view holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvType;
        public TextView mTvPoint;

        public ViewHolder(View v, TextView name, TextView point) {
            super(v);
            this.mTvType = name;
            this.mTvPoint = point;
        }
    }

    public DetailsAdapter(ArrayList<Detail> details) {
        this.mDetails = details;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TextView mTvType = (TextView) v.findViewById(R.id.tv_type);
        TextView mTvPoint = (TextView) v.findViewById(R.id.tv_points);

        return new ViewHolder(v, mTvType, mTvPoint);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Detail detail = mDetails.get(position);

        holder.mTvType.setText(detail.getType());
        holder.mTvPoint.setText(Integer.toString(detail.getPoint()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDetails.size();
    }
}
