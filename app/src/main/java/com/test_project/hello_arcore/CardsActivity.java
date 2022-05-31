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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        solarButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "untitled_anime");
                startActivity(intent);
            }
        });
        himiyaButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "chemistry");
                startActivity(intent);
            }
        });
        fishButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "fish");
                startActivity(intent);
            }
        });
        matButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "Video");
                startActivity(intent);
            }
        });
        skeletonButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "skeleton");
                startActivity(intent);
            }
        });
        slonButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "slon");
                startActivity(intent);
            }
        });

    }

    public void startNewActivity(View v) {
        Intent intent = new Intent(CardsActivity.this,MainActivity.class);
        intent.putExtra("name", "Rabbit");
        startActivity(intent);
    }


}