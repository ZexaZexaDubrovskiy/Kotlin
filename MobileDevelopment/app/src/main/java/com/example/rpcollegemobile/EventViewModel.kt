package com.example.rpcollegemobile

import android.view.SurfaceControl
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rpcollegemobile.itemEvent.Event
import okhttp3.Call

class EventViewModel: ViewModel() {

    private val eventLiveData: MutableLiveData<List<Event>> = MutableLiveData<List<Event>>()
    private val repository: Repository = Repository()
    private val eventIsLoadingLiveData = MutableLiveData<Boolean>()
    private var currentCall: Call? = null

    val event: LiveData<List<Event>>
        get() = eventLiveData

    val eventIsLoading: LiveData<Boolean>
        get() = eventIsLoadingLiveData

    fun loadEvent(fManager: FragmentManager) {
        eventIsLoadingLiveData.postValue(true)
        currentCall = repository.loadEventAsync(
            { event ->
                eventLiveData.postValue(event)
                eventIsLoadingLiveData.postValue(false)
                currentCall = null
            },
            { e ->
                eventIsLoadingLiveData.postValue(false)
                currentCall = null

//                AlertDialogFragment.newInstance(
//                    "About",
//                    "Инета нет, он пропал",
//                    "Понятно",
//                    ""
//                ).show(fManager, AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG)
            }
        )
    }

    override fun onCleared() {
        currentCall?.cancel()
        currentCall = null
        super.onCleared()
    }
}