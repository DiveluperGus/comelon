package com.gustavo.comelon.ui.manage.meal.edit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.manage.meal.ManageMealActivity;
import com.gustavo.comelon.ui.manage.meal.create.CreateNewMealActivity;
import com.gustavo.comelon.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditMealActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.scrollview_create_meal_form)
    ScrollView scrollViewForm;
    @BindView(R.id.constraint_create_meal_form)
    ConstraintLayout clCreateMealForm;
    @BindView(R.id.edtxt_name_meal)
    EditText edtxtNameMeal;
    @BindView(R.id.edtxt_name_stew)
    EditText edtxtNameStew;
    @BindView(R.id.edtxt_description)
    EditText edtxtDescription;
    @BindView(R.id.edtxt_number_persons)
    EditText edtxtNumberPersons;
    @BindView(R.id.edtxt_cost_meal)
    EditText edtxtCostMeal;
    @BindView(R.id.btn_deadline)
    Button btnDeadline;
    @BindView(R.id.txt_deadline)
    TextView txtDeadline;
    @BindView(R.id.btn_hour_limit)
    Button btnHourLimit;
    @BindView(R.id.txt_hour_limit)
    TextView txtHourLimit;
    @BindView(R.id.btn_cancel_create_meal)
    MaterialButton btnCancelCreateMeal;
    @BindView(R.id.btn_accept_create_meal)
    MaterialButton btnAcceptCreateMeal;

    private SharedPreferences prefs;
    private String nameMeal;
    private String nameStew;
    private String descriptionMeal;
    private String numPersons;
    private String costMeal;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);
        ButterKnife.bind(this);

        setToolbar();
        getDataSharedPrefs();
        showEditMealFormView();
    }

    private void getDataSharedPrefs() {
        prefs = getSharedPreferences(Constants.MEAL, MODE_PRIVATE);

        nameMeal = prefs.getString("nameMeal", "");
        nameStew = prefs.getString("nameStew", "");
        descriptionMeal = prefs.getString("descriptionMeal", "");
        numPersons = prefs.getString("numPersons", "");
        costMeal = prefs.getString("costMeal", "");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showEditMealFormView() {

        if (nameMeal != null && !nameMeal.isEmpty()) {
            fillFieldsForm();
        }

        //Configuración de los botones en el formulario
        btnDeadline.setOnClickListener(view -> {
            pickerDeadline();
        });

        btnHourLimit.setOnClickListener(view -> {
            pickerHourline();
        });

        btnCancelCreateMeal.setOnClickListener(view -> {
            finish();
        });

        btnAcceptCreateMeal.setOnClickListener(view -> {
            if (validateCreateNewMealForm()) {
                SharedPreferences.Editor editor = getSharedPreferences(Constants.MEAL,MODE_PRIVATE).edit();
                editor.putString("nameMeal", edtxtNameMeal.getText().toString());
                editor.putString("nameStew", edtxtNameStew.getText().toString());
                editor.putString("descriptionMeal", edtxtDescription.getText().toString());
                editor.putString("numPersons", edtxtNumberPersons.getText().toString());
                editor.putString("costMeal", edtxtCostMeal.getText().toString());
                editor.putString("statusMeal","edited");
                editor.apply();

                startActivity(new Intent(EditMealActivity.this, ManageMealActivity.class));
                Toast.makeText(this, "Operación exitosa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ningún campo debe estar vacío", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillFieldsForm() {
        edtxtNameMeal.setText(nameMeal);
        edtxtNameStew.setText(nameStew);
        edtxtDescription.setText(descriptionMeal);
        edtxtNumberPersons.setText(numPersons);
        edtxtCostMeal.setText(costMeal);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pickerHourline() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        //Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if (minute >= 0 && minute <= 9)
                    txtHourLimit.setText(hourOfDay + ":0" + minute);
                else
                    txtHourLimit.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pickerDeadline() {
        final Calendar c;
        int mYear;
        int mMonth;
        int mDay;
        DatePickerDialog datePickerDialog;
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(EditMealActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                txtDeadline.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private boolean validateCreateNewMealForm() {
        return !edtxtNameMeal.getText().toString().isEmpty() && !edtxtNameStew.getText().toString().isEmpty()
                && !edtxtDescription.getText().toString().isEmpty() && !edtxtNumberPersons.getText().toString().isEmpty()
                && !edtxtCostMeal.getText().toString().isEmpty() && !txtDeadline.getText().toString().isEmpty()
                && !txtHourLimit.getText().toString().isEmpty();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Crear comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

}
