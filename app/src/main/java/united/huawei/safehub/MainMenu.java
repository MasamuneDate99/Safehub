package united.huawei.safehub;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;
import com.huawei.hms.analytics.type.HAEventType;
import com.huawei.hms.analytics.type.HAParamType;
import com.huawei.hms.common.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {
    private TextView dataCovid;
    private TextView titleMain;
    private TextView titleDaily;
    private TextView dataCovidB;
    private RequestQueue dataRequest;
    private static final String TAG = "PushKit Token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        HiAnalyticsTools.enableLog();
        HiAnalyticsInstance instance = HiAnalytics.getInstance(MainMenu.this);

        Bundle bundle = new Bundle();
        bundle.putString("exam_difficulty", "high");
        bundle.putString("exam_level", "1-1");
        bundle.putString("exam_time", "20190520-08");
        instance.onEvent("begin_examination", bundle);

        Bundle bundle_pre = new Bundle();
        bundle_pre.putString(HAParamType.PRODUCTID, "item_ID");
        bundle_pre.putString(HAParamType.PRODUCTNAME, "name");
        bundle_pre.putString(HAParamType.CATEGORY, "category");
        bundle_pre.putLong(HAParamType.QUANTITY, 100L);
        bundle_pre.putDouble(HAParamType.PRICE, 10.01);
        bundle_pre.putDouble(HAParamType.REVENUE, 10);
        bundle_pre.putString(HAParamType.CURRNAME, "currency");
        bundle_pre.putString(HAParamType.PLACEID, "location_ID");
        instance.onEvent(HAEventType.ADDPRODUCT2WISHLIST, bundle_pre);

        Button mapbtn= findViewById(R.id.maps);
        Button privacyBtn = findViewById(R.id.privacyPolicy);
        Button dailyBtn = findViewById(R.id.dailyCase);

        //dataCovidB = findViewById(R.id.DataHarianCovid);
        //dataCovid = findViewById(R.id.DataSemua);
        //titleMain = findViewById(R.id.MainTitle);
        //titleDaily = findViewById(R.id.DailyTitle);
        //dataRequest = Volley.newRequestQueue(this);
        //jsonParse();

        /*
        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("united.huawei.safehub.ON_NEW_TOKEN");
        MainMenu.this.registerReceiver(receiver, filter);
        getToken();
        */

        mapbtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, MapsActivity.class)));
        privacyBtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, PrivacyPolicy.class)));
        dailyBtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, DailyCase.class)));
        permission();
    }
    /*
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
                    String token = HmsInstanceId.getInstance(MainMenu.this).getToken(appId, tokenScope);
                    Log.i(TAG, "get token: " + token);

                    // Check whether the token is empty.
                    if(!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "get token failed, " + e);
                }
            }
        }.start();
    }
    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }*/

    private void jsonParse() {
        String dataSource = "https://data.covid19.go.id/public/api/update.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, dataSource, null,
                response -> {
                    try {
                        JSONObject dataHarian = response.getJSONObject("data");
                        JSONObject dataTotal = response.getJSONObject("update").getJSONObject("penambahan");

                        int Odp = dataHarian.getInt("jumlah_odp");
                        int Pdp = dataHarian.getInt("jumlah_pdp");

                        int Positif = dataTotal.getInt("jumlah_positif");
                        int Meninggal = dataTotal.getInt("jumlah_meninggal");
                        int Sembuh = dataTotal.getInt("jumlah_sembuh");
                        int Dirawat = dataTotal.getInt("jumlah_dirawat");
                        String TanggalKasus = dataTotal.getString("tanggal");

                        dataCovid.append("Jumlah ODP : " + Odp + "\n"
                                + "Jumlah PDP : " + Pdp + "\n");

                        dataCovidB.append("Jumlah Positif : " + Positif + "\n"
                                + "Jumlah Meninggal : " + Meninggal + "\n"
                                + "Jumlah Sembuh : " + Sembuh + "\n"
                                + "Jumlah Dirawat : " + Dirawat + "\n"
                                + "Tanggal Kasus Harian : " + TanggalKasus + "\n\n");
                        titleMain.append("Indonesian COVID-19 Information");
                        titleDaily.append("Daily Case");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        dataRequest.add(request);
    }

    void permission(){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, "android.permission.ACCESS_BACKGROUND_LOCATION",  Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION",  Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, strings, 2);
            }
        }
    }
}
