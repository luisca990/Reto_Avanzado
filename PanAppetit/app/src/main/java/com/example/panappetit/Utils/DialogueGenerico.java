package com.example.panappetit.Utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.panappetit.R;

public class DialogueGenerico extends DialogFragment {

    private static DialogueGenerico instance = null;

    public static DialogueGenerico getInstance() {
        if (instance == null) {
            instance = new DialogueGenerico();
        }
        return instance;
    }

    private View containerPrincipal;
    private ImageView imageDialog;
    private TextView detailsMessage;
    private TextView title;
    private Button btnAccept;
    private Button btnCancel;
    private Guideline guideLine;

    private Runnable invokeActionBtnAccept;
    private Runnable invokeActionBtnCancel;
    private Integer routeTitle;
    private String routeText;
    private Integer routeTextBtnAccept;
    private Integer routeTextBtnCancel;
    private TypeDialogue typeDialog = TypeDialogue.OK;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        containerPrincipal = inflater.inflate(R.layout.dialogo_generico, container, false);
        setCancelable(false);

        findElementsView();
        loadViewInformation();
        return containerPrincipal;
    }

    private void findElementsView() {
        imageDialog = containerPrincipal.findViewById(R.id.image_dialog);
        detailsMessage = containerPrincipal.findViewById(R.id.detail_message);
        title = containerPrincipal.findViewById(R.id.title_dialog);
        btnAccept = containerPrincipal.findViewById(R.id.btn_accept_dialog);
        btnCancel = containerPrincipal.findViewById(R.id.btn_cancel_dialog);
        guideLine = containerPrincipal.findViewById(R.id.line_dialog);

        addListeners();
    }

    private void loadViewInformation() {
        selectIcon();
        introduceTitle();
        introduceMessage();
        introduceBtnAccept();
        introduceBtnCancel();
    }

    private void selectIcon() {
        imageDialog.setImageResource(typeDialog.getIcono());
    }

    private void introduceTitle() {
        if (routeTitle == null) {
            title.setVisibility(View.GONE);
            return;
        }
        title.setVisibility(View.VISIBLE);
        title.setText(routeTitle);
    }

    private void introduceMessage() {
        if (routeText == null) {
            detailsMessage.setVisibility(View.GONE);
            return;
        }
        detailsMessage.setVisibility(View.VISIBLE);
        detailsMessage.setText(routeText);
    }

    private void introduceBtnAccept() {
        if (routeTextBtnAccept == null) {
            btnAccept.setText(R.string.btn_aceptar);
            return;
        }
        btnAccept.setText(routeTextBtnAccept);
    }

    private void introduceBtnCancel() {
        if (routeTextBtnCancel == null) {
            btnCancel.setVisibility(View.GONE);
            guideLine.setGuidelinePercent(1.0f);
            return;
        }
        guideLine.setGuidelinePercent(0.5f);
        btnCancel.setVisibility(View.VISIBLE);
        btnCancel.setText(routeTextBtnCancel);
    }

    private void addListeners() {
        btnAccept.setOnClickListener(v -> {
            dismiss();
            if (invokeActionBtnAccept != null) {
                invokeActionBtnAccept.run();
            }
            clearViewElements();
        });

        btnCancel.setOnClickListener(v -> {
            dismiss();
            if (invokeActionBtnCancel != null) {
                invokeActionBtnCancel.run();
            }
            clearViewElements();
        });
    }

    private void clearViewElements() {
        routeTextBtnCancel = null;
        invokeActionBtnAccept = null;
        invokeActionBtnCancel = null;
    }

    @Override
    public void dismiss() {
        if (getFragmentManager() == null) {
            return;
        }
        super.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (isAdded()) {
            return;
        }
        super.show(manager, tag);
    }

    public enum TypeDialogue {
        ERROR, ADVERTENCIA, OK;

        public int getIcono() {
            switch (this) {
                case OK:
                    return R.drawable.ic_check;
                case ADVERTENCIA:
                    return R.drawable.ic_warning;
                case ERROR:
                    return R.drawable.ic_close;
                default:
                    return -1;
            }
        }
    }

    public DialogueGenerico withActionBtnAccept(Runnable actionAccept) {
        this.invokeActionBtnAccept = actionAccept;
        return this;
    }

    public DialogueGenerico withActionBtnCancel(Runnable actionCancel) {
        this.invokeActionBtnCancel = actionCancel;
        return this;
    }

    public DialogueGenerico withTitle(@StringRes int routeString) {
        this.routeTitle = routeString;
        return this;
    }

    public DialogueGenerico withText(String routeString) {
        this.routeText = routeString;
        return this;
    }

    public DialogueGenerico withTextBtnAccept(@StringRes int routeString) {
        this.routeTextBtnAccept = routeString;
        return this;
    }

    public DialogueGenerico withTextBtnCancel(@StringRes int routeString) {
        this.routeTextBtnCancel = routeString;
        return this;
    }

    public DialogueGenerico withTypeDialogue(TypeDialogue typeDialogue) {
        this.typeDialog = typeDialogue;
        return this;
    }

    public void showDialogue(FragmentManager fragmentManager, String tag) {
        show(fragmentManager, tag);
    }
}
