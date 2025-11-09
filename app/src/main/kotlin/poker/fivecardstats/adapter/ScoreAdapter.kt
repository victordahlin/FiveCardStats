package poker.fivecardstats.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import poker.fivecardstats.R
import poker.fivecardstats.model.Score

class ScoreAdapter(context: Context, scores: List<Score>) : ArrayAdapter<Score>(context, R.layout.score_list, scores) {

    private class ViewHolder {
        lateinit var mType: TextView
        lateinit var mPoints: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val score = getItem(position)
        val viewHolder: ViewHolder
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.score_list, parent, false)
            viewHolder.mType = view.findViewById(R.id.type_textview)
            viewHolder.mPoints = view.findViewById(R.id.point_textview)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.mType.text = score?.type
        viewHolder.mPoints.text = "${score?.score}p"

        return view!!
    }
}