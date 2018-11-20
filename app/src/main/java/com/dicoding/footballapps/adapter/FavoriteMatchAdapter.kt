package com.dicoding.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dicoding.footballapps.R
import com.dicoding.footballapps.database.MatchFavDB
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteMatchAdapter(
    private val favoriteMatch: List<MatchFavDB>,
    private val listener: (MatchFavDB) -> Unit)
    : RecyclerView.Adapter<FavMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMatchViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_match, parent, false)
        return FavMatchViewHolder(view)
    }

    override fun getItemCount(): Int = favoriteMatch.size


    override fun onBindViewHolder(holder: FavMatchViewHolder, position: Int) {
        holder.bindItem(favoriteMatch[position], listener)
    }

}

class FavMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val mDateEvent: TextView = view.find(R.id.dateMatch)
    val mHomeTeam: TextView = view.find(R.id.homeTeam)
    val mAwayTeam: TextView = view.find(R.id.awayTeam)
    val mHomeScore: TextView = view.find(R.id.homeScore)
    val mAwayScore: TextView = view.find(R.id.awayScore)
    val mTime: TextView = view.find(R.id.timeMatch)

    fun bindItem(itemFav: MatchFavDB, listener: (MatchFavDB) -> Unit) {
        mDateEvent.text = itemFav.dateEvent
        mHomeTeam.text = itemFav.homeTeam
        mAwayTeam.text = itemFav.awayTeam
        mHomeScore.text = itemFav.homeScore
        mAwayScore.text = itemFav.awayScore
        mTime.text = itemFav.time

        itemView.onClick { listener(itemFav) }
    }
}