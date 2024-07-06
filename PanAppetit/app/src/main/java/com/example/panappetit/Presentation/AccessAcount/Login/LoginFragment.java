package com.example.panappetit.Presentation.AccessAcount.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.Models.User;
import com.example.panappetit.R;

public class LoginFragment extends BaseFragment {

    private Button btnSeccion;
    private TextView btnTextRegister;
    private EditText editEmail;
    private EditText editPass;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_login, container, false));

        btnSeccion = getCustomView().findViewById(R.id.btn_start_section);
        btnTextRegister = getCustomView().findViewById(R.id.tv_register_user);
        editEmail = getCustomView().findViewById(R.id.et_email_login);
        editPass = getCustomView().findViewById(R.id.et_Pass_Login);

        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        btnSeccion.setOnClickListener(v->{
            user.setEmail(editEmail.getText().toString());
            user.setPassword(editPass.getText().toString());

        });
        btnTextRegister.setOnClickListener(v->{
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}