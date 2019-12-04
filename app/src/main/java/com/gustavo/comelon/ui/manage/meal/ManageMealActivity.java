package com.gustavo.comelon.ui.manage.meal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.manage.meal.create.CreateMealActivity;
import com.gustavo.comelon.ui.manage.meal.edit.EditMealActivity;
import com.gustavo.comelon.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageMealActivity extends AppCompatActivity {

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
            btnEditMeal.setOnClickListener(view -> {
                startActivity(new Intent(ManageMealActivity.this, EditMealActivity.class));
            });
            enableButton(btnStatusMeal, R.color.brownNormal);
            btnStatusMeal.setOnClickListener(view -> {

            });

            enableButton(btnDeleteMeal, R.color.redNormal);
            btnDeleteMeal.setOnClickListener(view -> {

            });
        } else {
            btnCreateMeal.setOnClickListener(view -> {
                Intent i = new Intent(ManageMealActivity.this, CreateMealActivity.class);
                startActivity(i);
            });

            disableButton(btnEditMeal);
            disableButton(btnStatusMeal);
            disableButton(btnDeleteMeal);
        }

    }

    private void getStatusMealSharedPrefs() {
        prefs = getSharedPreferences(Constants.MEAL, MODE_PRIVATE);
        statusMeal = prefs.getString("statusMeal", "");
    }

    private void disableButton(MaterialButton btn) {
        btn.setBackgroundColor(getResources().getColor(R.color.btnDisabled));
        btn.setTextColor(getResources().getColor(R.color.txtDisabled));
    }

    private void enableButton(MaterialButton btn, int bgColor) {
        btn.setBackgroundColor(getResources().getColor(bgColor));
        btn.setTextColor(getResources().getColor(R.color.whiteNormal));
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Administrar comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getStatusMealSharedPrefs();
    }
}
