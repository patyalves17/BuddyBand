package br.com.fiap.buddyband;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    // Time that the splashscreen will be visible
    private final int SPLASH_DISPLAY_LENGTH = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadScreen();

    }

    private void loadScreen() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        Animation animText = AnimationUtils.loadAnimation(this, R.anim.animation_splash_text);
        anim.reset();
        animText.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        TextView tv = (TextView) findViewById(R.id.tvName);
        if (iv != null && tv != null) {
            iv.clearAnimation();
            tv.clearAnimation();
            iv.startAnimation(anim);
            tv.startAnimation(animText);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
