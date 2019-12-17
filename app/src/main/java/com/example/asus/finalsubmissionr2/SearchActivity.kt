package com.example.asus.finalsubmissionr2

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.asus.finalsubmissionr2.adapter.SectionPageAdapter
import com.example.asus.finalsubmissionr2.adapter.SectionPageAdapterSearch
import com.example.asus.finalsubmissionr2.fragment.SearchFragmentMovie
import kotlinx.android.synthetic.main.activity_detail_movie_tv.*
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val sectionsPagerAdapter = SectionPageAdapterSearch(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater. inflate(R.menu.main_search, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        else if (item.itemId == R.id.all) {
            val mIntent = Intent(this,MainActivity::class.java)
            startActivity(mIntent)
        }
        else if(item.itemId == R.id.fav){
            val intent = Intent(this, Favorite::class.java)
            startActivity(intent)
        }
        else if(item.itemId == R.id.setting){
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
