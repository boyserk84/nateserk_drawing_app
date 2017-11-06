package com.mobile.nateserk.signaturepaint;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

/**
 * DrawingFragment.java
 *
 * We need this to access history and life cycle. Otherwise, View should be sufficient.
 * Created by natek on 11/5/17.
 */

public class DrawingFragment extends Fragment {

    private final String RESET_STATE = "reset";
    private final String DRAW_STATE = "draw";
    private final String PROCESS_STATE = "process";
    private final String DONE_STATE = "command";

    private String _state = DRAW_STATE;


    public void ResetDrawingBoard() {
        _state = RESET_STATE;
        DrawingView view = (DrawingView) this.getView().findViewById(R.id.drawingBoard);

        if (view !=null) {
            Log.d("DrawingFragment Debug", "Reset is called");
            view.ResetDrawingBoardView();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawing, container, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("DrawingFragement DEBUG", "OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DrawingFragment DEBUG", "OnResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DrawingFragment DEBUG", "OnDestroy");
    }
}
