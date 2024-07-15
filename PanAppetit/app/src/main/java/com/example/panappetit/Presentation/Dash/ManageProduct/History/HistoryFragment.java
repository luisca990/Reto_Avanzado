package com.example.panappetit.Presentation.Dash.ManageProduct.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.VentaDao;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.Venta;
import com.example.panappetit.Presentation.Dash.ManageProduct.History.Adapter.OnItemClickListenerHistory;
import com.example.panappetit.Presentation.Dash.ManageProduct.History.Interfaces.IHistoryView;
import com.example.panappetit.R;
import com.example.panappetit.Presentation.Dash.ManageProduct.History.Adapter.RecyclerAdapterHistorial;
import com.example.panappetit.Utils.DialogueGenerico;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends BaseFragment {
    private HistoryPresenter presenter;
    private List<Venta> ventas = new ArrayList<>();
    private ImageView arrow;
    private RecyclerAdapterHistorial adapter;
    private VentaDao dao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_history, container, false));

        arrow = getCustomView().findViewById(R.id.iv_return_history);
        RecyclerView rv = getCustomView().findViewById(R.id.rv_history);
        dao = new VentaDao(getContext());
        presenter = new HistoryPresenter(new listenerPresenter(), dao);

        adapter = new RecyclerAdapterHistorial(getContext(), ventas, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);

        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        SessionManager sessionManager = new SessionManager(requireContext());
        presenter.getAllVentas(sessionManager.getUseId());
        arrow.setOnClickListener(v -> Navigation.findNavController(requireView()).navigateUp());
    }

    private class listenerPresenter implements IHistoryView {
        @Override
        public void getAllVentas(List<Venta> list) {
            ventas = list;
            adapter.updateList(ventas);
        }

        @Override
        public void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type) {

        }
    }

    private static class listenerAdapter implements OnItemClickListenerHistory{
        @Override
        public void onItemClick(Venta venta) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }
}