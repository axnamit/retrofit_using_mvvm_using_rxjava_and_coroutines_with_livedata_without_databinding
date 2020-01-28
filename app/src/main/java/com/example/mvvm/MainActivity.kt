package com.example.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mvvm.view_model.ViewaModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: ViewaModel = ViewModelProviders.of(this).get<ViewaModel>(
            ViewaModel::class.java
        )
        viewModel.getPersons()?.observe(this, androidx.lifecycle.Observer {

                value ->
            println("list${value[0].body}")
            text.text = value[0].body.toString()


        })

        viewModel.getRXJAVAList()?.observe(this, androidx.lifecycle.Observer { value ->
            text2.text = value[0].id.toString()
        })
        /* GlobalScope.launch {
             runOnUiThread(Runnable {
                 toast(this@MainActivity,"courtinecalled")
             })


             delay(10)
         }*/

        viewModel.getCourtineData()?.observe(this, androidx.lifecycle.Observer { va ->
            text3.text = va[0].title
        })
    }


}
