package com.gustavo.comelon.ui.home.chef;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.login.LoginActivity;
import com.gustavo.comelon.ui.manage.commensal.ManageCommensalActivity;
import com.gustavo.comelon.ui.manage.meal.ManageMealActivity;
import com.gustavo.comelon.ui.modify_info.ModifyInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeChefActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_username_home)
    TextView username;
    @BindView(R.id.btn_manage_meal_home)
    MaterialButton btnManageMeal;
    @BindView(R.id.btn_manage_commensal_home)
    MaterialButton btnManageCommensal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chef);
        ButterKnife.bind(this);

        setToolbar();
        setBtnManageMeal();
        setBtnManageCommensal();
    }

    private void setBtnManageMeal() {
        btnManageMeal.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ManageMealActivity.class));
        });
    }

    private void setBtnManageCommensal() {
        btnManageCommensal.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ManageCommensalActivity.class));
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
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
