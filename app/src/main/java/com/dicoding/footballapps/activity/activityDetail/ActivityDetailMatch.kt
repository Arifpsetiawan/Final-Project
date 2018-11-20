package com.dicoding.footballapps.activity.activityDetail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.dicoding.footballapps.R
import com.dicoding.footballapps.R.drawable.ic_add_to_favorites
import com.dicoding.footballapps.R.drawable.ic_added_to_favorites
import com.dicoding.footballapps.R.id.add_to_favorite
import com.dicoding.footballapps.R.menu.menu_favorite
import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.database.MatchFavDB
import com.dicoding.footballapps.database.database
import com.dicoding.footballapps.model.BadgeItem
import com.dicoding.footballapps.model.DetailMatchItem
import com.dicoding.footballapps.presenter.MatchDetailPresenter
import com.dicoding.footballapps.view.DetailMatchView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.SimpleDateFormat

class ActivityDetailMatch : AppCompatActivity(), DetailMatchView {

    private lateinit var detailPresenter: MatchDetailPresenter
    private lateinit var progressBar: ProgressBar

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var eventId : String
    private var teamHome : String? = null
    private var teamAway : String? = null
    private var scoreHome : String? = null
    private var scoreAway : String? = null
    private var matchDate : String? = null
    private var matchTime : String? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        val intent = intent

        eventId    = intent.getStringExtra("id_event")
        teamHome   = intent.getStringExtra("home_team")
        scoreHome  = intent.getStringExtra("home_score")
        teamAway   = intent.getStringExtra("away_team" )
        scoreAway  = intent.getStringExtra("away_score")
        matchDate  = intent.getStringExtra("date_event")
        matchTime  = intent.getStringExtra("time_event")

        progressBar = progressBar_detail

        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val formatGMT = SimpleDateFormat("E, dd MMM yyyy")
        val dateParse = formatDate.parse(matchDate)
        val dateEvent = formatGMT.format(dateParse)

        dateMatch_detail.text  = dateEvent
        timeMatch_detail.text = matchTime
        homeTeam_detail.text   = teamHome
        homeScore_detail.text  = scoreHome
        awayTeam_detail.text   = teamAway
        awayScore_detail.text  = scoreAway

        showProgress(progressBar)

        favoriteState()

        val apiReq = ApiRepository()
        val gson = Gson()
        detailPresenter = MatchDetailPresenter(this, apiReq, gson)

        detailPresenter.getMatchEventDetail(eventId)

        if(teamHome.equals(null) || teamAway.equals(null)) {
            teamHome = "Manchester City"
            teamAway = "Manchester United"}

        detailPresenter.getTeamBadge(teamHome, "Home")
        detailPresenter.getTeamBadge(teamAway, "Away")

    }

    override fun showProgress(progressBar: ProgressBar) {
        progressBar.visibility= View.VISIBLE
    }

    override fun hideProgress(progressBar: ProgressBar) {
        progressBar.visibility= View.GONE
    }

    override fun showDetailMatch(data: List<DetailMatchItem>) {

        val homeGoalDetail    = getDataList(data[0].strHomeGoalDetails)
        val homeShot           = getDataList(data[0].intHomeShots)
        val homeGoalKeeper     = getDataList(data[0].strHomeLineupGoalkeeper)
        val homeDefense        = getDataList(data[0].strHomeLineupDefense)
        val homeMidfield       = getDataList(data[0].strHomeLineupMidfield)
        val homeForward        = getDataList(data[0].strHomeLineupForward)
        val homeSubstitutes    = getDataList(data[0].strHomeLineupSubstitutes)

        setToTextDetails(homeGoalDetail, homeGoal_detail)
        setToTextDetails(homeShot, home_shot)
        setToTextDetails(homeGoalKeeper, home_goalkeeper)
        setToTextDetails(homeDefense, home_defense)
        setToTextDetails(homeMidfield, home_midfield)
        setToTextDetails(homeForward, home_forward)
        setToTextDetails(homeSubstitutes, home_substitutes)

        val awayGoalDetail    = getDataList(data[0].strAwayGoalDetails)
        val awayShot           = getDataList(data[0].intAwayShots)
        val awayGoalKeeper     = getDataList(data[0].strAwayLineupGoalkeeper)
        val awayDefense        = getDataList(data[0].strAwayLineupDefense)
        val awayMidfield       = getDataList(data[0].strAwayLineupMidfield)
        val awayForward        = getDataList(data[0].strAwayLineupForward)
        val awaySubstitutes    = getDataList(data[0].strAwayLineupSubstitutes)

        setToTextDetails(awayGoalDetail, awayGoal_detail)
        setToTextDetails(awayShot, away_shot)
        setToTextDetails(awayGoalKeeper, away_goalkeeper)
        setToTextDetails(awayDefense, away_defense)
        setToTextDetails(awayMidfield, away_midfield)
        setToTextDetails(awayForward, away_forward)
        setToTextDetails(
            awaySubstitutes,
            this.away_substitutes
        )

        hideProgress(progressBar)

    }

    override fun showHomeTeamBadge(data: List<BadgeItem>) {
        Picasso.get().load(data[0].strTeamBadge).into(logo_home)
    }

    override fun showAwayTeamBadge(data: List<BadgeItem>) {
        Picasso.get().load(data[0].strTeamBadge).into(logo_away)
    }

    private fun getDataString(dataText: String?, value: String): String {
        return if (value != "null")
            getString(R.string.textDetails,dataText, value)
        else
            getString(R.string.textDetails, ""," - ")
    }

    private fun getDataList (data: String?): List<String> {
        return data.toString().split(";")
    }

    private fun setToTextDetails(listData: List<String> , tv: TextView){
        for (value in listData) {
            tv.text = getDataString(tv.text.toString(), value.trim() )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            add_to_favorite -> {
                if (isFavorite)  removeToFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        swipeRefreshLayout = swipeRefresh_detail

        try {
            database.use {
                insert(MatchFavDB.TABLE_FAVORITE_MATCH,
                    MatchFavDB.ID_EVENT to eventId,
                    MatchFavDB.DATE_EVENT to matchDate,
                    MatchFavDB.HOME_TEAM to teamHome,
                    MatchFavDB.AWAY_TEAM to teamAway,
                    MatchFavDB.HOME_SCORE to scoreHome,
                    MatchFavDB.AWAY_SCORE to scoreAway,
                    MatchFavDB.TIME_EVENT to matchTime)
            }
            snackbar(swipeRefreshLayout, "Added to Your Favorite Match").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun removeToFavorite() {

        swipeRefreshLayout = swipeRefresh_detail

        try {
            database.use {
                delete(MatchFavDB.TABLE_FAVORITE_MATCH, "( ID_EVENT = {id_event} )",
                    "id_event" to eventId)
            }
            snackbar(swipeRefreshLayout, "Remove From Favorite Match").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(MatchFavDB.TABLE_FAVORITE_MATCH)
                .whereArgs("( ID_EVENT = {id_event})",
                    "id_event" to eventId)
            val favoriteMatch = result.parseList(classParser<MatchFavDB>())
            if (!favoriteMatch.isEmpty()) isFavorite = true
        }
    }
}