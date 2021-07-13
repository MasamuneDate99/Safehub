package united.huawei.safehub;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DailyCase extends AppCompatActivity {
    private TextView dataCovid;
    private TextView titleMain;
    private TextView titleDaily;
    private TextView dataCovidB;
    private RequestQueue dataRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_case);

        dataCovidB = findViewById(R.id.DataHarianCovid);
        dataCovid = findViewById(R.id.DataSemua);
        titleMain = findViewById(R.id.MainTitle);
        titleDaily = findViewById(R.id.DailyTitle);
        dataRequest = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {
        String dataSource = "https://data.covid19.go.id/public/api/update.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, dataSource, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject dataHarian = response.getJSONObject("data");
                            JSONObject dataTotal = response.getJSONObject("update").getJSONObject("penambahan");

                            int Odp = dataHarian.getInt("id");
                            int Pdp = dataHarian.getInt("jumlah_odp");

                            int Positif = dataTotal.getInt("jumlah_positif");
                            int Meninggal = dataTotal.getInt("jumlah_meninggal");
                            int Sembuh = dataTotal.getInt("jumlah_sembuh");
                            int Dirawat = dataTotal.getInt("jumlah_dirawat");
                            String TanggalKasus = dataTotal.getString("tanggal");

                            dataCovid.append("Jumlah ODP : " + String.valueOf(Odp) + "\n"
                                + "Jumlah PDP : " + String.valueOf(Pdp) + "\n");

                            dataCovidB.append("Jumlah Positif : " + String.valueOf(Positif) + "\n"
                                    + "Jumlah Meninggal : " + String.valueOf(Meninggal) + "\n"
                                    + "Jumlah Sembuh : " + String.valueOf(Sembuh) + "\n"
                                    + "Jumlah Dirawat : " + String.valueOf(Dirawat) + "\n"
                                    + "Tanggal Kasus Harian : " + TanggalKasus + "\n\n");
                            titleMain.append("Indonesian COVID-19 Information");
                            titleDaily.append("Daily Case");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        dataRequest.add(request);
    }
}
