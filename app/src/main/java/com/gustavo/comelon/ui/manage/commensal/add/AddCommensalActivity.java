package com.gustavo.comelon.ui.manage.commensal.add;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gustavo.comelon.R;
import com.gustavo.comelon.adapter.NamePosCommensalAdapter;
import com.gustavo.comelon.model.Commensal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCommensalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.recyclerview_name_pos_commensal)
    RecyclerView rvNamePosCommensal;

    private List<Commensal> commensals;
    private NamePosCommensalAdapter adapterNamePos;
    private String numMealsToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commensal);
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
        rvNamePosCommensal.setHasFixedSize(false);
        adapterNamePos = new NamePosCommensalAdapter(commensals, this);
        adapterNamePos.setListener(position -> {
            createAlertDialog(position);
        });
        rvNamePosCommensal.setLayoutManager(new LinearLayoutManager(this));
        rvNamePosCommensal.setAdapter(adapterNamePos);

    }

    private void createAlertDialog(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.alert_dialog_add_commensal, null);

        final TextView nameCommensal;
        final EditText edtxtNumberMealsToAdd;

        nameCommensal = viewInflated.findViewById(R.id.txt_name_commensal);
        edtxtNumberMealsToAdd = viewInflated.findViewById(R.id.edtxt_number_meals_to_add);

        String nameFull = commensals.get(pos).getName() + " " + commensals.get(pos).getSurname();
        nameCommensal.setText(nameFull);

        builder.setView(viewInflated);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                numMealsToAdd = edtxtNumberMealsToAdd.getText().toString();
                Toast.makeText(AddCommensalActivity.this, "Comensal agregado a "+ numMealsToAdd + " comidas", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                Toast.makeText(AddCommensalActivity.this, "Se ha agregado correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();

    }
}
