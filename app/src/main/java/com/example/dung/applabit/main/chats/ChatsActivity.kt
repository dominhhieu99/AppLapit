package com.example.dung.applabit.main.chats

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import com.example.dung.applabit.R
import com.example.dung.applabit.base.MyBaseActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.Exception
import java.util.*

class ChatsActivity : MyBaseActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        getLocation()
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val mFusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            val latitude = location!!.latitude
            val longitude = location.longitude

            toast(hereLocation(latitude, longitude)+"   $latitude,$longitude")
        }.addOnFailureListener { exception: Exception ->
            toast(exception.message.toString())

        }

    }

    private fun hereLocation(lat: Double, lon: Double): String {
        var city = ""
        val geocoder = Geocoder(this, Locale.getDefault())
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

}

