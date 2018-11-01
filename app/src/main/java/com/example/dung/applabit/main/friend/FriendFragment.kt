package com.example.dung.applabit.main.friend


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.dung.applabit.R
import com.example.dung.applabit.base.BaseFragment

class FriendFragment : BaseFragment() {
    companion object {
        val newFragment = FriendFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun init() {


    }

}
