package com.gustavo.comelon.ui.manage.meal.create;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.manage.meal.ManageMealActivity;
import com.gustavo.comelon.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateNewMealActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;
    @BindView(R.id.progress_bar_createMeal)
    ProgressBar progressBar;

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

    private Bundle args;
    private String nameMeal;
    private String nameStew;
    private String descriptionMeal;
    private String numPersons;
    private String costMeal;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_meal);
        ButterKnife.bind(this);

        setArgs();
        setToolbar();
        showCreateMealFormView();

    }

    private void setArgs() {
        args = getIntent().getExtras();
        if (args != null) {
            nameMeal = args.getString("nameMeal","");
            nameStew = args.getString("nameStew","");
            descriptionMeal = args.getString("descriptionMeal","");
            numPersons = args.getString("numPersons","");
            costMeal = args.getString("costMeal","");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showCreateMealFormView() {
        setProgressBar(66);

        if (args != null) {
            fillFieldsForm();
        }

        //Configuración de los escuchadores en los campos del formulario
        edtxtNameMeal.addTextChangedListener(this);
        edtxtNameStew.addTextChangedListener(this);
        edtxtDescription.addTextChangedListener(this);
        edtxtNumberPersons.addTextChangedListener(this);
        edtxtCostMeal.addTextChangedListener(this);
        txtDeadline.addTextChangedListener(this);
        txtHourLimit.addTextChangedListener(this);

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
                SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS,MODE_PRIVATE).edit();
                editor.putBoolean("mealCreated",true);
                editor.apply();
                startActivity(new Intent(CreateNewMealActivity.this, ManageMealActivity.class));
                Toast.makeText(this, "Comida creada", Toast.LENGTH_SHORT).show();
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

        datePickerDialog = new DatePickerDialog(CreateNewMealActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    private void setProgressBar(int i) {
        progressBar.setProgress(i);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Crear comida");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }

    /////////////////////////Métodos propios de la clase TextWatcher//////////////////////////
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (validateCreateNewMealForm()) {
            setProgressBar(100);
        } else {
            setProgressBar(66);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////

}
