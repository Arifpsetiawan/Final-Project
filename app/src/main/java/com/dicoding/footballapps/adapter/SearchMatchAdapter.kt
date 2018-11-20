package com.dicoding.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.footballapps.R
import com.dicoding.footballapps.model.EventItem
import kotlinx.android.synthetic.main.activity_match.view.*
import java.text.SimpleDateFormat

class SearchMatchAdapter(private var eventDataItem: List<EventItem>
                         , private val listener: (EventItem) -> Unit)
    : RecyclerView.Adapter<ViewHolderSearch>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSearch {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_match, parent, false)
        return ViewHolderSearch(view)
    }

    override fun getItemCount(): Int = eventDataItem.size

    override fun onBindViewHolder(holder: ViewHolderSearch, position: Int) {
        holder.bindItem(eventDataItem[position], listener)
    }

}

class ViewHolderSearch(val view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(eventData: EventItem, listener: (EventItem) -> Unit){

        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val formatGMT = SimpleDateFormat("E, dd MMM yyyy")
        val dateParse = formatDate.parse(eventData.dateEvent)
        val dateEvent = formatGMT.format(dateParse)

        itemView.dateMatch.text = dateEvent
        itemView.timeMatch.text = eventData.strTime
        itemView.homeTeam.text = eventData.strHomeTeam
        itemView.awayTeam.text = eventData.strAwayTeam
        itemView.homeScore.text = eventData.intHomeScore
        itemView.awayScore.text = eventData.intAwayScore

        itemView.setOnClickListener {
            listener(eventData)
        }
    }
}