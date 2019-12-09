package com.gustavo.comelon.ui.manage.meal.status;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;
import com.gustavo.comelon.adapter.CommensalAdapter;
import com.gustavo.comelon.model.Commensal;
import com.gustavo.comelon.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusMealActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.txt_name_meal)
    TextView txtNameMeal;
    @BindView(R.id.txt_deadline_and_timelimit)
    TextView txtDeadline;
    @BindView(R.id.btn_made_meal)
    MaterialButton btnMadeMeal;
    @BindView(R.id.btn_go_by_tortillas)
    MaterialButton btnGoByTortillas;
    @BindView(R.id.recyclerview_commensal_suscribed)
    RecyclerView rvCommensalSuscribed;
    @BindView(R.id.btn_send_result)
    MaterialButton btnSendResult;

    private ArrayList<Commensal> commensals;
    private CommensalAdapter adapter;
    private int commensalSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_meal);
        ButterKnife.bind(this);

        setToolbar();
        setInitialButtons();


    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Administrar comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    private void setInitialButtons() {
        enableButton(btnMadeMeal, R.color.blueNormal);
        disableButton(btnGoByTortillas);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_made_meal:
                alreadyMealMade();
                break;
            case R.id.btn_go_by_tortillas:
                performDraw(commensalSelected);
                break;
            case R.id.btn_send_result:
                sendResultGoForTheTortillas();
                break;
        }
    }

    private void sendResultGoForTheTortillas() {
        Toast.makeText(this, commensals.get(commensalSelected).getName() + " irá por las tortillas", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "El resultado se envío a todos los comensales correctamente", Toast.LENGTH_SHORT).show();
        disableButton(btnGoByTortillas);
        disableButton(btnSendResult);
    }

    private void performDraw(int i) {
        int nRandom = (int) getRandomIntegerBetweenRange(0,commensals.size()-1);
        commensals.get(nRandom).setSelected(true);
        commensals.get(i).setSelected(false);
        commensalSelected = nRandom;
        adapter.notifyItemChanged(nRandom);
        adapter.notifyItemChanged(i);
        enableButton(btnSendResult,R.color.greenNormal);
    }


    public static double getRandomIntegerBetweenRange(double min, double max) {
        double x = (int) (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    private void alreadyMealMade() {
        disableButton(btnMadeMeal);
        enableButton(btnGoByTortillas, R.color.greenNormal);
        btnSendResult.setVisibility(View.VISIBLE);
        configRecyclerViewCommensal();
        disableButton(btnSendResult);

        SharedPreferences prefs = getSharedPreferences(Constants.MEAL,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("statusMeal","made");
    }

    private void configRecyclerViewCommensal() {
        rvCommensalSuscribed.setVisibility(View.VISIBLE);
        fillCommensalList();
        adapter = new CommensalAdapter(commensals, getApplicationContext());
        rvCommensalSuscribed.setHasFixedSize(true);
        rvCommensalSuscribed.setLayoutManager(new LinearLayoutManager(this));
        rvCommensalSuscribed.setAdapter(adapter);
    }

    private void fillCommensalList() {
        commensals = new ArrayList<>();
        commensals.add(new Commensal("Gustavo", "Reyes", "gustavo@gmail.com", "2721244358", 2, false));
        commensals.add(new Commensal("David", "Sanchez", "david@gmail.com", "2721244310", 2, false));
        commensals.add(new Commensal("Itzel", "Cancio", "itzel@gmail.com", "2721244320", 2, false));
        commensals.add(new Commensal("Fernando", "Andrade", "fernando@gmail.com", "2721244330", 2, false));
        commensals.add(new Commensal("Celeste", "Pérez", "celeste@gmail.com", "2721244340", 2, false));
        commensals.add(new Commensal("Carlos", "Martinez", "carlos@gmail.com", "2721244350", 2, false));
        commensals.add(new Commensal("Andres", "Esteban", "andres@gmail.com", "2721244360", 2, false));
        commensals.add(new Commensal("Salvador", "Gonzalez", "salvador@gmail.com", "2721244370", 2, false));
        commensals.add(new Commensal("Aracely", "Reyes", "aracely@gmail.com", "2721244380", 2, false));
        commensals.add(new Commensal("José Antonio", "Pintor", "antonio@gmail.com", "2721244390", 2, false));
    }
}
