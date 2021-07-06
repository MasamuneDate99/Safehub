package united.huawei.safehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;

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
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-6.218805, 106.802604),15));
        // Add the marker to the cluster.
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.211260, 106.796535)).title("Puskesmas Gelora Senayan").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.195957, 106.846654)).title("Rumah Sakit Cipto Mangumkusumo").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.218196503919996, 106.81640948485537)).title("Rumah Sakit Jakarta").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.226729005950587, 106.7883428491708)).title("Rumah Sakit Simprug Pertamina").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.240935314100641, 106.79263438385833)).title("Rumah Sakit Pusat Pertamina").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.217108600054092, 106.81804026799998)).title("Rumah Sakit Siloam").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.208063978165238, 106.81949938974006)).title("Rumah Sakit Murni Teguh Sudirman Jakarta").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.198336569445448, 106.80113162196993)).title("Rumah Sakit Bhakti Mulia").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.189120964651131, 106.81503619384621)).title("RSUD Tanah Abang").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.189291625241268, 106.82791079667307)).title("Rumah Sakit YPK Mandiri").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.179478539113008, 106.8180402678095)).title("Rumah Sakit Budi Kemuliaan").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.17000660520187, 106.81108798187137)).title("RSUD Tarakan Jakarta").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.1673608131172815, 106.79723812887653)).title("Rumah Sakit Sumber Waras").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.189120517666091, 106.76350666854033)).title("Siloam Hospitals Kebon Jeruk").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.165910134193234, 106.78530766340273)).title("Rumah Sakit Royal Taruma").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.120784585242261, 106.75036546973853)).title("Rumah Sakit Ibu dan Anak Grand Family").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.108324596554203, 106.75379869673127)).title("Rumah Sakit Pantai Indah Kapuk").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.149117176154545, 106.7148315641289)).title("RS Hermina Daan Mogot").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.170962962858858, 106.887866232428)).title("Rumah Sakit Kartika Pulomas").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.199463533325456, 106.88597795722652)).title("RSUP Persahabatan").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.169085626144801, 106.88254472960273)).title("Rumah Sakit Pertamina Jaya").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.14216972900335, 106.86990495828208)).title("Rumah Sakit Hermina Podomoro").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.1476313315658615, 106.8980574245681)).title("RS. Mitra Keluarga Kelapa Gading").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.134830613120566, 106.86767336009774)).title("RS Satya Negara").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.161455761492258, 106.91488023974256)).title("Rumah Sakit Gading Pluit").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.114037483518899, 106.89326472651516)).title("RSUD Kecamatan Tanjung Priok").clusterable(true));
        hMap.addMarker(new MarkerOptions().position(new LatLng(-6.151816152998234, 106.86052658374884)).title("Rumah Sakit Darurat Penanganan COVID19 Wisma Atlet Kemayoran\n").clusterable(true));

        hMap.setMarkersClustering(true);
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