package com.resmana.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    companion object {
        private val TAG = DetailActivity::class.java.simpleName
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        title = "Detail User"

        val userGit = intent.getParcelableExtra(EXTRA_USER) as Users
        val user = userGit.username
        getDetailUser(user)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.userName = userGit.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

//        showLoading(true)
    }

//    private fun showLoading(state: Boolean) {
//        if (state) {
//            progressBarDetail.visibility = View.VISIBLE
//        } else {
//            progressBarDetail.visibility = View.GONE
//        }
//    }


    private fun getDetailUser(userGit:String?) {
        progressBarDetail.visibility = View.VISIBLE
        val url = "https://api.github.com/users/${userGit}"
        val client = AsyncHttpClient()
        client.addHeader("Authorization","token 05ccef4bfd9014178c9e3bca405d45863b0ff916")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray){
                progressBarDetail.visibility = View.INVISIBLE
               val result = String(responseBody)
                Log.d(TAG, result)
                try{
                    val responseObject = JSONObject(result)
                    val avatarUser = responseObject.getString("avatar_url")
                    val name = responseObject.getString("name")
                    val company = responseObject.getString("company")
                    val locations = responseObject.getString("location")

                    Glide.with(this@DetailActivity)
                        .load(avatarUser)
                        .apply(RequestOptions().override(55, 55))
                        .into(avatar_img)
                    nameDetail.text = name
                    tv_company.text = company
                    tv_location.text = locations
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray,error: Throwable) {
                progressBarDetail.visibility = View.INVISIBLE
                val errorMessage = when(statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}