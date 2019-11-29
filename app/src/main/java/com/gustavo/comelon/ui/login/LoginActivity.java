package com.gustavo.comelon.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
import com.gustavo.comelon.R;
import com.gustavo.comelon.ui.home.chef.HomeChefActivity;
import com.gustavo.comelon.ui.home.commensal.HomeCommensalActivity;
import com.gustavo.comelon.ui.signup.SignupActivity;
import com.gustavo.comelon.utils.Regex;

import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtInp_username_login)
    TextInputEditText edtxtUsername;
    @BindView(R.id.txt_error_email)
    TextView txtErrorEmail;
    @BindView(R.id.txtInp_passw_login)
    TextInputEditText edtxtPassword;
    @BindView(R.id.txt_error_passw)
    TextView txtErrorPassw;
    @BindView(R.id.btn_login)
    MaterialButton btnLogin;
    @BindView(R.id.txt_signUp)
    TextView txtSignUp;
    @BindView(R.id.txt_forgot_password)
    TextView txtForgotPassword;

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";
    private int typeUser = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        clearFields();
        setBtnLogin();
        setBtnSignUp();
        setBtnForgotPass();

    }

    @Override
    protected void onResume() {
        super.onResume();
        clearFields();
    }

    private void clearFields() {
        edtxtUsername.setText("");
        edtxtPassword.setText("");
    }

    private void setBtnLogin() {
        btnLogin.setOnClickListener(view -> {
            if (validForm())
                login(edtxtUsername.getText().toString(), edtxtPassword.getText().toString());
        });
    }

    private void setBtnSignUp() {
        txtSignUp.setOnClickListener(view -> {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        });
    }

    private void setBtnForgotPass() {
        txtForgotPassword.setOnClickListener(view -> {
            Toast.makeText(this, "Olvidé mi contraseña", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validForm() {
        boolean valid = false;
        if (!edtxtUsername.getText().toString().isEmpty()) {
            if (validateEmail()) {
                setInvisibleView(txtErrorEmail);
                if (!edtxtPassword.getText().toString().isEmpty()) {
                    if (validatePassword()) {
                        setInvisibleView(txtErrorPassw);
                        valid = true;
                    } else
                        setVisibleView(txtErrorPassw);
                }
            } else
                setVisibleView(txtErrorEmail);
        }
        return valid;
    }

    private boolean validatePassword() {
        Matcher matcher = Regex.VALID_PASSWORD_REGEX.matcher(edtxtPassword.getText().toString());
        return matcher.matches();
    }

    private boolean validateEmail() {
        Matcher matcher = Regex.VALID_EMAIL_REGEX.matcher(edtxtUsername.getText().toString());
        return matcher.matches();
    }

    private void setVisibleView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void setInvisibleView(View view) {
        view.setVisibility(View.GONE);
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent;
                            if (typeUser == 1) {
                                intent = new Intent(LoginActivity.this, HomeChefActivity.class);
                            } else {
                                intent = new Intent(LoginActivity.this, HomeCommensalActivity.class);
                            }

                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
