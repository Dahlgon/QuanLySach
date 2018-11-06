package com.example.admin.quanlysach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.admin.quanlysach.model.User;
import com.example.admin.quanlysach.DAO.UserDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserName;
    private EditText edPassWord;
    private CheckBox chkRememberPass;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = findViewById(R.id.edUserName);
        edPassWord = findViewById(R.id.edPassWord);
        chkRememberPass = findViewById(R.id.remember);
        Button btnLogin = findViewById(R.id.btnLogin);
        chkRememberPass.setChecked(true);
        userDAO = new UserDAO(this);
        getUser();

        User user2 = userDAO.getUser("ADMIN");
        if (user2 == null) {
            userDAO.insertUser("ADMIN", "sacchan16", "Admin", "999-666-333");
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edUserName.getText().toString().trim();
                String pass = edPassWord.getText().toString().trim();
                if (user.isEmpty() || pass.isEmpty()) {
                    if (user.isEmpty()) {
                        edUserName.setError(getString(R.string.notify_empty_user));
                    }
                    if (pass.isEmpty()) {
                        edPassWord.setError(getString(R.string.notify_empty_pass));
                    }
                } else if (pass.length() < 6) {
                    edPassWord.setError(getString(R.string.notify_length_pass));
                } else {
                    User user2 = userDAO.getUser(edUserName.getText().toString().trim().toUpperCase());
                    if (user2 == null) {
                        edUserName.setError(getString(R.string.user_not_exitst));
                    } else {
                        if (user2.getPassword().equals(edPassWord.getText().toString().trim())) {
                            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                            final int[] a = {0};
                            progressDialog.setTitle(getString(R.string.login));
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            CountDownTimer countDownTimer = new CountDownTimer(1000, 40) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    a[0] = a[0] + 4;
                                    progressDialog.show();
                                    progressDialog.setProgress(a[0]);
                                }

                                @Override
                                public void onFinish() {
                                    progressDialog.dismiss();
                                    reUser(edUserName.getText().toString().trim(), edPassWord.getText().toString().trim(), chkRememberPass.isChecked());
                                    reUser(edUserName.getText().toString().trim(), edPassWord.getText().toString().trim());
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                }
                            };
                            countDownTimer.start();
                        } else {
                            edPassWord.setError(getString(R.string.wrong_pass));
                        }

                    }
                }
            }
        });


    }

    private void reUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString("UserName", u);
            edit.putString("PassWord", p);
        }
        edit.apply();
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        if (pref != null) {
            String strUserName = pref.getString("UserName", "");
            String strPassWord = pref.getString("PassWord", "");
            edPassWord.setText(strPassWord);
            edUserName.setText(strUserName);
        }
    }

    private void reUser(String u, String p) {
        SharedPreferences pref = getSharedPreferences("USER_FILE2", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("UserName", u);
        edit.putString("PassWord", p);
        edit.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }
}
