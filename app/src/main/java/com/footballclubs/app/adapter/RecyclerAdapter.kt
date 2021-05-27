package com.footballclubs.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.footballclubs.app.R
import com.footballclubs.app.features.main.MainFragmentDirections
import com.footballclubs.app.networking.model.Club
import com.squareup.picasso.Picasso

class RecyclerAdapter (
    private val clubs: List<Club>,
    private val activity: FragmentActivity?):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val club = clubs[position]

        holder.name.text = club.name
        holder.country.text = club.country
        holder.value.text = club.value.toString() + activity?.resources?.getString(R.string.millions)
        Picasso.get().load(club.image).into(holder.imageView)
        holder.itemView.setOnClickListener{
            onToMembersClick(club.id)
        }
    }

    override fun getItemCount() = clubs.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name)
        val country: TextView = itemView.findViewById(R.id.country)
        val value: TextView = itemView.findViewById(R.id.value)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    private fun onToMembersClick(clubId: String){
        // Handles navigation to details page of the clicked item

            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(clubId)
            activity?.findNavController(R.id.navHostFragment)?.navigate(action)
    }

}


