package com.gustavo.comelon.ui.meal.request;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.manage.commensal.add.AddCommensalActivity;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestMealActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.btn_accept_request_meal)
    MaterialButton btnAcceptRequestMeal;
    @BindView(R.id.btn_cancel_request_meal)
    MaterialButton btnCancelRequestMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_meal);
        ButterKnife.bind(this);

        setToolbar();
        setBtns();

    }

    private void setBtns() {
        btnCancelRequestMeal.setOnClickListener(view -> {
            saveDataFromSharedPrefs(2);
            finish();
        });

        btnAcceptRequestMeal.setOnClickListener(view -> {
            saveDataFromSharedPrefs(3);
            createAlertDialog();
        });
    }

    private void saveDataFromSharedPrefs(int status) {
        SharedPreferences prefs = getSharedPreferences("commensal_suscribed", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("statusOrder",status);
        editor.apply();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Solicitar comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.alert_dialog_congratulations_meal_requested, null);

        final TextView btnAccept;

        btnAccept = viewInflated.findViewById(R.id.txt_accept_requested);

        builder.setView(viewInflated);

        btnAccept.setOnClickListener(view -> {
            finish();
        });

        builder.show();

    }
}
