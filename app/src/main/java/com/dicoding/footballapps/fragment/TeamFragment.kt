package com.dicoding.footballapps.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.dicoding.footballapps.R
import com.dicoding.footballapps.R.id.search_team
import com.dicoding.footballapps.activity.activityDetail.ActivityDetailTeam
import com.dicoding.footballapps.adapter.TeamAdapter
import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.model.TeamItem
import com.dicoding.footballapps.presenter.TeamPresenter
import com.dicoding.footballapps.view.TeamView
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class TeamFragment : Fragment(), TeamView {

    private var teamDataList        : MutableList<TeamItem> = mutableListOf()
    private lateinit var presenter  : TeamPresenter
    private lateinit var adapter    : TeamAdapter

    private lateinit var recyclerViewTeam : RecyclerView
    private lateinit var progressBar : ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var league : String
    private var searchView : SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar = progressBar_team
        swipeRefreshLayout = swipeRefresh_team
        spinner = spinner_team

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem.toString()
                presenter.getTeamListData(league)
            }
        }

        adapter = TeamAdapter(teamDataList){

            ctx.startActivity<ActivityDetailTeam>("team_data" to it )
        }

        recyclerViewTeam = recyclerView_team
        recyclerViewTeam.layoutManager = LinearLayoutManager(ctx)

        recyclerViewTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)


        swipeRefreshLayout.onRefresh {
            presenter.getTeamListData(league)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun showloading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideloading() {
        progressBar.visibility = View.GONE
    }

    override fun showTeamListData(data: List<TeamItem>) {
        swipeRefreshLayout.isRefreshing = false
        teamDataList.clear()
        teamDataList.addAll(data)
        adapter.notifyDataSetChanged()
        hideloading()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        menu?.clear()
        inflater?.inflate(R.menu.menu_search_team, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_team)

        searchView = searchItem?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView?.setIconifiedByDefault(false)
        searchView?.queryHint = "Search Team..."
        searchView?.onQueryTextListener {
            onQueryTextChange { it ->
                presenter.getSearchTeamData(it)
                true
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            search_team -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}