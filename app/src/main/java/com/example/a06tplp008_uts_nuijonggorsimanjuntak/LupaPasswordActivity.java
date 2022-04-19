package com.example.a06tplp008_uts_nuijonggorsimanjuntak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.a06tplp008_uts_nuijonggorsimanjuntak.databinding.ActivityLoginBinding;
import com.example.a06tplp008_uts_nuijonggorsimanjuntak.databinding.ActivityLupaPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LupaPasswordActivity extends AppCompatActivity {
    private ActivityLupaPasswordBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLupaPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString();

                if (email.isEmpty()) {
                    binding.edtEmail.setError("Email Tidak Boleh Kosong");
                    binding.edtEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmail.setError("Masukkan Email yang Valid");
                    binding.edtEmail.requestFocus();
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Reset Password Url Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(LupaPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    binding.edtEmail.setError("Tidak Terdaftar");
                                    binding.edtEmail.requestFocus();
                                    return;
//                                    Toast.makeText(getApplicationContext(), "Reset Password Url Gagal Dikirim", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}