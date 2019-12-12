package com.gustavo.comelon.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.login.LoginActivity;
import com.gustavo.comelon.utils.Regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.edtxt_name)
    EditText edtxtName;
    @BindView(R.id.edtxt_lastName)
    EditText edtxtLastName;
    @BindView(R.id.edtxt_email)
    EditText edtxtEmail;
    @BindView(R.id.edtxt_phone)
    EditText edtxtPhone;
    @BindView(R.id.edtxt_password_user)
    TextInputEditText edtxtPassword;
    @BindView(R.id.txt_fields_required)
    TextView txtFieldsRequired;
    @BindView(R.id.txt_error_email)
    TextView txtErrorEmail;
    @BindView(R.id.txt_error_passw)
    TextView txtErrorPassword;

    @BindView(R.id.btn_signup)
    MaterialButton btnSigUp;
    @BindView(R.id.progressbarSignup)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private static final String TAG = "SignupActivity";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setBtnSignUp();
    }

    private void setBtnSignUp() {
        btnSigUp.setOnClickListener(view -> {
            if (validateForm()) {
                txtFieldsRequired.setVisibility(View.GONE);
                if(validateEmail()){
                    txtErrorEmail.setVisibility(View.GONE);
                    if(validatePassword()){
                        txtErrorPassword.setVisibility(View.GONE);
                        verifyUserExists(edtxtEmail.getText().toString(), edtxtPassword.getText().toString());
                    }else{
                        txtErrorPassword.setVisibility(View.VISIBLE);
                    }
                }else{
                    txtErrorEmail.setVisibility(View.VISIBLE);
                }
            } else
                txtFieldsRequired.setVisibility(View.VISIBLE);
        });
    }

    private boolean validateEmail(){
        Matcher matcher = Regex.VALID_EMAIL_REGEX.matcher(edtxtEmail.getText().toString());
        return matcher.matches();
    }


    private void verifyUserExists(final String email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Log.d(TAG, "checking to see if user exists in firebase or not");
                    SignInMethodQueryResult result = task.getResult();

                    if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        Log.d(TAG, "User exists, trying to login using entered credentials");
                        Toast.makeText(SignupActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        edtxtEmail.requestFocus();
                    } else {
                        Log.d(TAG, "User doesn't exist, creating account");
                        signUpUser(email, password);
                    }
                } else {
                    Log.w(TAG, "User check failed", task.getException());
                    Toast.makeText(SignupActivity.this,
                            "There is a problem, please try again later.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUpUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Log.e(TAG, "Account created");

                            String idUser = mAuth.getCurrentUser().getUid();
                            Boolean ban = false;

                            Map<String,Object> map = new HashMap<>();
                            map.put("name",edtxtName.getText().toString());
                            map.put("surname",edtxtLastName.getText().toString());
                            map.put("email",edtxtEmail.getText().toString());
                            map.put("phone",edtxtPhone.getText().toString().isEmpty() ? "" : edtxtPhone.getText().toString());
                            map.put("rol",1);
                            map.put("selectedDraw",ban);
                            map.put("status",0);
                            map.put("remeaningMeals",0);
                            map.put("subscribedMeal",ban);

                            mDatabase.child("comelon").child("users").child(idUser).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupActivity.this, "El usuario ha sido creado", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(SignupActivity.this, "No se pudo registrar el usuarios", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Account registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        return !edtxtName.getText().toString().isEmpty() && !edtxtLastName.getText().toString().isEmpty()
                && !edtxtEmail.getText().toString().isEmpty() && !edtxtPassword.getText().toString().isEmpty();
    }

    private boolean validatePassword() {
        Matcher matcher = Regex.VALID_PASSWORD_REGEX.matcher(edtxtPassword.getText().toString());
        return  matcher.matches();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
