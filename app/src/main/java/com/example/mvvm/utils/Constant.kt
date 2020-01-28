package com.example.mvvm.utils

import android.content.Context
import android.widget.Toast

const val BASE_URL: String = "https://jsonplaceholder.typicode.com"

fun toast(context: Context, string: String) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}
