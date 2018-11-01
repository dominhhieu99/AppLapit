package com.example.dung.applabit.main

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.dung.applabit.MyApp
import com.example.dung.applabit.R
import com.example.dung.applabit.base.MyBaseActivity
import com.example.dung.applabit.main.chats.ChatsActivity
import com.example.dung.applabit.main.findfriend.FindFriendFragment
import com.example.dung.applabit.main.friend.FriendFragment
import com.example.dung.applabit.main.like.LikeFragment
import com.example.dung.applabit.main.profile.ProfileActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class MainActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addOrShowFragment(FindFriendFragment.newFragment, R.id.framelayoutMain, false)

        setSupportActionBar(toobarMain as Toolbar?)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = "Home"

        init()
    }

    private fun init() {

        getLocation()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_find -> {
                    addOrShowFragment(FindFriendFragment.newFragment, R.id.framelayoutMain, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_like -> {
                    addOrShowFragment(LikeFragment.newFragment, R.id.framelayoutMain, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_friend -> {
                    addOrShowFragment(FriendFragment.newFragment, R.id.framelayoutMain, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_profile -> {

                    val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val mFusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this as MyBaseActivity)

        mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                toast("${location!!.longitude}")
                MyApp.getInsatnce().latitude = location!!.latitude
                MyApp.getInsatnce().longitude = location.longitude
            } else {
                toast("location = null")

            }

        }.addOnFailureListener { exception: Exception ->
            toast(exception.message.toString())

        }

    }

    private fun hereLocation(lat: Double, lon: Double): String {
        var city = ""
        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
        val addresses: List<Address>
        try {

            addresses = geocoder.getFromLocation(lat, lon, 15)
            if (addresses.isNotEmpty()) {
                for (adr: Address in addresses) {
                    if (adr.locality != null) {
                        city = adr.locality
                        return city
                    }
                }
            }


        } catch (e: Exception) {

        }
        return city
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_message, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_message -> {
                toast("message...")
                val intent = Intent(this, ChatsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }


}
