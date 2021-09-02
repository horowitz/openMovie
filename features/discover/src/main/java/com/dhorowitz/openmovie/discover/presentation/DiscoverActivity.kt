package com.dhorowitz.openmovie.discover.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dhorowitz.openmovie.discover.R
import com.dhorowitz.openmovie.discover.databinding.ActivityDiscoverBinding
import com.dhorowitz.openmovie.discover.presentation.grid.DiscoverAdapter
import com.dhorowitz.openmovie.discover.presentation.grid.GridSpacingItemDecoration
import com.dhorowitz.openmovie.discover.presentation.grid.OnScrollListener
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity
import com.dhorowitz.openmovie.navigation.navigateToMovieDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscoverBinding

    private val viewModel: DiscoverViewModel by viewModels()
    private val adapter = DiscoverAdapter { viewModel.handle(ItemClicked(it)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setupViews()
        setContentView(binding.root)

        viewModel.state.observe(this, ::handleState)
        viewModel.event.observe(this, ::handleEvent)

        viewModel.handle(Load)
    }

    private fun setupViews() = with(binding) {
        setSupportActionBar(toolbar)
        discoverRecyclerView.layoutManager = GridLayoutManager(this@DiscoverActivity, 3)
        discoverRecyclerView.addItemDecoration(GridSpacingItemDecoration(3, 20, true))
        discoverRecyclerView.addOnScrollListener(OnScrollListener { viewModel.handle(Load) })

        discoverRecyclerView.adapter = adapter
    }

    private fun handleEvent(event: DiscoverEvent) = when (event) {
        is NavigateToMovieDetails -> navigateToMovieDetails(event.id)
    }

    private fun handleState(state: DiscoverState) = when (state) {
        is Content -> setViewsVisibility(content = View.VISIBLE).also { render(state.items) }
        Loading -> setViewsVisibility(loading = View.VISIBLE)
        Error -> handleError()
    }

    private fun handleError() {
        setViewsVisibility(error = View.VISIBLE)

        binding.discoverError.findViewById<Button>(R.id.errorButton)
            .setOnClickListener { viewModel.handle(Load) }
    }

    private fun setViewsVisibility(
        loading: Int = View.GONE,
        content: Int = View.GONE,
        error: Int = View.GONE
    ) = with(binding) {
        discoverLoading.visibility = loading
        discoverError.visibility = error
        discoverRecyclerView.visibility = content
    }

    private fun render(items: List<DiscoverViewEntity>) {
        adapter.submitList(items)
    }
}
