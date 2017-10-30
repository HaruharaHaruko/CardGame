package co.roulette.cardgame;

import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, new GameFragment()).commit();
    }
}
