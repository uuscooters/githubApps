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
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONArray

class FollowersFragment : Fragment() {

    private lateinit var adapter: FollowersAdapter
    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
        private const val ARG_USERNAME = "username"
        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarFollowers.visibility = View.VISIBLE
        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()


        listFollowers.setHasFixedSize(true)
        listFollowers.layoutManager = LinearLayoutManager(context)
        listFollowers.adapter = FollowersAdapter()
        listFollowers.adapter = adapter

        val follower = arguments?.getString(ARG_USERNAME)
        val url = "https://api.github.com/users/${follower}/followers"
            val client = AsyncHttpClient()
            client.addHeader("Authorization","token 05ccef4bfd9014178c9e3bca405d45863b0ff916")

            client.addHeader("User-Agent", "request")
            client.get(url, object : AsyncHttpResponseHandler(){
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    progressBarFollowers.visibility = View.INVISIBLE
                    val UsersFollowers = ArrayList<Users>()
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
                            UsersFollowers.add(user)
                        }
                        adapter.setData(UsersFollowers)
                    } catch (e: Exception) {
                        Log.d(TAG, e.message.toString())
                        e.printStackTrace()
                    }
                }
                override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                   progressBarFollowers.visibility = View.INVISIBLE
                    Log.d(TAG, error.message.toString())
                }
            })
    }
}