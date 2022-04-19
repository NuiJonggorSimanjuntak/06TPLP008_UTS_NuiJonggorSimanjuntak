package com.example.a06tplp008_uts_nuijonggorsimanjuntak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.a06tplp008_uts_nuijonggorsimanjuntak.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmailRegist.getText().toString();
                String password = binding.edtPasswordRegist.getText().toString();

                if (email.isEmpty()) {
                    binding.edtEmailRegist.setError("Email Harus Diisi!");
                    binding.edtEmailRegist.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmailRegist.setError("Email Tidak Valid");
                    binding.edtEmailRegist.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    binding.edtPasswordRegist.setError("Password Harus Diisi!");
                    binding.edtPasswordRegist.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    binding.edtPasswordRegist.setError("Password Minimal 6 Karakter!");
                    binding.edtPasswordRegist.requestFocus();
                    return;
                }

                daftarUserFirebase(email, password);
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void daftarUserFirebase(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Berhasil Membuat Akun", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.edtEmailRegist.setError(email + " Sudah Digunakan");
                            binding.edtEmailRegist.requestFocus();
                            return;
                        }
                    }
                });
    }
}