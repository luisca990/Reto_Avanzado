package com.example.panappetit.Presentation.AccessAcount.Register.Implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.Models.MessageResponse;
import com.example.panappetit.Models.User;
import com.example.panappetit.Presentation.AccessAcount.Register.Interfaces.IRegisterUserPresenter;
import com.example.panappetit.Presentation.AccessAcount.Register.Interfaces.IRegisterUserView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;


public class RegisterFragment extends BaseFragment {

    private IRegisterUserPresenter presenter;
    private final IRegisterUserView actionPresenter = new ActionViewPresenter();
    private EditText editEmail;
    private EditText editPass;
    private EditText editConfirPass;
    private Button btnCreate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_register, container, false));
        editEmail = getCustomView().findViewById(R.id.et_email_register);
        editPass = getCustomView().findViewById(R.id.et_pass_register);
        editConfirPass = getCustomView().findViewById(R.id.et_conf_pass_register);
        btnCreate = getCustomView().findViewById(R.id.btnCrear_register);

        presenter = new RegisterUserPresenter(getContext(), actionPresenter);

        return getCustomView();
    }
    @Override
    public void onResume() {
        super.onResume();
        btnCreate.setOnClickListener(v -> {
            User user = new User();
            user.setEmail(editEmail.getText().toString());
            user.setPassword(editPass.getText().toString());
            user.setConfPassword(editConfirPass.getText().toString());
            presenter.registerUser(user);
        });
    }

    private class ActionViewPresenter implements IRegisterUserView {

        @Override
        public void responseRegisterUser(User user) {
            Toast.makeText(getContext(), getString(R.string.user_create), Toast.LENGTH_LONG).show();
            Navigation.findNavController(requireView()).navigateUp();
        }

        @Override
        public void messageError(MessageResponse message) {
            if (message.getMessage() != null) {
                dialogueFragment(R.string.Error, message.getMessage(), DialogueGenerico.TypeDialogue.ERROR);
            }
        }

        @Override
        public void showDialogFragment(int title, int detail, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, getString(detail), type);
        }
    }
}