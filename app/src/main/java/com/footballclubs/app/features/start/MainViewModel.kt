package com.footballclubs.app.features.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.footballclubs.app.networking.model.Club
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<List<Club>>()
    val selectedItem: LiveData<List<Club>> get() = mutableSelectedItem

    fun selectItem(item: List<Club>) {
        mutableSelectedItem.value = item
    }
}
