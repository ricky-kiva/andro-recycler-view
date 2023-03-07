package com.rickyslash.recyclerview

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/* BIG NOTE ABOUT THE PROCESS*/
// This is a class that works as an 'Adapter' for the 'RecyclerView'
// all of the component below (properties/methods) will be 'automatically' called 'during layout process' (scrolling)
// it also means that, we 'don't need to call' any of the 'overridden function' from 'Adapter'

// Extending 'RecyclerView.Adapter' will state the class as a RecyclerView's Adapter
// 'ListViewHolder' is a custom class (made by us) to store all 'View' object 'inside layout'
class ListHeroAdapter(private val listHero: ArrayList<Hero>): RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    // This variable later will be assigned with 'OnItemClickCallback' 'Interface' later
    private lateinit var onItemClickCallback: OnItemClickCallback

    // 'setOnItemClickCallback' is a custom method (made by us) for 'OnClick' 'callback'
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        // when '.setOnClickCallback' is called, the 'onItemClickCallback' properties will be assigned a value from the interface
        this.onItemClickCallback = onItemClickCallback
    }

    // This is the sub-class used as the 'Generic Type' for this class
    // It extends 'RecyclerView.ViewHolder'. It's going to store the 'View's that is going to be used 'ViewHolder' for the RecyclerView
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // All of the 'View' in the created 'layout' is 'referenced' in this class
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_item_desc)
    }

    // Initiate 'ViewHolder' from 'layout XML' (layout_row_hero) as a template
    // 'ViewHolder' works by 'caching' views that are 'used', 'repeatedly' in 'each row' of the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // 'LayoutInflater' Instantiates 'layout XML file' to its corresponding View objects
        // '(parent.context)' means from 'parent' (ViewGroup) it bounds to the 'reference of the app environment' (context)
        // 'context' provides access to resources, system-level services, activities, broadcast receivers, etc

        // '.inflate' will return 'root View' of the inflated hierarchy
        // 'root View' contains a 'parent View' and 'child View', where the 'parent View' commonly a 'ViewGroup'
        // The '2nd' parameter (root) will use 'parent' (ViewGroup) as the parent of the generated hierarchy
        // The '3rd' parameter (attachToRoot) value 'false' means the inflated layout should not be attached to 'parent' immediately
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_row_hero, parent, false)

        // it will return 'ListViewHolder' sub-class will the 'root View' as parameter
        return ListViewHolder(view)
    }

    // after the 'ViewHolder' is generated, the 'item size' will be estimated
    override fun getItemCount(): Int = listHero.size

    // 'onBindViewHolder()' is called by 'RecyclerView' when system need to 'update contents' (data) of the View regarding of the "current state"
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        // get 'name', 'desc', and 'photo' data based on 'listHero' (this class parameter) position
        val (name, desc, photo) = listHero[position]

        // 'assigning' the data from the 'current position'
        holder.tvName.text = name
        holder.tvDesc.text = desc
        // use 'Glide' plugin to fetch 'image' from the internet
        Glide.with(holder.itemView.context)
            .load(photo) // the URL
            .into(holder.imgPhoto) // 'ImageView' to be 'applied' the photo

        // add 'setOnClickListener' by 'callback'
        // the 'onItemClickCallback' referenced from 'lateinit var' in this class. It reference the 'OnItemClickCallback' 'Interface'
        // the 'variable' 'calls' the method inside the 'Interface' (.onItemClicked) and pass 'Hero' object respective to the 'holder.adapterPosition' (ViewHolder Adapter Position)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHero[holder.adapterPosition]) }
    }

    // Interface is a 'set' of methods that you could 'optionally' use without calling the method
    interface OnItemClickCallback {
        // the Interface contains 1 method
        fun onItemClicked(data: Hero)
    }

}