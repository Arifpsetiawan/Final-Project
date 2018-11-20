package com.dicoding.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.footballapps.R
import com.dicoding.footballapps.model.TeamItem
import kotlinx.android.synthetic.main.activity_team.view.*

class TeamAdapter (private val teamDataItem: List<TeamItem>,
                   private val listener: (TeamItem) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_team, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teamDataItem[position], listener)
    }


    override fun getItemCount(): Int = teamDataItem.size

}

class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(teamDataItem: TeamItem, listener: (TeamItem) -> Unit) {
        itemView.name_team.text = teamDataItem.strTeam
        Glide.with(itemView.context).load(teamDataItem.strTeamBadge).into(itemView.logo_team)

        itemView.setOnClickListener {
            listener(teamDataItem)
        }
    }
}