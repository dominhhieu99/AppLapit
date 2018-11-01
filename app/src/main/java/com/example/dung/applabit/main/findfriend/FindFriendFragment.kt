package com.example.dung.applabit.main.findfriend


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.dung.applabit.R
import com.example.dung.applabit.base.BaseFragment

class FindFriendFragment : BaseFragment() {
    companion object {
        val newFragment = FindFriendFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_friend, container, false)
    }

    override fun init() {

    }

}
