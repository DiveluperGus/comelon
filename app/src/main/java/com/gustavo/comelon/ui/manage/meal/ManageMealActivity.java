package com.gustavo.comelon.ui.manage.meal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.manage.meal.create.CreateMealActivity;
import com.gustavo.comelon.ui.manage.meal.edit.EditMealActivity;
import com.gustavo.comelon.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageMealActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.btn_create_meal_mgn_meal)
    MaterialButton btnCreateMeal;
    @BindView(R.id.btn_edit_meal_mgn_meal)
    MaterialButton btnEditMeal;
    @BindView(R.id.btn_status_meal_mgn_meal)
    MaterialButton btnStatusMeal;
    @BindView(R.id.btn_delete_meal_mgn_meal)
    MaterialButton btnDeleteMeal;

    private SharedPreferences prefs;
    private String statusMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_meal);
        ButterKnife.bind(this);

        setToolbar();
        setButtons();
    }

    private void setButtons() {
        getStatusMealSharedPrefs();

        if (statusMeal.equals("created") || statusMeal.equals("edited")) {
            disableButton(btnCreateMeal);
            enableButton(btnEditMeal, R.color.greenNormal);
            enableButton(btnStatusMeal, R.color.brownNormal);
            enableButton(btnDeleteMeal, R.color.redNormal);
        } else {
            enableButton(btnCreateMeal,R.color.blueNormal);
            disableButton(btnEditMeal);
            disableButton(btnStatusMeal);
            disableButton(btnDeleteMeal);
        }

    }

    private void showAlertDialogDeleteMeal() {
        String nMeal = getNameMeal();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(getResources().getDrawable(R.drawable.ic_warning));
        builder.setTitle("¿Quieres eliminar la comida?");
        builder.setMessage("Eliminarás la comida " + nMeal);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteMealSharedPrefs();
                enableButton(btnCreateMeal,R.color.blueNormal);
                disableButton(btnEditMeal);
                disableButton(btnStatusMeal);
                disableButton(btnDeleteMeal);
            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.create();
        builder.show();
    }

    private void deleteMealSharedPrefs() {
        SharedPreferences sp = getSharedPreferences(Constants.MEAL,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("statusMeal","deleted");
        editor.apply();
    }

    private String getNameMeal() {
        SharedPreferences sp = getSharedPreferences(Constants.MEAL,MODE_PRIVATE);
        return sp.getString("nameMeal","");
    }

    private void getStatusMealSharedPrefs() {
        prefs = getSharedPreferences(Constants.MEAL, MODE_PRIVATE);
        statusMeal = prefs.getString("statusMeal", "");
    }

    private void disableButton(MaterialButton btn) {
        btn.setBackgroundColor(getResources().getColor(R.color.btnDisabled));
        btn.setTextColor(getResources().getColor(R.color.txtDisabled));
        btn.setOnClickListener(null);
    }

    private void enableButton(MaterialButton btn, int bgColor) {
        btn.setBackgroundColor(getResources().getColor(bgColor));
        btn.setTextColor(getResources().getColor(R.color.whiteNormal));
        btn.setOnClickListener(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Administrar comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStatusMealSharedPrefs();
        setButtons();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_create_meal_mgn_meal:
                Intent i = new Intent(ManageMealActivity.this, CreateMealActivity.class);
                startActivity(i);
                break;
            case R.id.btn_edit_meal_mgn_meal:
                startActivity(new Intent(ManageMealActivity.this, EditMealActivity.class));
                break;
            case R.id.btn_status_meal_mgn_meal:
                break;
            case R.id.btn_delete_meal_mgn_meal:
                showAlertDialogDeleteMeal();
                break;
        }
    }
}
