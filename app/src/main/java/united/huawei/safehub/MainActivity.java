package united.huawei.safehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(() -> {
            Intent loadScreen = new Intent(MainActivity.this, MainMenu.class);
            startActivity(loadScreen);
            finish();
        }, 3*1000);
    }
}