package com.example.project1.dao

import android.os.Handler
import android.os.HandlerThread
import android.util.Log

class DbWorkerThread(threadName: String) : HandlerThread(threadName) {

    private lateinit var mWorkerHandler: Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mWorkerHandler = Handler(looper)
    }

    fun postTask(task: Runnable) {
        if(mWorkerHandler!=null){
            mWorkerHandler.post(task)
        }else{
            Log.e("Error", "Handle error")
            mWorkerHandler = Handler(looper)
            mWorkerHandler.post(task)
        }

    }

}