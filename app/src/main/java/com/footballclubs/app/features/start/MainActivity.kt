package com.footballclubs.app.features.start


import android.os.Bundle
import android.widget.Toast
import com.footballclubs.app.R
import com.footballclubs.app.application.App
import com.footballclubs.app.base.BaseActivity
import com.footballclubs.app.di.app.DaggerAppComponent
import com.footballclubs.app.networking.model.Club
import retrofit2.Call
import retrofit2.Response

class MainActivity : BaseActivity() {

    private var clubs: List<Club> = listOf()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModel()

        val component = DaggerAppComponent.builder()
            .application(application)
           .seedInstance(application = App())
           .build()

        component.apiService().getClubs().enqueue(object : retrofit2.Callback<List<Club>> {

            // Calls the components ApiService to fetch the json object

                   override fun onResponse(call: Call<List<Club>>, response: Response<List<Club>>) {
                       clubs = response.body()!!
                       viewModel.selectItem(clubs) //stores the "clubs" in the viewModel
                   }

                   override fun onFailure(call: Call<List<Club>>, t: Throwable) {
                       Toast.makeText(this@MainActivity, resources.getString(R.string.errortoast), Toast.LENGTH_LONG).show()
                   }
               })
        }
}
