package com.gustavo.comelon.ui.manage.commensal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.manage.commensal.consult.ConsultCommensalsActivity;
import com.gustavo.comelon.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageCommensalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.btn_consulting_commensal)
    MaterialButton btnConsultingComs;
    @BindView(R.id.btn_add_commensal)
    MaterialButton btnAddCom;

    private String statusMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_commensal);
        ButterKnife.bind(this);

        setToolbar();
        getDataFromSharedPrefs();
        setButtons();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Administrar comensales");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    private void getDataFromSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences(Constants.MEAL,MODE_PRIVATE);
        statusMeal = prefs.getString("statusMeal","");
    }

    private void setButtons() {
        //Validar si existe alguna comida para habilitar el botón

        if(statusMeal.equals("complete") || statusMeal.equals("deleted")){
            disableButton(btnConsultingComs);
        }else{
            enableButton(btnConsultingComs,R.color.blueNormal);
            btnConsultingComs.setOnClickListener(view -> {
                startActivity(new Intent(ManageCommensalActivity.this,ConsultCommensalsActivity.class));
            });
        }
        //Botón siempre disponible
        btnAddCom.setOnClickListener(view ->{

        });
    }

    private void disableButton(MaterialButton btn) {
        btn.setBackgroundColor(getResources().getColor(R.color.btnDisabled));
        btn.setTextColor(getResources().getColor(R.color.txtDisabled));
        btn.setOnClickListener(null);
    }

    private void enableButton(MaterialButton btn, int bgColor) {
        btn.setBackgroundColor(getResources().getColor(bgColor));
        btn.setTextColor(getResources().getColor(R.color.whiteNormal));
    }
}
