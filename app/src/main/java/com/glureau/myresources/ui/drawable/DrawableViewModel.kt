package com.glureau.myresources.ui.drawable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrawableViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is drawable Fragment"
    }
    val text: LiveData<String> = _text
}