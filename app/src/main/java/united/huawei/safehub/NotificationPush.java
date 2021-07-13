package united.huawei.safehub;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessageService;
import android.os.Bundle;

public class NotificationPush extends HmsMessageService {
    private static final String TAG = "";
    private void getToken() {
        // Create a thread.
        new Thread() {
            @Override
            public void run() {
                try {
                    // Obtain the app ID from the agconnect-service.json file.
                    String appId = "104487543";

                    // Set tokenScope to HCM.
                    String tokenScope = "HCM";
                    String token = HmsInstanceId.getInstance(NotificationPush.this).getToken(appId, tokenScope);
                    Log.i(TAG, "get token: " + token);
                    // Check whether the token is empty.
                    if(!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "Get token failed, " + e);
                }
            }
        }.start();
    }
    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "Sending token to server. token:" + token);
    }

    @Override
    public void onNewToken(String token, Bundle bundle) {
        // Obtain a token.
        Log.i(TAG, "Have received refresh token " + token);

        // Check whether the token is empty.
        if (!TextUtils.isEmpty(token)) {
            refreshedTokenToServer(token);
        }
    }

    private void refreshedTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
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
                    Log.i(TAG, "Token deleted successfully");
                } catch (ApiException e) {
                    Log.e(TAG, "DeleteToken failed." + e);
                }
            }
        }.start();
    }
}
