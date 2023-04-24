package com.example.harman_mann_n01585147_problem_solving_assessment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WV_Fragment extends Fragment {
    String url;
    public WV_Fragment(String url) {
        this.url = url;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        WebView webView = requireView().findViewById(R.id.wv);
        webView.loadUrl(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_w_v_, container, false);
    }
}