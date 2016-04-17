package poker.fivecardstats.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import poker.fivecardstats.R;
import poker.fivecardstats.model.User;

/**
 * Created by Victor on 2016-03-28.
 */
public class UsersAdapter extends ArrayAdapter<User> {
    private static class ViewHolder {
        TextView mName;
        TextView mScore;
    }

    public UsersAdapter(Context context, List<User> users) {
        super(context, R.layout.user_list, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_list, parent, false);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.mScore = (TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.mName.setText(user.getName());
        viewHolder.mScore.setText(user.getPoints() + "p");
        // Return the completed view to render on screen
        return convertView;
    }
}
