package br.com.fiap.buddyband

import android.app.Notification
import android.app.NotificationChannel
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.concurrent.atomic.AtomicInteger


class MyFirebaseMessagingService : FirebaseMessagingService() {

    class NotificationID {
        companion object {
            private final val id = AtomicInteger(0);
            public fun generate(): Int {
                return  id.incrementAndGet()
            }
        }
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("Firebase", "From: " + remoteMessage!!.from!!)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {

            Log.d("Firebase", "Message data payload: " + remoteMessage.data)

            var title = ""
            var message = ""

            when(remoteMessage.data.get("type").toString()) {
                "panic" -> {
                    title = "Botão de PANICO!"
                    message = "A pessoa está solicitando ajuda urgente"
                }
                "critical-high" -> {
                    title = "Alerta de batimentos cardíacos"
                    message = "Está MUITO ALTO (" + remoteMessage.data.get("bpm").toString() + "bpm)"
                }
                "high" -> {
                    title = "Alerta de batimentos cardíacos"
                    message = "Está ALTO (" + remoteMessage.data.get("bpm").toString() + "bpm)"
                }
                "critical-low" -> {
                    title = "Alerta de batimentos cardíacos"
                    message = "Está BAIXO (" + remoteMessage.data.get("bpm").toString() + "bpm)"
                }
            }

            val mBuilder = NotificationCompat.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setContentTitle(title)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            val notificationManager = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(NotificationID.generate(), mBuilder.build());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d("Firebase", "Message Notification Body: " + remoteMessage.notification!!.body!!)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
