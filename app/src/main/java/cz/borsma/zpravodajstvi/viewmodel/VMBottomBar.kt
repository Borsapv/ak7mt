package cz.borsma.zpravodajstvi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VMBottomBar: ViewModel() {
    private val _state: MutableLiveData<Boolean> = MutableLiveData(false)

    val state: LiveData<Boolean> get() = _state

    fun setState(status: Boolean) {

        _state.postValue(status)

    }

}