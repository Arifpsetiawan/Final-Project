package com.dicoding.footballapps.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.footballapps.R
import com.dicoding.footballapps.model.TeamItem
import kotlinx.android.synthetic.main.fragment_overviewteam.*

class OverviewTeamFragment : Fragment() {

    private lateinit var teamData: TeamItem


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamData = activity?.intent!!.getSerializableExtra("team_data") as TeamItem
        overview_team_detail.text = teamData.strDescriptionEN
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overviewteam, container, false)
    }
}