package com.dicoding.footballapps.activity.activityDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.dicoding.footballapps.R
import com.dicoding.footballapps.model.PlayerItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class ActivityDetailPlayer : AppCompatActivity() {

    private lateinit var playerData : PlayerItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        playerData = intent.getSerializableExtra("player_data") as PlayerItem


        if(playerData.strFanart1.isNullOrEmpty()){
            player_banner_detail.setImageResource(R.drawable.img_background)
        } else {
            Picasso.get().load(playerData.strFanart1).into(player_banner_detail)
        }

        if(playerData.strWeight.isNullOrEmpty()) {
            player_weight_detail.setText(" - ")
        } else {
            player_weight_detail.text = playerData.strWeight
        }

        if(playerData.strHeight.isNullOrEmpty()){
            player_height_detail.setText(" - ")
        } else {
            player_height_detail.text = playerData.strHeight
        }

        player_position_detail.text = playerData.strPosition
        player_description_detail.text = playerData.strDescriptionEN

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = playerData.strPlayer

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}