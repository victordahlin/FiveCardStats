package poker.fivecardstats.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import poker.fivecardstats.R;
import poker.fivecardstats.model.User;

/**
 * Created by Victor on 2016-03-28.
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private ArrayList<User> mUsers;

    /**
     *  Provide a reference to the views for each data item
     *  Complex data items may need more than one view per item, and
     *  you provide access to all the views for a data item in a view holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        public TextView mTvPoints;

        public ViewHolder(View v, TextView mTvName, TextView mTvPoints) {
            super(v);
            this.mTvName = mTvName;
            this.mTvPoints = mTvPoints;
        }
    }

    public ScoreAdapter(ArrayList<User> users) {
        this.mUsers = users;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list, parent, false);
        // Lookup view for data population
        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
        TextView tvPoints = (TextView) v.findViewById(R.id.tv_points);

        return new ViewHolder(v, tvName, tvPoints);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);

        holder.mTvName.setText(user.getName());
        holder.mTvPoints.setText(user.getPoints() + "p");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
