package com.rickyslash.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // make new 'RecyclerView' that is going to be 'assigned later'
    private lateinit var rvHeroes: RecyclerView

    // make 'new empty Array' with Generic type 'Hero'
    private val heroList = ArrayList<Hero>()

    // function when the 'ViewHolder' is 'clicked'
    private fun showSelectedHero(hero: Hero) {
        // 'Toast' will make 'floating notification'
        Toast.makeText(this, "You've chosen ${hero.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHeroes = findViewById(R.id.rv_heroes)

        // this optimize the 'RecyclerView'. It indicates the 'size' of the 'RecyclerView' will remain constant and 'will not change dynamically' during runtime
        rvHeroes.setHasFixedSize(true)

        // '.addAll' will add all obect inside an 'ArrayList' returned from custom method 'getListHeroes()'
        heroList.addAll(getListHeroes())

        // custom method (made by us) to 'show' the 'RecyclerView'
        showRecyclerList()
    }

    // add 'option menu' onCreate
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // menuInflater Instantiates 'menu XML file' to its corresponding View objects
        // 1st parameter is the 'XML menu' file, the 2nd is the 'menu' to inflate to
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // add 'action' when 'option item' is 'selected'
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                rvHeroes.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvHeroes.layoutManager = GridLayoutManager(this, 2)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListHeroes(): ArrayList<Hero> {
        // assigning 'StringArray' / 'TypedArray' from 'resources' (res)
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDesc = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<Hero>()

        // make 'object' for 'each data' 'in resources'
        for (i in dataName.indices) {
            val hero = Hero(dataName[i], dataDesc[i], dataPhoto.getResourceId(i, -1))
            // add it to the ArrayList that's going to be returned
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        // sets 'layoutManager' for the 'RecyclerView' as 'LinearLayoutManager' and will be spanned in 'this' (MainActivity)
        rvHeroes.layoutManager = LinearLayoutManager(this)

        // instantiate 'ListHeroAdapter' by passing ArrayList<Hero> 'heroList'
        val listHeroAdapter = ListHeroAdapter(heroList)

        // '.adapter' sets new adapter to provide child views on demand. And the Adapter that's being 'used' is 'ListHeroAdapter(heroList)'
        rvHeroes.adapter = listHeroAdapter

        // call 'setOnItemClickCallback' 'method' from 'listHeroAdapter' class
        // '.setOnItemCallback' need to be passed 'Interface' (OnItemClickCallback), so an 'anonymous object' is implemented (Interface need to be called 'raw' directly from the class, without instantiating)
        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            // 'overriding' the 'Interface' 'method'
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }
}