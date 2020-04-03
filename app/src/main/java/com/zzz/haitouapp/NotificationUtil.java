package com.zzz.haitouapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import static androidx.core.app.NotificationCompat.DEFAULT_ALL;


class NotificationUtil {


    /**
     * 发送通知
     *
     * @param content
     */
    static void sendNotification(
            Context context,
            String content
    ) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "123321")
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                //.setFullScreenIntent(contentIntent,true)
                .setAutoCancel(true)
                .setTicker("悬浮通知")
                .setDefaults(DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setContentIntent(pendingIntent);

        // 兼容  API 26，Android 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 第三个参数表示通知的重要程度，默认则只在通知栏闪烁一下
            NotificationChannel notificationChannel = new NotificationChannel(
                    "123321",
                    "海投网推送通知",
                    NotificationManager.IMPORTANCE_HIGH
            );
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            // 关联PendingIntent
            builder.setFullScreenIntent(pendingIntent, true);

            // 注册通道，注册后除非卸载再安装否则不改变
            notifyManager.createNotificationChannel(notificationChannel);
        }


        notifyManager.notify((int) System.currentTimeMillis(),builder.build());//id要保证唯一
    }

}
