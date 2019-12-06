package com.gustavo.comelon.ui.manage.meal.status;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;

import butterknife.BindView;

public class StatusMealActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.txt_name_commensal)
    TextView txtNameCommensal;
    @BindView(R.id.txt_deadline_and_timelimit)
    TextView txtDeadline;
    @BindView(R.id.btn_made_meal)
    MaterialButton btnMadeMeal;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_meal);

        setToolbar();


    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Administrar comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

}
