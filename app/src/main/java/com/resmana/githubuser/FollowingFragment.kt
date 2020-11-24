package com.resmana.githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray

class FollowingFragment : Fragment() {

    private lateinit var adapter: FollowingAdapter
    companion object {
        private val TAG= FollowingFragment::class.java.simpleName
        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarFollowing.visibility = View.VISIBLE
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()
        listFollowing.setHasFixedSize(true)
        listFollowing.layoutManager = LinearLayoutManager(context)
        listFollowing.adapter = FollowersAdapter()
        listFollowing.adapter = adapter

        val follower = arguments?.getString(ARG_USERNAME)
        val url = "https://api.github.com/users/${follower}/following"
        val client = AsyncHttpClient()
        client.addHeader("Authorization","token 05ccef4bfd9014178c9e3bca405d45863b0ff916")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                progressBarFollowing.visibility = View.INVISIBLE
                val UsersFollowing = ArrayList<Users>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val imgFollowers = jsonObject.getString("avatar_url")
                        val nameFollowers = jsonObject.getString("login")
                        val user = Users()
                        user.avatar = imgFollowers
                        user.username = nameFollowers
                        UsersFollowing.add(user)
                    }
                    adapter.setData(UsersFollowing)
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                progressBarFollowing.visibility = View.INVISIBLE
                Log.d(TAG, error.message.toString())
            }
        })
    }
}