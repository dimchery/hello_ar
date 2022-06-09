package com.test_project.hello_arcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.widget.TextView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
public class CardsActivity extends AppCompatActivity {

    private ImageButton solarButton;
    private ImageButton himiyaButton;
    private ImageButton fishButton;
    private ImageButton matButton;
    private ImageButton skeletonButton;
    private ImageButton slonButton;
    private ImageButton arrowButton;

    private ConstraintLayout constraintLayout;
    SwipeListener swipeListener;


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
        himiyaButton = (ImageButton)findViewById(R.id.himiyaButton);
        fishButton = (ImageButton)findViewById(R.id.fishButton);
        matButton = (ImageButton)findViewById(R.id.matButton);
        skeletonButton = (ImageButton)findViewById(R.id.skeletonButton);
        slonButton = (ImageButton)findViewById(R.id.slonButton);
        arrowButton = (ImageButton)findViewById(R.id.arrow_button);

        constraintLayout = findViewById(R.id.cards_layout);
        swipeListener = new SwipeListener(constraintLayout);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        solarButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "планеты_1");
                startActivity(intent);
                CardsActivity.this.finish();
            }
        });
        himiyaButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "химия");
                startActivity(intent);
                CardsActivity.this.finish();
            }
        });
        fishButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "рыбы");
                startActivity(intent);
                CardsActivity.this.finish();
            }
        });
        matButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "matan2");
                startActivity(intent);
                CardsActivity.this.finish();
            }
        });
        skeletonButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "human");
                startActivity(intent);
                CardsActivity.this.finish();
            }
        });
        slonButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "слон");
                startActivity(intent);
                CardsActivity.this.finish();
            }
        });

        arrowButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                intent.putExtra("name", "Rabbit");
                startActivity(intent);

                CardsActivity.this.finish();
            }
        });

    }

    /*public void startMainActivity(View v) {
        Intent intent = new Intent(CardsActivity.this,MainActivity.class);
        intent.putExtra("name", "Rabbit");
        startActivity(intent);

        CardsActivity.this.finish();
    }*/

    @Override
    public void onBackPressed() {
        this.finishAffinity();
        System.gc();
        System.exit(0);
    }

    //обработчик движения
    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;
        //конструктор
        SwipeListener(View view){
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(MotionEvent e) {
                            //pass true value
                            return  true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {                            //get x and y difference
                            float xDiff = e2.getX() - e1.getX();
                            float yDiff = e2.getY() - e1.getY();
                            try {
                                //проверка условия
                                if (Math.abs(xDiff) > Math.abs(yDiff)){
                                    //Когда х больше у

                                    //проверка условия
                                    if(Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold){
                                        //When x difference is greater than threshold
                                        //When x velocity is greater than velocity threshold

                                        //проверка условия
                                        if (xDiff > 0){
                                            //Когда смахивание вправо

                                        }else {
                                            //Когда смахивание влево
                                            Intent intent = new Intent(CardsActivity.this,MainActivity.class);
                                            intent.putExtra("name", "Rabbit");
                                            startActivity(intent);
                                            CardsActivity.this.finish();
                                        }
                                        return true;
                                    }
                                }else {
                                    //Когда у больше х

                                    //проверка условия
                                    if(Math.abs(yDiff)>threshold&&Math.abs(velocityY)>velocity_threshold){
                                        //When y difference is greater than threshold
                                        //When y velocity is greater than velocity threshold

                                        if (yDiff>0){
                                            //Когда смахивание вниз
                                        }else{
                                            //Когда смахивание вверх
                                        }
                                        return true;
                                    }
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            return false;
                        }
                    };
            //детектор
            gestureDetector = new GestureDetector(listener);
            //set listener on view
            view.setOnTouchListener(this);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Return gesture event
            return gestureDetector.onTouchEvent(event);
        }
    }
}