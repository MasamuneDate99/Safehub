package united.huawei.safehub;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {
//    private TextView dataCovid;
//    private TextView titleMain;
//    private TextView titleDaily;
    private TextView dataCovidplus, dataCoviddead, dataCovidrawat, dataCovidsembuh, DataSemua_PDP, DataSemua_ODP;
    private TextView tgl;
    private Button mapbtn;
    private LinearLayout greenCard, redCard, yellowCard;
    private RequestQueue dataRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        greenCard = findViewById(R.id.green_rect_shape);
        greenCard.setBackgroundColor(Color.parseColor("#27E874"));
        yellowCard = findViewById(R.id.yellow_rect_shape);
        yellowCard.setBackgroundColor(Color.parseColor("#F2C524"));
        yellowCard = findViewById(R.id.yellow_rect_shape2);
        yellowCard.setBackgroundColor(Color.parseColor("#F2C524"));
        redCard = findViewById(R.id.red_rect_shape);
        redCard.setBackgroundColor(Color.parseColor("#F23124"));
        redCard = findViewById(R.id.red_rect_shape2);
        redCard.setBackgroundColor(Color.parseColor("#F23124"));
        mapbtn= findViewById(R.id.maps);

        ImageButton privacyBtn = findViewById(R.id.privacyPolicy);
        dataCovidplus = findViewById(R.id.DataHarianCovid_jmlpositif);
        dataCoviddead = findViewById(R.id.DataHarianCovid_jmlmeninggal);
        dataCovidrawat = findViewById(R.id.DataHarianCovid_jmldirawat);
        dataCovidsembuh = findViewById(R.id.DataHarianCovid_jmlsembuh);
        DataSemua_PDP = findViewById(R.id.DataSemua_PDP);
        DataSemua_ODP = findViewById(R.id.DataSemua_ODP);
        tgl = findViewById(R.id.tanggal);

//        dataCovid = findViewById(R.id.DataSemua);
//        Button dailyBtn = findViewById(R.id.dailyCase);
//        titleMain = findViewById(R.id.MainTitle);
//        titleDaily = findViewById(R.id.DailyTitle);

        dataRequest = Volley.newRequestQueue(this);
        jsonParse();

        mapbtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, MapsActivity.class)));
        privacyBtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, PrivacyPolicy.class)));
//        dailyBtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, DailyCase.class)));
        permission();

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

                        tgl.setText((TanggalKasus));
                        dataCovidplus.setText((Positif));
                        dataCoviddead.setText((Meninggal));
                        dataCovidrawat.setText((Dirawat));
                        dataCovidsembuh.setText((Sembuh));
                        DataSemua_ODP.setText((Odp));
                        DataSemua_PDP.setText((Pdp));

//                        dataCovid.append("Jumlah ODP : " + Odp + "\n"
//                                + "Jumlah PDP : " + Pdp + "\n");

//                        dataCovidB.append("Jumlah Positif : " + Positif + "\n"
//                                + "Jumlah Meninggal : " + Meninggal + "\n"
//                                + "Jumlah Sembuh : " + Sembuh + "\n"
//                                + "Jumlah Dirawat : " + Dirawat + "\n");
//                                + "Tanggal Kasus Harian : " + TanggalKasus + "\n\n");

//                        titleMain.append("Indonesian COVID-19 Information");
//                        titleDaily.append("Daily Case");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        dataRequest.add(request);
    }



}
