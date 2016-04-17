package poker.fivecardstats.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import poker.fivecardstats.R;
import poker.fivecardstats.model.Score;
import poker.fivecardstats.model.User;

/**
 * Created by Victor on 2016-03-28.
 */
public class ScoreAdapter extends ArrayAdapter<Score> {
    private static class ViewHolder {
        TextView mType;
        TextView mPoints;
    }

    public ScoreAdapter(Context context, List<Score> scores) {
        super(context, R.layout.score_list, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Score score = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.score_list, parent, false);
            viewHolder.mType = (TextView) convertView.findViewById(R.id.type_textview);
            viewHolder.mPoints = (TextView) convertView.findViewById(R.id.point_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.mType.setText(score.getType());
        viewHolder.mPoints.setText(score.getScore() + "p");
        // Return the completed view to render on screen
        return convertView;
    }
}
