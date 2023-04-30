package com.example.spotfinder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.spotfinder.R
import com.example.spotfinder.adapters.PagerAdapter
import com.example.spotfinder.ui.fragments.commets.CommentsFragment
import com.example.spotfinder.ui.fragments.location.LocationFragment
import com.example.spotfinder.ui.fragments.overview.OverviewFragment
import com.example.spotfinder.util.Constants.Companion.BUNDLE_LKEY
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(CommentsFragment())
        fragments.add(LocationFragment())

        val titles = ArrayList<String>()
        titles.add("Resumen")
        titles.add("Comentarios")
        titles.add("Ubicación")

        val resultBundle = Bundle()
        resultBundle.putParcelable(BUNDLE_LKEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.apply {
            adapter = pagerAdapter
        }
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)

    }
}