package com.example.panappetit.Presentation.AccessAcount;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.R;

public class SplashFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_splash, container, false));
        new Handler().postDelayed(() -> Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment), 4000);
        return getCustomView();
    }
}