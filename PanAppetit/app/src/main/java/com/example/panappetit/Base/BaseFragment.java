package com.example.panappetit.Base;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;
import com.example.panappetit.Utils.Util;

public class BaseFragment extends Fragment {

    private View view;

    public View getCustomView() {
        return view;
    }
    public void setCustomView(View view) {
        this.view = view;
    }

    public void dialogueFragment(int title, String detail, DialogueGenerico.TypeDialogue type) {
        DialogueGenerico.getInstance()
                .withTitle(title)
                .withText(detail)
                .withTypeDialogue(type)
                .withTextBtnAccept(R.string.btn_aceptar)
                .withActionBtnAccept(() -> {
                    Log.e("", "");
                });

        if (getContext() != null) {
            Util.showDialogueGenerico(getContext());
        }
    }
}
