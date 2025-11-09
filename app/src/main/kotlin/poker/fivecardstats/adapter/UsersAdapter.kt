package poker.fivecardstats.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import poker.fivecardstats.R
import poker.fivecardstats.model.User

class UsersAdapter(context: Context, users: List<User>) : ArrayAdapter<User>(context, R.layout.user_list, users) {

    private class ViewHolder {
        lateinit var mName: TextView
        lateinit var mScore: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val user = getItem(position)
        val viewHolder: ViewHolder

        if (view == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.user_list, parent, false)
            viewHolder.mName = view.findViewById(R.id.tv_name)
            viewHolder.mScore = view.findViewById(R.id.tv_score)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.mName.text = user?.name
        viewHolder.mScore.text = "${user?.points}p"

        return view!!
    }
}