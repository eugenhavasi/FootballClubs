package com.footballclubs.app.features.main

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.footballclubs.app.R
import com.footballclubs.app.adapter.RecyclerAdapter
import com.footballclubs.app.base.BaseFragment
import com.footballclubs.app.databinding.FragmentMainBinding
import com.footballclubs.app.features.start.MainViewModel
import com.footballclubs.app.networking.model.Club
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)

    private var clubs: List<Club> = listOf()
    private var mRecyclerView: RecyclerView? = null
    private var mActionBarToolbar: Toolbar? = null
    private var sortedByName = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = binding.recyclerView
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mActionBarToolbar = binding.toolbar
        insertMenuIntoToolbar(mActionBarToolbar!!)

        viewModel.selectedItem.observe(viewLifecycleOwner, {
          this.clubs = it.sortedBy { it.name }
            mRecyclerView?.adapter = RecyclerAdapter(clubs, activity)
        })
    }



    private fun insertMenuIntoToolbar(toolbar: Toolbar){
        // Sets the "sort" button in the toolbar
        toolbar.inflateMenu(R.menu.menu)
        toolbar.title = resources.getString(R.string.app_name)
        toolbar.setTitleTextColor(Color.WHITE)
        val item = toolbar.menu.getItem(0)
        item.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.sortItems -> {
                    if(sortedByName){
                        mRecyclerView?.adapter = RecyclerAdapter(clubs.sortedByDescending { it.value }, activity)
                        sortedByName = false
                    }else{
                        mRecyclerView?.adapter = RecyclerAdapter(clubs.sortedBy { it.name }, activity)
                        sortedByName = true
                    }

                }
            }
            true
        }
    }

    override fun onDestroyView() {
        // nullify the Toolbar and RecyclerView to avoid leaks
        mActionBarToolbar = null
        mRecyclerView = null
        super.onDestroyView()
    }
}





