package com.dicoding.footballapps.activity.activityDetail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dicoding.footballapps.R
import com.dicoding.footballapps.adapter.TeamDetailAdapter
import com.dicoding.footballapps.database.TeamFavDB
import com.dicoding.footballapps.database.database
import com.dicoding.footballapps.model.TeamItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class ActivityDetailTeam : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var teamData: TeamItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        teamData = intent.getParcelableExtra("team_data")

        favoriteState()

        Picasso.get().load(teamData.strTeamBadge).into(logo_team_detail)
        team_year_detail.text = teamData.intFormedYear
        team_stadium_detail.text = teamData.strStadium

        if (teamData.strStadiumThumb.isNullOrEmpty()){
            img_background_detail.setImageResource(R.drawable.img_background)
        } else {
            Picasso.get().load(teamData.strStadiumThumb).into(img_background_detail)
        }

        viewPager_team_detail.adapter = TeamDetailAdapter(supportFragmentManager, teamData.strDescriptionEN, teamData.idTeam)
        tabs_team_detail.setupWithViewPager(viewPager_team_detail)

        setSupportActionBar(toolbar_team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = teamData.strTeam
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            android.R.id.home -> {
                super.onBackPressed()
                true }

            R.id.add_to_favorite -> {
                if (isFavorite)  removeToFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(TeamFavDB.TABLE_FAVORITE_TEAM,
                    TeamFavDB.ID_TEAM to teamData.idTeam,
                    TeamFavDB.NAME_TEAM to teamData.strTeam,
                    TeamFavDB.IMAGE_TEAM to teamData.strTeamBadge,
                    TeamFavDB.YEAR_TEAM to teamData.intFormedYear,
                    TeamFavDB.STADIUM_TEAM to teamData.strStadium,
                    TeamFavDB.STADIUM_THUMB to teamData.strStadiumThumb,
                    TeamFavDB.FANART1_TEAM to teamData.strTeamFanart1,
                    TeamFavDB.FANART2_TEAM to teamData.strTeamFanart2,
                    TeamFavDB.DESC_TEAM to teamData.strDescriptionEN)


            }
            snackbar(detail_activity, "Added to Your Favorite Team").show()
        }catch (e: SQLiteConstraintException){
            snackbar(detail_activity, e.localizedMessage).show()
        }
    }

    private fun removeToFavorite() {
        try {
            database.use {
                delete(TeamFavDB.TABLE_FAVORITE_TEAM, "( ID_TEAM = {id_team} )",
                    "id_team" to teamData.idTeam.toString())
            }
            snackbar(detail_activity, "Remove From Favorite Team").show()
        }catch (e: SQLiteConstraintException){
            snackbar(detail_activity, e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(TeamFavDB.TABLE_FAVORITE_TEAM)
                .whereArgs("( ID_TEAM = {id_team})",
                    "id_team" to teamData.idTeam.toString() )
            val favoriteTeam = result.parseList(classParser<TeamItem>())
            if (!favoriteTeam.isEmpty()) isFavorite = true
        }
    }
}