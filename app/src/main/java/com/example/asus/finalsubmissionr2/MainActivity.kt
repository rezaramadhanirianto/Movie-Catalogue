package com.example.asus.finalsubmissionr2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asus.finalsubmissionr2.adapter.MovieTvShowAdapter
import com.example.asus.finalsubmissionr2.adapter.SectionPageAdapter
import com.example.asus.finalsubmissionr2.alarm.AlarmReceiver
import com.example.asus.finalsubmissionr2.db.MovieHelper
import com.example.asus.finalsubmissionr2.db.TvShowHelper
import com.example.asus.finalsubmissionr2.model.Movie
import com.example.asus.finalsubmissionr2.viewModel.MovieTvViewModel
import com.example.asus.finalsubmissionr2.viewModel.UpToDateViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: UpToDateViewModel

    private lateinit var alarmManager: AlarmReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        Daily Reminder
        var language = Locale.getDefault().getDisplayLanguage().toString()
        if(language == "Bahasa Indonesia"){
            language = "id"
        }else{
            language = "en-US"
        }

//        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
//            UpToDateViewModel::class.java)
//        mainViewModel.setMovie("movie",language)
//        mainViewModel.getMovies().observe(this, androidx.lifecycle.Observer { movieItem ->
//            val data = ArrayList<Movie>()
//
//            if(movieItem != null){
//                alarmManager.setDailyMovie(this, movieItem)
//            }
//        })
//        alarmManager = AlarmReceiver()
//        alarmManager.setDailyReminder(this)




        val sectionsPagerAdapter = SectionPageAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
        MovieHelper.getInstance(applicationContext).open()
        TvShowHelper.getInstance(applicationContext).open()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater. inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        else if (item.itemId == R.id.fav) {
            val mIntent = Intent(this,Favorite::class.java)
            startActivity(mIntent)
        }else if(item.itemId == R.id.search){
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == R.id.setting){
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

}
