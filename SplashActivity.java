package internship.batch1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        sp = getSharedPreferences(ConstantUrl.PREF, MODE_PRIVATE);
        imageView = findViewById(R.id.splash_iv);
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2500);
        imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getString(ConstantUrl.ID, "").equals("")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);

    }
}