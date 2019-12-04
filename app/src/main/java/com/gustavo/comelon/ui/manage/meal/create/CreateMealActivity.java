package com.gustavo.comelon.ui.manage.meal.create;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateMealActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;
    @BindView(R.id.progress_bar_createMeal)
    ProgressBar progressBar;

    //Primer vista, selecciona si desea crear una comida nueva o elegir entre las ya creadas
    @BindView(R.id.constraint_create_o_choice_meal)
    ConstraintLayout clCreateOrChoiceMeal;
    @BindView(R.id.btn_create_new_meal)
    MaterialButton btnCreateNewMeal;
    @BindView(R.id.btn_create_by_choice_meal)
    MaterialButton btnCreateNewMealByChoice;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        ButterKnife.bind(this);
        setToolbar();
        setViewCreateOrChoiceMeal();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViewCreateOrChoiceMeal() {
        setProgressBar(33);

        btnCreateNewMeal.setOnClickListener(view -> {
            Intent i = new Intent(CreateMealActivity.this, CreateNewMealActivity.class);
            startActivity(i);
        });
        btnCreateNewMealByChoice.setOnClickListener(view -> {
            Intent i = new Intent(CreateMealActivity.this, ChoiceMealActivity.class);
            startActivity(i);
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Crear comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    private void setProgressBar(int i) {
        progressBar.setProgress(i);
    }

}
