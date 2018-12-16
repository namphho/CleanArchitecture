package com.hnam.remote.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by nampham on 12/16/18.
 */
class GithubTrendingServiceFactory {

    private fun makeGithubTrendingService(isDebug: Boolean) : GithubTrendingService{
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor(isDebug))
        return makeGithubTrendingService(okHttpClient)
    }

    private fun makeGithubTrendingService(okHttpClient: OkHttpClient) : GithubTrendingService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(GithubTrendingService::class.java)
    }

    private fun makeOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean) : HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug){
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}