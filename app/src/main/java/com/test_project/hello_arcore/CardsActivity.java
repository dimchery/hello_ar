package com.test_project.hello_arcore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.widget.TextView;

public class CardsActivity extends AppCompatActivity {

    private Button arrow_button;
    private ImageButton solarButton;
    private TextView solarTextCard;
    private ImageButton himiyaButton;
    private ImageButton fishButton;
    private ImageButton matButton;
    private ImageButton skeletonButton;
    private ImageButton slonButton;
    private boolean flag = true;
    private Intent arcoreClass;
    //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
    //View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //перекрытие разметки (панель накладывается на разметку)
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //setContentView(R.layout.activity_main);

        //сокрытие строки состояния и маскировка навигации
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setContentView(R.layout.activity_cards);
        solarButton = (ImageButton)findViewById(R.id.solarButton);
        solarTextCard = (TextView)findViewById(R.id.solarTextCard);
        himiyaButton = (ImageButton)findViewById(R.id.himiyaButton);
        fishButton = (ImageButton)findViewById(R.id.fishButton);
        matButton = (ImageButton)findViewById(R.id.matButton);
        skeletonButton = (ImageButton)findViewById(R.id.skeletonButton);
        slonButton = (ImageButton)findViewById(R.id.slonButton);
        arrow_button = (Button)findViewById(R.id.arrow_button);

        //сокрытие строки состояния и навигации
        /*decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);*/


        solarButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "solar.glb");
                startActivity(intent);
            }
        });
        himiyaButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "chemistry.glb");
                startActivity(intent);
            }
        });
        fishButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "fish.glb");
                startActivity(intent);
            }
        });
        matButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "math.glb");
                startActivity(intent);
            }
        });
        skeletonButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "skeleton.glb");
                startActivity(intent);
            }
        });
        slonButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "slon.glb");
                startActivity(intent);
            }
        });

    }

    public void startNewActivity(View v) {
        super.onBackPressed();
    }


}