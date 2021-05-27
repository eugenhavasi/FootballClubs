package com.footballclubs.app.features.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.footballclubs.app.R
import com.footballclubs.app.base.BaseFragment
import com.footballclubs.app.databinding.FragmentDetailsBinding
import com.footballclubs.app.features.start.MainViewModel
import com.footballclubs.app.networking.model.Club
import com.squareup.picasso.Picasso
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private val viewModel: MainViewModel by activityViewModels()
    private val binding by viewBinding(FragmentDetailsBinding::bind)

    private var clubs: List<Club> = listOf()
    private var club: Club? = null
    private var clubId: String? = null
    private var mActionBarToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.clubId = DetailsFragmentArgs.fromBundle(requireArguments()).clubId //receives the selected club id from the MainFragment

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActionBarToolbar = binding.toolbar2

        viewModel.selectedItem.observe(viewLifecycleOwner, {
            this.clubs = it
            this.club = clubs.find { it.id == clubId }!!
            setToolBar(mActionBarToolbar!!, club!!)
            onBindClubView(ClubDetailsView(binding))
        })
    }

    fun setToolBar(toolbar: Toolbar, club: Club){
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.title = club.name
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener{onBackToListClick()}
    }

    fun onBackToListClick(){
        // Handles navigation to the list when clicked on the back arrow
            val action = DetailsFragmentDirections.actionDetailsFragmentToMainFragment()
            activity?.findNavController(R.id.navHostFragment)?.navigate(action)
        }

        private fun onBindClubView(holder: ClubDetailsView){

        holder.country.text = club?.country
        Picasso.get().load(club?.image).into(holder.imageView)

        holder.valueDescription.text = resources.getString(R.string.details_valuedescription, club?.name, club?.country, club?.value)

        holder.europeanTitlesDescription.text = resources.getString(R.string.details_europeantitlesdescription, club?.name, club?.european_titles)
    }

    override fun onDestroyView() {
        // nullify the Club and Toolbar to avoid leaks
        mActionBarToolbar = null
        super.onDestroyView()
    }

    inner class ClubDetailsView(binding: FragmentDetailsBinding){
        val imageView: ImageView = binding.detailsImage
        val country: TextView = binding.detailsCountry
        val valueDescription: TextView = binding.detailsValueDescription
        val europeanTitlesDescription: TextView = binding.detailsEuropeanTitlesDescription
    }
}





