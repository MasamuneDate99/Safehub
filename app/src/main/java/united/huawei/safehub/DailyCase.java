package united.huawei.safehub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DailyCase extends AppCompatActivity {
    private TextView dataCovid;
    private RequestQueue dataRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_case);

        dataCovid = findViewById(R.id.DataHarianCovid);
        Button btnParse = findViewById(R.id.RequestData);
        dataRequest = Volley.newRequestQueue(this);
        btnParse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String dataSource = "https://data.covid19.go.id/public/api/update.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, dataSource, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray jsonArray = response.getJSONArray("data");
                            //for (int i = 0;i < jsonArray.length(); i++) {
                                JSONObject dataHarian = response.getJSONObject("data");
                                JSONObject dataTotal = response.getJSONObject("update").getJSONObject("penambahan");

                                int Odp = dataHarian.getInt("id");
                                int Pdp = dataHarian.getInt("jumlah_odp");

                                int Positif = dataTotal.getInt("jumlah_positif");
                                int Meninggal = dataTotal.getInt("jumlah_meninggal");
                                int Sembuh = dataTotal.getInt("jumlah_sembuh");
                                int Dirawat = dataTotal.getInt("jumlah_dirawat");
                                String TanggalKasus = dataTotal.getString("tanggal");

                                dataCovid.append("COVID-19 Data" + "\n"
                                + "Jumlah ODP : " + String.valueOf(Odp) + "\n"
                                + "Jumlah PDP : " + String.valueOf(Pdp) + "\n"
                                + "Jumlah Positif : " + String.valueOf(Positif) + "\n"
                                + "Jumlah Meninggal : " + String.valueOf(Meninggal) + "\n"
                                + "Jumlah Sembuh" + String.valueOf(Sembuh) + "\n"
                                + "Jumlah Dirawat" + String.valueOf(Dirawat) + "\n"
                                + "Tanggal Kasus Harian" + TanggalKasus + "\n\n");
                            //}
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
