

package com.example.android.eggtimer.pushnotification

import android.app.NotificationManager
import android.nfc.Tag
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.android.eggtimer.utils.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d(TAG, "From: ${remoteMessage?.from}")


        remoteMessage?.data?.let {
            Log.d(TAG, "Message data payload ${remoteMessage.data}")
        }



        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body:${it.body}")
            sendNotification(it.body as String)
        }

    }


    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token:$token")

        sendRegistrationToServer(token)
    }

    // [END on_new_token]


    private fun sendRegistrationToServer(token: String?) {

    }

   
    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}