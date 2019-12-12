package com.gustavo.comelon.ui.last_requested;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gustavo.comelon.R;
import com.gustavo.comelon.adapter.MealAdapter;
import com.gustavo.comelon.model.Meal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LastMealsRequestedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.recyclerview_last_meals_requested)
    RecyclerView rvLastMealsRequested;

    private ArrayList<Meal> mealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_meals_requested);
        ButterKnife.bind(this);

        setToolbar();
        setRecyclerView();

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Comidas pedidas");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    private void setRecyclerView() {
        fillMealList();

        rvLastMealsRequested.setHasFixedSize(true);
        rvLastMealsRequested.setLayoutManager(new LinearLayoutManager(this));
        MealAdapter mealAdapter = new MealAdapter(getApplicationContext(), mealList);
        rvLastMealsRequested.setAdapter(mealAdapter);

    }

    private void fillMealList() {
        mealList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            mealList.add(new Meal("Pollo " + i, "Arroz " + i, " Hola, esta es la descripción de la comida ",
                    4, 15, 22.22f, "22/07/2019", "23:40", "created"));
        }
    }

}
