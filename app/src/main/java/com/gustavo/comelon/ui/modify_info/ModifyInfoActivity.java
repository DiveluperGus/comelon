package com.gustavo.comelon.ui.modify_info;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gustavo.comelon.R;
import com.gustavo.comelon.model.Commensal;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView titleToolbar;
    @BindView(R.id.toolbar_arrow_back)
    ImageButton btnBack;

    @BindView(R.id.edtxt_name)
    EditText edtxtName;
    @BindView(R.id.edtxt_lastName)
    EditText edtxtLastName;
    @BindView(R.id.edtxt_email)
    EditText edtxtEmail;
    @BindView(R.id.edtxt_phone)
    EditText edtxtPhone;

    @BindView(R.id.btn_cancel_modify_info)
    MaterialButton btnCancelModifyInfo;
    @BindView(R.id.btn_accept_modify_info)
    MaterialButton btnAcceptModifyInfo;

    @BindView(R.id.progressBar_mofify_info)
    ProgressBar progressBar;

    private String name, surname, email, phone;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        ButterKnife.bind(this);

        setToolbar();
        setViews();
    }

    private void setViews() {
        getDataFromSharedPrefs();
        edtxtName.setText(name);
        edtxtName.setSelection(name.length());
        edtxtLastName.setText(surname);
        edtxtEmail.setText(email);
        edtxtEmail.setEnabled(false);
        edtxtPhone.setText(phone);

        btnCancelModifyInfo.setOnClickListener(view -> {
            finish();
        });

        btnAcceptModifyInfo.setOnClickListener(view -> {
            if (validateForm()) {
                changeDataFromFirebase();
            } else {
                Toast.makeText(this, "El campo nombre y apellido no deben estar vacíos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateForm() {
        return !edtxtName.getText().toString().isEmpty() && !edtxtLastName.getText().toString().isEmpty();
    }

    private void changeDataFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("comelon");
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int rol = dataSnapshot.getValue(Commensal.class).getRol();
                Boolean selectedDraw = dataSnapshot.getValue(Commensal.class).isSelectedDraw();
                int status = dataSnapshot.getValue(Commensal.class).getStatus();
                int remeaningMeals = dataSnapshot.getValue(Commensal.class).getRemeaningMeals();
                Boolean subscribedMeal = dataSnapshot.getValue(Commensal.class).isSuscribedMeal();

                Map<String, Object> map = new HashMap<>();
                map.put("name", edtxtName.getText().toString());
                map.put("surname", edtxtLastName.getText().toString());
                map.put("email", edtxtEmail.getText().toString());
                map.put("phone", edtxtPhone.getText().toString());

                map.put("rol", rol);
                map.put("selectedDraw", selectedDraw);
                map.put("status", status);
                map.put("remeaningMeals", remeaningMeals);
                map.put("subscribedMeal", subscribedMeal);

                mDatabase.child("users").child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ModifyInfoActivity.this, "Cambios de información con éxito.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(ModifyInfoActivity.this, "Para ver los datos actulizados vuelve a iniciar sesión.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ModifyInfoActivity.this, "Algo ha fallado, inténtelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getDataFromSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        name = prefs.getString("name", "");
        surname = prefs.getString("surname", "");
        email = prefs.getString("email", "");
        phone = prefs.getString("phone", "");
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText("Modificar información");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(view -> finish());
    }
}
