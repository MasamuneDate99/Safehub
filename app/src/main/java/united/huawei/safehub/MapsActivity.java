package united.huawei.safehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,  HuaweiMap.OnMyLocationButtonClickListener {
    private SupportMapFragment mSupportMapFragment;
    private HuaweiMap hMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapsInitializer.setApiKey("CgB6e3x9CrCsI3bZIZ0Th5b5BsxKfHX4JHn+Z35tjymZamno2NSIB5S9Nyxg/QCBBskLvmT57fzJHpP8ntS2Cgfq");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        permission();
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
        mSupportMapFragment.getMapAsync(this);
    }
    public void onMapReady(HuaweiMap huaweiMap) {
        hMap = huaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.getUiSettings().setMyLocationButtonEnabled(true);
        hMap.setOnMyLocationButtonClickListener(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    void permission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check whether your app has the specified permission and whether the app operation corresponding to the permission is allowed.
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permissions for your app.
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                // Request permissions.
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        }


    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}