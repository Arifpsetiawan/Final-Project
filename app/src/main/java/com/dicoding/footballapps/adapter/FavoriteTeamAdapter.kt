package com.dicoding.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.footballapps.R
import com.dicoding.footballapps.model.TeamItem
import kotlinx.android.synthetic.main.activity_team.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamAdapter (private val favoriteTeam: List<TeamItem>,
                           private val listener: (TeamItem) -> Unit)
    : RecyclerView.Adapter<ViewHolderFavTeam>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFavTeam {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_team, parent, false)
        return ViewHolderFavTeam(view)
    }

    override fun getItemCount(): Int = favoriteTeam.size

    override fun onBindViewHolder(holder: ViewHolderFavTeam, position: Int) {
        holder.bindItem(favoriteTeam[position], listener)
    }

}

class ViewHolderFavTeam(view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(itemFavoriteTeam: TeamItem, listener: (TeamItem) -> Unit) {
        itemView.name_team.text = itemFavoriteTeam.strTeam
        Glide.with(itemView.context).load(itemFavoriteTeam.strTeamBadge).into(itemView.logo_team)

        itemView.onClick { listener(itemFavoriteTeam) }
    }
}