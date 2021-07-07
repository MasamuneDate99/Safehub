package united.huawei.safehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button mapbtn= findViewById(R.id.maps);
        Button privacyBtn = findViewById(R.id.privacyPolicy);
        Button dailyBtn = findViewById(R.id.dailyCase);

        mapbtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, MapsActivity.class)));
        privacyBtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, PrivacyPolicy.class)));
        dailyBtn.setOnClickListener(v -> startActivity(new Intent(MainMenu.this, DailyCase.class)));
    }
}
