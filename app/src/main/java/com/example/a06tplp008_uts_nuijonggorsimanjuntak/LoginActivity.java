package com.example.a06tplp008_uts_nuijonggorsimanjuntak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.a06tplp008_uts_nuijonggorsimanjuntak.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmailLogin.getText().toString();
                String password = binding.edtPasswordLogin.getText().toString();

                if (email.isEmpty()) {
                    binding.edtEmailLogin.setError("Email Harus Diisi!");
                    binding.edtEmailLogin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmailLogin.setError("Email Tidak Valid");
                    binding.edtEmailLogin.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    binding.edtPasswordLogin.setError("Password Harus Diisi!");
                    binding.edtPasswordLogin.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    binding.edtPasswordLogin.setError("Password Minimal 6 Karakter!");
                    binding.edtPasswordLogin.requestFocus();
                    return;
                }

                loginUserAccount(email, password);
            }
        });

        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LupaPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUserAccount(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), email + " Berhasil Login", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), email + " Gagal Login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}