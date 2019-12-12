package com.gustavo.comelon.ui.home.commensal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.last_requested.LastMealsRequestedActivity;
import com.gustavo.comelon.ui.login.LoginActivity;
import com.gustavo.comelon.ui.meal.request.RequestMealActivity;
import com.gustavo.comelon.ui.modify_info.ModifyInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeCommensalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_username_home)
    TextView username;

    //Primera opción, en caso de que no haya alguna comida disponible
    @BindView(R.id.constraint_no_meals_created)
    ConstraintLayout constraintNoMealsCreated;

    //Segunda opción, en caso de que haya alguna comida creada
    @BindView(R.id.constraint_meal_created)
    ConstraintLayout constraintMealCreated;
    @BindView(R.id.txt_msg_meal_created)
    TextView txtMealCreated;
    @BindView(R.id.btn_request_meal)
    MaterialButton btnRequestMeal;

    //Tercera opción, en caso de querer eliminar la comida
    @BindView(R.id.constraint_meal_requested)
    ConstraintLayout constraintMealRequested;
    @BindView(R.id.txt_msg_meal_requested)
    TextView txtMealRequested;
    @BindView(R.id.btn_cancel_meal)
    MaterialButton btnCancelMeal;

    @BindView(R.id.btn_see_last_you_has_eat)
    MaterialButton btnSeeLastYouHasEat;

    private SharedPreferences prefs;
    private String userName;
    private int statusOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_commensal);
        ButterKnife.bind(this);

        setToolbar();
        setBtnsView();
        setViews();

    }


    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    private void setBtnsView() {
        ///////////////////Primera opción////////////////////////////

        Toast.makeText(this, "Num: " + statusOrder, Toast.LENGTH_SHORT).show();
        if (statusOrder == 1 || statusOrder == 0) {
            setVisibleConstraint(constraintNoMealsCreated);
            setInvisibleConstraint(constraintMealRequested);
            setInvisibleConstraint(constraintMealCreated);
        }
        ///////////////////Segunda opción/////////////////////////////
        else if (statusOrder == 2) {
            setVisibleConstraint(constraintMealCreated);
            setInvisibleConstraint(constraintNoMealsCreated);
            setInvisibleConstraint(constraintMealRequested);
            btnRequestMeal.setOnClickListener(view -> {
                startActivity(new Intent(HomeCommensalActivity.this, RequestMealActivity.class));
            });
        }
        //////////////////Tercera opción/////////////////////////////
        else if (statusOrder == 3) {
            setVisibleConstraint(constraintMealRequested);
            setInvisibleConstraint(constraintMealCreated);
            setInvisibleConstraint(constraintNoMealsCreated);
        }

        btnSeeLastYouHasEat.setOnClickListener(view -> {
            startActivity(new Intent(HomeCommensalActivity.this, LastMealsRequestedActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.global_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify_info:
                startActivity(new Intent(getApplicationContext(), ModifyInfoActivity.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setViews() {
        getDataSharedPrefs();
        username.setText(userName);
    }

    private void getDataSharedPrefs() {
        prefs = getSharedPreferences("user", MODE_PRIVATE);
        userName = prefs.getString("name", "Nombre del comensal");
        SharedPreferences prefs = getSharedPreferences("commensal_suscribed", MODE_PRIVATE);
        statusOrder = prefs.getInt("statusOrder",0);


    }

    private void setVisibleConstraint(ConstraintLayout cl){
        cl.setVisibility(View.VISIBLE);
    }

    private void setInvisibleConstraint(ConstraintLayout cl){
        cl.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataSharedPrefs();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
