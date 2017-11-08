package com.mobile.nateserk.signaturepaint;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private DrawingFragment _drawingFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);

                    if (_drawingFragment!=null) {
                        _drawingFragment.ResetDrawingBoard();
                    }

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    if (_drawingFragment!=null) {
                        Bitmap bitmap = _drawingFragment.GetDrawnImage();
                        if (bitmap!=null) {
                            Log.d("Activity DEBUG", "Bitmap Exists! Need to test actual image.");
                            // TODO: Write to file and check if bitmap contains the image.

                            String path = Environment.getExternalStorageDirectory().toString();
                            File newFile = new File(path, "test.png");
                            FileOutputStream fos = null;
                            Log.d("Activity Debug", newFile.getPath());
                            try {
                                fos = new FileOutputStream(newFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } else {
                            // TODO: Show Error message
                            Log.d("Acitivyt DEBUG","NO BITMAP! ABORT!!!!");
                        }
                    }

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        _drawingFragment = (DrawingFragment) getFragmentManager().findFragmentById(R.id.drawingBoardFragment);
    }
}
