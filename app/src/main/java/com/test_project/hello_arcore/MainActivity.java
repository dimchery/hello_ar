package com.test_project.hello_arcore;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import com.google.android.filament.Engine;
import com.google.android.filament.filamat.MaterialBuilder;
import com.google.android.filament.filamat.MaterialPackage;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.animation.ModelAnimation;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.EngineInstance;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.RenderableInstance;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.InstructionsController;
import com.google.ar.sceneform.ux.TransformableNode;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity implements
        FragmentOnAttachListener,
        BaseArFragment.OnSessionConfigurationListener,
        View.OnClickListener {

    private final List<CompletableFuture<Void>> futures = new ArrayList<>();
    private ArFragment arFragment;

    private boolean objectDetected = false;
    private AugmentedImageDatabase database;
    private Renderable objectModel;
    private Material plainVideoMaterial;
    private MediaPlayer mediaPlayer;


//    AnchorNode anchorNode;
    TransformableNode modelNode;

    private Button playButton;
    private Button stopButton;

    private ObjectAnimator modelAnimator;

    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addFragmentOnAttachListener(this);

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
        }

        if(Sceneform.isSupported(this)) {
            // .glb models can be loaded at runtime when needed or when app starts
            // This method loads ModelRenderable when app starts
            loadModel();
//            loadMatrixMaterial();
        }

        playButton = (Button)findViewById(R.id.play_button);
        playButton.setOnClickListener(this);
        playButton.setEnabled(false);
        playButton.setVisibility(View.INVISIBLE);

        stopButton = (Button)findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        stopButton.setEnabled(false);
        stopButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.play_button:
                modelAnimator.start();
                modelAnimator.setRepeatCount(0);
                Toast.makeText(this, "modelAnimator.start();", Toast.LENGTH_LONG).show();
                break;
            case R.id.stop_button:
                modelAnimator.cancel();
                Toast.makeText(this, "modelAnimator.cancel();", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        // Disable plane detection
        config.setPlaneFindingMode(Config.PlaneFindingMode.HORIZONTAL);

        // Images to be detected by our AR need to be added in AugmentedImageDatabase
        // This is how database is created at runtime
        // You can also prebuild database in you computer and load it directly (see: https://developers.google.com/ar/develop/java/augmented-images/guide#database)
        config.setFocusMode(Config.FocusMode.AUTO);

        database = new AugmentedImageDatabase(session);
        //добавляем изображения в базу
        Bitmap testImage = BitmapFactory.decodeResource(getResources(), R.drawable.rabbit);


        // Every image has to have its own unique String identifier
        database.addImage("rabbit", testImage);

        config.setAugmentedImageDatabase(database);

        // Check for image detection
        arFragment.setOnAugmentedImageUpdateListener(this::onAugmentedImageTrackingUpdate);

        // depth mode
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }

        // arcore hack. Без этого не работает автофокус...
        try {
            session.resume();
            session.pause();
            session.resume();
        } catch (CameraNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        futures.forEach(future -> {
            if (!future.isDone())
                future.cancel(true);
        });

    }

    public void onAugmentedImageTrackingUpdate(AugmentedImage augmentedImage) {
        // If there are both images already detected, for better CPU usage we do not need scan for them
        if (objectDetected) {
            return;
        }

        if (augmentedImage.getTrackingState() == TrackingState.TRACKING
                && augmentedImage.getTrackingMethod() == AugmentedImage.TrackingMethod.FULL_TRACKING) {

            // Setting anchor to the center of Augmented Image
            AnchorNode anchorNode;
            //AnchorNode anchorNode = new AnchorNode(augmentedImage.createAnchor(augmentedImage.getCenterPose()));
            Pose imgPose = augmentedImage.getCenterPose();

            // If rabbit model haven't been placed yet and detected image has String identifier of "rabbit"
            // This is also example of model loading and placing at runtime
            if (!objectDetected && augmentedImage.getName().equals("rabbit")) {
                Collection<Plane> planes = arFragment.getArSceneView().getUpdatedPlanes();
                if(planes.isEmpty()){
                    return;
                }
                float difDist = 100000f;
                Plane currentPlane = null;

                for(int i = 0; i < planes.size(); i++){
                    Plane plane = planes.iterator().next();
                    Pose planePose = plane.getCenterPose();
                    float tempDiff = (float)Math.sqrt(Math.pow(planePose.qx() - imgPose.qx(), 2)
                            + Math.pow(planePose.qy() - imgPose.qy(), 2)
                            + Math.pow(planePose.qz() - imgPose.qz(), 2));
                    if(tempDiff < difDist){
                        currentPlane = plane;
                        difDist = tempDiff;
                    }
                }

                if(currentPlane == null){
                    return;
                }

                anchorNode = new AnchorNode(currentPlane.createAnchor(augmentedImage.getCenterPose()));


                objectDetected = true;
                Toast.makeText(this, "Rabbit tag detected", Toast.LENGTH_LONG).show();

                anchorNode.setWorldScale(new Vector3(1.0f, 1.0f, 1.0f));
                arFragment.getArSceneView().getScene().addChild(anchorNode);

                modelNode = new TransformableNode(arFragment.getTransformationSystem());
                modelNode.setRenderable(objectModel);
                anchorNode.addChild(modelNode);
                modelNode.getScaleController().setMinScale(0.1f);
                modelNode.getScaleController().setMaxScale(2.0f);

                modelAnimator = modelNode.getRenderableInstance().animate(false);

                playButton.setEnabled(true);
                stopButton.setEnabled(true);
                playButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
            }
        }
        if (objectDetected) {
            arFragment.getInstructionsController().setEnabled(
                    InstructionsController.TYPE_AUGMENTED_IMAGE_SCAN, false);
        }
    }

        private void loadModel() {
        // загружаем модель
        futures.add(ModelRenderable.builder()
                .setSource(this, Uri.parse("models/untitled_anime2.glb"))
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(model -> {
                    //removing shadows for this Renderable
                    objectModel = model;
                })
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG).show();
                            return null;
                        }));
    }

}
