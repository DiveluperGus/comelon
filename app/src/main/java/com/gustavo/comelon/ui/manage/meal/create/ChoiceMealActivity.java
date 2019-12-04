package com.gustavo.comelon.ui.manage.meal.create;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gustavo.comelon.R;
import com.gustavo.comelon.adapter.MealAdapter;
import com.gustavo.comelon.model.Meal;
import com.gustavo.comelon.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceMealActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;
    @BindView(R.id.progress_bar_createMeal)
    ProgressBar progressBar;

    //Se muestra un listado de 10 elementos, cada uno es una comida
    @BindView(R.id.constraint_choice_between_meals)
    ConstraintLayout clChoiceBetweenMeals;
    @BindView(R.id.recylerview_meals)
    RecyclerView rvMeals;

    private ArrayList<Meal> mealList;
    private boolean configureEditMeal = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_meal);
        ButterKnife.bind(this);
        setToolbar();
        showMealListPopular();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Crear comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showMealListPopular() {
        setProgressBar(50);

        if (!configureEditMeal) {
            mealList = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                mealList.add(new Meal("Pollo " + i, "Arroz " + i, " Hola, esta es la descripción de la comida ",
                        4, 22.22f, "22/07/2019", "23:40", "created"));
            }

            rvMeals.setHasFixedSize(true);
            rvMeals.setLayoutManager(new LinearLayoutManager(this));
            MealAdapter mealAdapter = new MealAdapter(getApplicationContext(), mealList);
            rvMeals.setAdapter(mealAdapter);

            mealAdapter.setItemClickListener(position -> {
                Intent i = new Intent(ChoiceMealActivity.this, CreateNewMealActivity.class);
                Meal m = mealList.get(position);

                SharedPreferences.Editor editor = getSharedPreferences(Constants.MEAL,MODE_PRIVATE).edit();
                editor.putString("nameMeal", m.getNameMeal());
                editor.putString("nameStew", m.getNameStew());
                editor.putString("descriptionMeal", m.getDescription());
                editor.putString("numPersons", m.getNumNecessaryPersons() + "");
                editor.putString("costMeal", m.getMealCost() + "");
                editor.putString("statusMeal",m.getStatus());
                editor.apply();
                startActivity(i);

            });
            configureEditMeal = true;
        }
    }

    private void setProgressBar(int i) {
        progressBar.setProgress(i);
    }

}
