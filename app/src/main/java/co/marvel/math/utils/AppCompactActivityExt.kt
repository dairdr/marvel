package co.marvel.math.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity

fun <T : ViewModel> AppCompatActivity.buildViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.default()).get(viewModelClass)