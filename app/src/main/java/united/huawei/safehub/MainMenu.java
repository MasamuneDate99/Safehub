package united.huawei.safehub;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends AppCompatActivity {
    private TextView dataCovidplus, dataCoviddead, dataCovidrawat, dataCovidsembuh, DataSemua_PDP, DataSemua_ODP;
    private TextView tgl;
    private Button mapbtn, ChangeAccBtn, CancelAuth, SignIn;
    private RequestQueue dataRequest;

    // Account Kit Stuff
    private AccountAuthService mAuthService;
    private AccountAuthParams mAuthParam;
    private static final int REQUEST_CODE_SIGN_IN = 1000;
    private static final String TAG = "Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mapbtn = findViewById(R.id.maps);
        ImageButton privacyBtn = findViewById(R.id.privacyPolicy);
        dataCovidplus = findViewById(R.id.DataHarianCovid_jmlpositif);
        dataCoviddead = findViewById(R.id.DataHarianCovid_jmlmeninggal);
        dataCovidrawat = findViewById(R.id.DataHarianCovid_jmldirawat);
        dataCovidsembuh = findViewById(R.id.DataHarianCovid_jmlsembuh);
        DataSemua_PDP = findViewById(R.id.DataSemua_PDP);
        DataSemua_ODP = findViewById(R.id.DataSemua_ODP);
        tgl = findViewById(R.id.tanggal);

        // Account Kit
        //ChangeAccBtn = findViewById(R.id."ID Logout Button");
        //SignIn = findViewByID(R.id."ID SIGN IN");
        //CancelAuth = findViewByID(R.id."Cancel ID");

        dataRequest = Volley.newRequestQueue(this);
        jsonParse();

        mapbtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, MapsActivity.class)));
        privacyBtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, PrivacyPolicy.class)));

        ChangeAccBtn.setOnClickListener(v -> signOut());
        CancelAuth.setOnClickListener(v -> cancelAuthorization());
        SignIn.setOnClickListener(v -> silentSignInByHwId());
        permission();
    }
    private void silentSignInByHwId() {
        // 1. Use AccountAuthParams to specify the user information to be obtained, including the user ID (OpenID and UnionID), email address, and profile (nickname and picture).
        // 2. By default, DEFAULT_AUTH_REQUEST_PARAM specifies two items to be obtained, that is, the user ID and profile.
        // 3. If your app needs to obtain the user's email address, call setEmail().
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setEmail()
                .createParams();
        // Use AccountAuthParams to build AccountAuthService.
        mAuthService = AccountAuthManager.getService(this, mAuthParam);
        // Use silent sign-in to sign in with a HUAWEI ID.
        Task<AuthAccount> task = mAuthService.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                // The silent sign-in is successful. Process the returned account object AuthAccount to obtain the HUAWEI ID information.
                dealWithResultOfSignIn(authAccount);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // The silent sign-in fails. Use the getSignInIntent() method to show the authorization or sign-in screen.
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    Intent signInIntent = mAuthService.getSignInIntent();
                    startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
                }
            }
        });
    }
    private void dealWithResultOfSignIn(AuthAccount authAccount) {
        // Obtain the HUAWEI DI information.
        Log.i(TAG, "display name:" + authAccount.getDisplayName());
        Log.i(TAG, "photo uri string:" + authAccount.getAvatarUriString());
        Log.i(TAG, "photo uri:" + authAccount.getAvatarUri());
        Log.i(TAG, "email:" + authAccount.getEmail());
        Log.i(TAG, "openid:" + authAccount.getOpenId());
        Log.i(TAG, "unionid:" + authAccount.getUnionId());
        // TODO: Implement service logic after the HUAWEI ID information is obtained.
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                // The sign-in is successful, and the authAccount object that contains the HUAWEI ID information is obtained.
                AuthAccount authAccount = authAccountTask.getResult();
                dealWithResultOfSignIn(authAccount);
                Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
            } else {
                // The sign-in fails. Find the failure cause from the status code. For more information, please refer to the "Error Codes" section in the API Reference.
                Log.e(TAG, "sign in failed : " +((ApiException)authAccountTask.getException()).getStatusCode());
            }
        }
    }
    private void signOut() {
        Task<Void> signOutTask = mAuthService.signOut();
        signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "signOut Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "signOut fail");
            }
        });
    }
    private void cancelAuthorization() {
        Task<Void> task = mAuthService.cancelAuthorization();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "cancelAuthorization success");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "cancelAuthorization failure:" + e.getClass().getSimpleName());
            }
        });
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
                        //yyyy-MM-dd
                        Date date1 = null;
                        try{
                            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(TanggalKasus);
                        }catch(Exception e){ }
                        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy");
                        String date2 = sdf.format(date1);
                        tgl.setText(date2);

                        dataCovidplus.append("" + Positif );
                        dataCoviddead.append("" + Meninggal );
                        dataCovidrawat.append("" + Dirawat);
                        dataCovidsembuh.append("" + Sembuh);
                        DataSemua_ODP.append("" + Odp);
                        DataSemua_PDP.append("" + Pdp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        dataRequest.add(request);
    }
}
