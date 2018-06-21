package com.example.zvonimir.testrxjava

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.ArrayAdapter
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {
    var target: ListView?=null
    val gson= GsonBuilder().setLenient().create()
    val retrofit: Retrofit=Retrofit.Builder().baseUrl("http://jservice.io").addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    var questions: Array<Question>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        target=findViewById(R.id.ListView)
        val rest=retrofit.create(RestInterface::class.java)
        rest.getQuestionList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            questions =it
            var questionString=ArrayList<String>()
            for(i in 0 until questions!!.size step 1) questionString.add(questions!!.get(i).question!!)
            val adapter=ArrayAdapter<String>(this,R.layout.question,questionString)
            target?.adapter=adapter
        },{
            throw it
        })
    }
}
