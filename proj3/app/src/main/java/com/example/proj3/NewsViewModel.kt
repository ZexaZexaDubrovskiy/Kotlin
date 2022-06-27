package com.example.proj3

import android.view.SurfaceControl
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call

class NewsViewModel : ViewModel() {
    private val newsLiveData: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    private val repository: NewsRepository = NewsRepository()
    private val newsIsLoadingLiveData = MutableLiveData<Boolean>()
    private var currentCall: Call? = null

    val news: LiveData<List<Article>>
        get() = newsLiveData

    val newsIsLoading: LiveData<Boolean>
        get() = newsIsLoadingLiveData

    fun loadNews(fManager: FragmentManager) {
        newsIsLoadingLiveData.postValue(true)
        currentCall = repository.loadNewsAsync(
            { news ->
                newsLiveData.postValue(news)
                newsIsLoadingLiveData.postValue(false)
                currentCall = null
            },
            { e ->
                newsIsLoadingLiveData.postValue(false)
                currentCall = null

                AlertDialogFragment.newInstance(
                    "About",
                    "Инета нет, он пропал",
                    "Понятно",
                    ""
                ).show(fManager, AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG)
            }
        )
    }

    override fun onCleared() {
        currentCall?.cancel()
        currentCall = null
        super.onCleared()
    }
}