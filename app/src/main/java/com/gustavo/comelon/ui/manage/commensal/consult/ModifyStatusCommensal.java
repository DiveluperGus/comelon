package com.gustavo.comelon.ui.manage.commensal.consult;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyStatusCommensal extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.txt_name_commensal)
    TextView txtNameCommensal;
    @BindView(R.id.checkbox_meal_paidUp)
    CheckBox cbMealPaidUp;
    @BindView(R.id.checkbox_meal_delivered)
    CheckBox cbMealDelivered;
    @BindView(R.id.btn_accept_changes)
    MaterialButton btnAcceptChanges;
    @BindView(R.id.btn_delete_commensal)
    MaterialButton btnDeleteCommensal;

    private Bundle args;
    private String nameCommensal, surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_status_commensal);
        ButterKnife.bind(this);

        setToolbar();
        getDataFromBundle();
        configDataScreen();

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Modificar estatus comensal");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    private void getDataFromBundle() {
        args = getIntent().getExtras();
        nameCommensal = args.getString("nameCommensal","");
        surname = args.getString("surname","");
    }

    private void configDataScreen() {
        txtNameCommensal.setText(nameCommensal + " " + surname);

        btnAcceptChanges.setOnClickListener(view -> {
            verifyCheckBoxs();
        });

        btnDeleteCommensal.setOnClickListener(view -> {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        });
    }

    private void verifyCheckBoxs() {
        if (cbMealPaidUp.isSelected()) {
            if (cbMealDelivered.isSelected()) {
                //Pagó y se le entregó
            } else {
                //Pagó y no se le entregó aún
            }
        } else {
            if (cbMealDelivered.isSelected()) {
                //No pagó y se le entregó la comida
            } else {
                //No pagó y no se le entegó la comida aún
            }
        }
    }

    private void showAlertDialogDeleteCommensal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(getResources().getDrawable(R.drawable.ic_warning));
        builder.setTitle("¿Quieres eliminar la comida?");
        builder.setMessage("Eliminarás a " + nameCommensal + " de la comida\n ¿Deseas continuar?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.create();
        builder.show();
    }

}
