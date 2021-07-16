package united.huawei.safehub;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import android.os.Bundle;

public class NotificationPush extends HmsMessageService {
    private static final String TAG = "PushDemoToken";
    private static final String CODELABS_ACTION = "com.huawei.codelabpush.action.ON_NEW_MESSAGE";

    @Override
    public void onNewToken(String token){
        super.onNewToken(token);
        Log.i(TAG, "recieve token :" + token);
        sendTokenToDisplay(token);

        // Token empty ?
        if (!TextUtils.isEmpty(token)) {
            refreshedTokenToServer(token);
        }

        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onNewToken");
        intent.putExtra("msg", "onNewToken called, token : " + token);
    }

    private void refreshedTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }

    @Override
    public void onTokenError(Exception e) { super.onTokenError(e); }

    @Override
    public void onMessageReceived(RemoteMessage message){
        super.onMessageReceived(message);
        if(message.getData().length() > 0 ){
            Log.i(TAG, "Message data payload : " + message.getData());
        }
        if(message.getNotification() != null ){
            Log.i(TAG, "Message Notification Body : " + message.getNotification().getBody());
        }

        if(message == null ){
            Log.e(TAG, "Received Message entity is NULL !");
            return;
        }

        Log.i(TAG, "get Data: " + message.getData()
                + "\n getFrom: " + message.getFrom()
                + "\n getTo: " + message.getTo()
                + "\n getMessageId: " + message.getMessageId()
                + "\n getSendTime: " + message.getSentTime()
                + "\n getDataMap: " + message.getDataOfMap()
                + "\n getMessageType: " + message.getMessageType()
                + "\n getTtl: " + message.getTtl()
                + "\n getToken: " + message.getToken());

        Boolean judgeWhetherIn10s = false;
        // If the message is not processed within 10 seconds, create a job to process it.
        if (judgeWhetherIn10s) {
            startWorkManagerJob(message);
        } else {
            // Process the message within 10 seconds.
            processWithin10s(message);
        }
    }

    private void startWorkManagerJob(RemoteMessage message) {
        Log.d(TAG, "Start new job processing.");
    }
    private void processWithin10s(RemoteMessage message) {
        Log.d(TAG, "Processing now.");
    }
    @Override
    public void onMessageSent(String s){
    }

    @Override
    public void onSendError(String s, Exception e){
    }

    private void sendTokenToDisplay(String token){
        Intent intent = new Intent("united.huawei.safehub.ON_NEW_TOKEN");
        intent.putExtra("token", token);
        sendBroadcast(intent);
    }

    private void deleteToken() {
        // Create a thread.
        new Thread() {
            @Override
            public void run() {
                try {
                    // Obtain the app ID from the agconnect-service.json file.
                    String appId = "your_APPId";

                    // Set tokenScope to HCM.
                    String tokenScope = "HCM";

                    // Delete the token.
                    HmsInstanceId.getInstance(NotificationPush.this).deleteToken(appId, tokenScope);
                    Log.i(TAG, "token deleted successfully");
                } catch (ApiException e) {
                    Log.e(TAG, "deleteToken failed." + e);
                }
            }
        }.start();
    }
}
