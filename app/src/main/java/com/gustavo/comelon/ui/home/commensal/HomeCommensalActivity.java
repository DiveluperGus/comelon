package com.gustavo.comelon.ui.home.commensal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.login.LoginActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_commensal);
        ButterKnife.bind(this);

        setToolbar();

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
