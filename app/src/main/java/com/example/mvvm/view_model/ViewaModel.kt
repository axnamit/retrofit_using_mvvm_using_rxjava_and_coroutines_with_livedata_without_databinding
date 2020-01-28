package com.example.mvvm.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.model.Post
import com.example.mvvm.network.Service
import com.example.mvvm.utils.BASE_URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ViewaModel : ViewModel() {

    var compositeDisposable: CompositeDisposable? = null

    var posts: MutableLiveData<List<Post>>? = null

    var postsS: MutableLiveData<List<Post>>? = null

    var couroutine: MutableLiveData<List<Post>>? = null

    fun getPersons(): LiveData<List<Post>>? {
        if (posts == null) {
            posts = MutableLiveData()
            loadPersonList()
        }
        return posts
    }

    fun getRXJAVAList(): LiveData<List<Post>>? {
        if (postsS == null) {
            postsS = MutableLiveData()
            loadWithRxJAVA()
        }

        return postsS
    }

    fun getCourtineData(): LiveData<List<Post>>? {
        if (couroutine == null) {
            couroutine = MutableLiveData()
            usingCourtine()
        }
        return couroutine
    }

    private fun loadPersonList() {
        val httpClient = OkHttpClient.Builder()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL/*"https://jsonplaceholder.typicode.com"*/)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        val service: Service = retrofit.create(Service::class.java)

        service.getPersonList().enqueue(object : Callback<MutableList<Post>> {


            override fun onResponse(
                call: Call<MutableList<Post>>,
                response: Response<MutableList<Post>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        posts?.value = response.body()

                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    private fun loadWithRxJAVA() {

        compositeDisposable = CompositeDisposable()

        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Service::class.java)

        compositeDisposable?.add(
            requestInterface.getListWithRxJAVA()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ onSuccess(it) }, {})
        )

    }

    private fun onSuccess(it: List<Post>?) {
        postsS?.value = it
    }

    private fun usingCourtine() {

        val httpClient = OkHttpClient.Builder()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL/*"https://jsonplaceholder.typicode.com"*/)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        val service: Service = retrofit.create(Service::class.java)

        viewModelScope.launch {
            val response = service.coGO()
            try {
                couroutine?.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /* CoroutineScope(Dispatchers.IO).launch {
             val response = service.getPersonList()
             withContext(Dispatchers.Main){
                 try {

                     if (response.execute().isSuccessful){
                         couroutine?.value=response.execute().body()

                     }

                 }catch (e:Exception){
                     e.printStackTrace()
                 }
             }
         }*/

    }

}