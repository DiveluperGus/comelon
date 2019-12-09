package com.gustavo.comelon.ui.manage.commensal.consult;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gustavo.comelon.R;
import com.gustavo.comelon.adapter.StatusCommensalAdapter;
import com.gustavo.comelon.model.Commensal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsultCommensalsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.recyclerview_status_commensal)
    RecyclerView rvStatusCommensal;

    private StatusCommensalAdapter adapterStatusCom;
    private List<Commensal> commensals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_commensals);
        ButterKnife.bind(this);

        setToolbar();
        loadCommensals();
        configRecyclerView();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Administrar comensales");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    private void loadCommensals() {
        commensals = new ArrayList<>();
        commensals.add(new Commensal("Gustavo", "Reyes", "gustavo@gmail.com", "2721244358", 2, false, 1, 0, true));
        commensals.add(new Commensal("David", "Sanchez", "david@gmail.com", "2721244310", 2, false, 2, 0, true));
        commensals.add(new Commensal("Itzel", "Cancio", "itzel@gmail.com", "2721244320", 2, false, 3, 0, true));
        commensals.add(new Commensal("Fernando", "Andrade", "fernando@gmail.com", "2721244330", 2, false, 4, 0, true));
        commensals.add(new Commensal("Celeste", "Pérez", "celeste@gmail.com", "2721244340", 2, false, 1, 0, true));
        commensals.add(new Commensal("Carlos", "Martinez", "carlos@gmail.com", "2721244350", 2, false, 4, 0, true));
        commensals.add(new Commensal("Andres", "Esteban", "andres@gmail.com", "2721244360", 2, false, 2, 0, true));
        commensals.add(new Commensal("Salvador", "Gonzalez", "salvador@gmail.com", "2721244370", 2, false, 3, 0, true));
        commensals.add(new Commensal("Aracely", "Reyes", "aracely@gmail.com", "2721244380", 2, false, 4, 0, true));
        commensals.add(new Commensal("José Antonio", "Pintor", "antonio@gmail.com", "2721244390", 2, false, 1, 0, true));
    }

    private void configRecyclerView() {
        rvStatusCommensal.setHasFixedSize(false);
        adapterStatusCom = new StatusCommensalAdapter(commensals,getApplicationContext());
        rvStatusCommensal.setLayoutManager(new LinearLayoutManager(this));
        rvStatusCommensal.setAdapter(adapterStatusCom);
    }
}
