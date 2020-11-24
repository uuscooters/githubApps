package com.resmana.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showRecyclerList()
        viewModel()
    }

    private fun viewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        btnSearch.setOnClickListener {
            val user = editUser.text.toString()
            if (user.isEmpty()) return@setOnClickListener
            showLoading(true)
            mainViewModel.setUsers(user)
        }

        mainViewModel.getUsers().observe(this, Observer { Users ->
            if (Users != null) {
                adapter.setData(Users)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerList() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        lv_list.layoutManager = LinearLayoutManager(this)
        lv_list.setHasFixedSize(true)
        lv_list.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarUser.visibility = View.VISIBLE
        } else {
            progressBarUser.visibility = View.GONE
        }
    }

}

