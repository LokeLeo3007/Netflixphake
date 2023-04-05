package com.example.netflixphake.ui.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

open class BaseViewModel(application: Application): AndroidViewModel(application) {
    private var ioJob: Job = Job()

    val coroutineScope by lazy { CoroutineScope(ioJob + Dispatchers.IO + exceptionHandler) }

    val apiErrorReponse: MutableLiveData<String> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler{coroutineContext, error ->
        Log.d("Error",error.message.toString())
        apiErrorReponse.postValue(error.message.toString())
        coroutineContext.cancel()
    }

    open fun clearDisposable(){
        ioJob.cancel()
        coroutineScope.cancel()
    }
}