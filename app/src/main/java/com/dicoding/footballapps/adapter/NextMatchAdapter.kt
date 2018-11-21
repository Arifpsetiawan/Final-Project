package com.dicoding.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dicoding.footballapps.R
import com.dicoding.footballapps.model.MatchItem
import org.jetbrains.anko.find
import java.text.SimpleDateFormat
import java.util.*

class NextMatchAdapter (
    private val dataItems: MutableList<MatchItem>,
    private val listener: NextMatchFragment.OnFragmentInteractionListener?)
    : RecyclerView.Adapter<NextMatchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_match, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(dataItems[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val mDateEvent  : TextView = view.find(R.id.dateMatch)
        val mHomeTeam   : TextView = view.find(R.id.homeTeam)
        val mAwayTeam   : TextView = view.find(R.id.awayTeam)
        val mHomeScore  : TextView = view.find(R.id.homeScore)
        val mAwayScore  : TextView = view.find(R.id.awayScore)
        val mTime       : TextView = view.find(R.id.timeMatch)

        fun bindItem(item: MatchItem, listener: NextMatchFragment.OnFragmentInteractionListener?) {

            val timeconvert = toGMTFormat(item.dateEvent,item.strTime)
            val formatDate = SimpleDateFormat("E, dd MM yyyy")
            val formatTime = SimpleDateFormat("HH:mm")
            val date = formatDate.format(timeconvert)
            val timeNew = formatTime.format(timeconvert)

            item.strTime
            item.dateEvent
            mDateEvent.text = "$date"
            mTime.text      = "$timeNew"
            mHomeTeam.text  = item.strHomeTeam
            mAwayTeam.text  = item.strAwayTeam
            mHomeScore.text = item.intHomeScore
            mAwayScore.text = item.intAwayScore

            itemView.setOnClickListener {
                listener?.onFragmentInteraction(item)
            }
        }
    }

}

private fun toGMTFormat(date: String?, time: String?): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return formatter.parse(dateTime)
}