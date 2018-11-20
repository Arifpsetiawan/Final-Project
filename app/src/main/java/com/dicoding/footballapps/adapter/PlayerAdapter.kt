package com.dicoding.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.footballapps.R
import com.dicoding.footballapps.model.PlayerItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player.view.*

class PlayerAdapter (private val playerDataItem: List<PlayerItem>,
                     private val listener: (PlayerItem) -> Unit)
    : RecyclerView.Adapter<ViewHolderPlayer>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPlayer {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_player, parent, false)
        return ViewHolderPlayer(view)
    }

    override fun getItemCount(): Int = playerDataItem.size


    override fun onBindViewHolder(holder: ViewHolderPlayer, position: Int) {
        holder.bindItem(playerDataItem[position], listener)
    }

}

class ViewHolderPlayer(val view: View): RecyclerView.ViewHolder(view) {

    fun bindItem (playerDataItem: PlayerItem, listener: (PlayerItem) -> Unit) {
        itemView.player_name.text = playerDataItem.strPlayer
        itemView.player_position.text = playerDataItem.strPosition
        if(playerDataItem.strCutout.isNullOrEmpty()) {
            itemView.img_player.setImageResource(R.drawable.player)
        } else {
            Picasso.get().load(playerDataItem.strCutout).into(itemView.img_player)
        }

        itemView.setOnClickListener {
            listener(playerDataItem)
        }
    }
}