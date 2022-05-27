package com.test_project.hello_arcore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LeftSplashActivity extends AppCompatActivity {
    /*  private ImageButton imageButton2;
  private ImageButton imageButton1;
  private ImageButton imageButton3;
  private Button arrow_button;*/

    private ImageButton imageButton;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_splash);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag)
                    imageButton.setImageResource(R.drawable.card4);
                else
                    // возвращаем первую картинку
                    imageButton.setImageResource(R.drawable.card2);
                flag = !flag;
            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
