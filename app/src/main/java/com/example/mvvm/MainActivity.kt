package com.example.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.androidnetworking.AndroidNetworking
import com.example.mvvm.model.Post
import com.example.mvvm.view_model.ViewaModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidNetworking.initialize(getApplicationContext())
        compositeDisposable = CompositeDisposable()
        val viewModel: ViewaModel = ViewModelProviders.of(this).get<ViewaModel>(
            ViewaModel::class.java
        )
        /* viewModel.getPersons()?.observe(this, androidx.lifecycle.Observer {
                 value ->
             println("list${value[0].body}")
             text.text = value[0].body.toString()
         })*/
        viewModel.getPersons()

        viewModel.getRXJAVAList()?.observe(this, androidx.lifecycle.Observer { value ->
            text2.text = value[0].id.toString()
        })


        viewModel.getCourtineData()?.observe(this, androidx.lifecycle.Observer { va ->
            text3.text = va[0].title
        })

        var fff = viewModel.rxNetworkMethod().subscribe({
            // println("body_list" + it.body)

            var lsit: List<Post> = it as List<Post>
            text.text = lsit[0].body
        }, {
            it.printStackTrace()
            text.text = it.message.toString()
        }).let {
            compositeDisposable?.add(it)

        }

        println("rxnetwork" + fff.toString())


    }




}
