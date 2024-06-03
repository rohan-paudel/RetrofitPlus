package com.coredreams.retrofitlearn

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import com.coredreams.retrofitlearn.databinding.ActivityMainBinding
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).also {
            binding = it
            it.lifecycleOwner = this
            it.mainViewModel = viewModel.also {hey ->
                val loginCallback = object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                        hey.updateEmail()
                    }
                }
                hey.emailData.addOnPropertyChangedCallback(loginCallback)
            }
        }
    }
}