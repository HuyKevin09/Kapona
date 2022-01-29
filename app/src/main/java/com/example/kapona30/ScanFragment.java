package com.example.kapona30;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment affiché lors d'un appui sur le bouton Scan dans le menu du bas
 * <br>Ce fragment est appelé par {@link com.example.kapona30.MainActivity}.
 */

public class ScanFragment extends Fragment {

    /**
     * Met en place la mise en page du fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Une page
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }
}