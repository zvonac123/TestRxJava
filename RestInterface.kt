package com.example.zvonimir.testrxjava

import retrofit2.http.GET

interface RestInterface {

    @GET("/api/clues") fun getQuestionList(): io.reactivex.Observable<Array<Question>>
}