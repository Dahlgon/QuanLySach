package com.example.admin.quanlysach;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanlysach.adapter.UserAdapter;
import com.example.admin.quanlysach.listener.OnDelete;
import com.example.admin.quanlysach.listener.OnEdit;
import com.example.admin.quanlysach.model.User;
import com.example.admin.quanlysach.DAO.DatabaseManager;
import com.example.admin.quanlysach.DAO.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserActivity extends AppCompatActivity implements OnEdit, OnDelete {
    private Toolbar toolbarUser;
    private RecyclerView lvListUser;
    private List<User> userList;
    private UserAdapter user_adapter;
    private UserDAO userDAO;
    private final String format_phone = "0\\d{9,10}";
    private String strUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initViews();
        SharedPreferences preferences = getSharedPreferences("USER_FILE2", MODE_PRIVATE);
        strUserName = preferences.getString("UserName", "").toUpperCase();
        userDAO = new UserDAO(this);
        toolbarUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userList = new ArrayList<>();
        user_adapter = new UserAdapter(userList, this, this);
        lvListUser.setAdapter(user_adapter);
        getUser();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        lvListUser.setLayoutManager(manager);

    }

    private void initViews() {
        toolbarUser = findViewById(R.id.toolbarUser);
        lvListUser = findViewById(R.id.lvListUser);
        setSupportActionBar(toolbarUser);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarUser.setTitleTextColor(Color.WHITE);
        toolbarUser.setTitle(getString(R.string.title_user));
        toolbarUser.setNavigationIcon(R.drawable.undo);
    }

    private void getUser() {
        Cursor cursorUser = userDAO.getUser();
        if (cursorUser == null) {
            return;
        }
        if (cursorUser.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(cursorUser.getString(cursorUser.getColumnIndex(DatabaseManager.COLUMN_USERNAME)));
                user.setPassword(cursorUser.getString(cursorUser.getColumnIndex(DatabaseManager.COLUMN_PASSWORD)));
                user.setName(cursorUser.getString(cursorUser.getColumnIndex(DatabaseManager.COLUMN_NAME)));
                user.setPhonenumber(cursorUser.getString(cursorUser.getColumnIndex(DatabaseManager.COLUMN_PHONE_NUMBER)));
                userList.add(user);
            } while (cursorUser.moveToNext());
        }
        cursorUser.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addUser:
                showDialogAddUser();
                break;
            case R.id.changePass:
                showDialogChangePassWord();
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    private void showDialogAddUser() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        final EditText edUserName = dialogView.findViewById(R.id.edUserName_dialogAdd_User);
        final EditText edPassWord = dialogView.findViewById(R.id.edPassWord_dialogAdd_User);
        final EditText edPassWord2 = dialogView.findViewById(R.id.edPassWord2_dialogAdd_User);
        final EditText edPhone = dialogView.findViewById(R.id.edPhone_dialogAdd_User);
        final EditText edName = dialogView.findViewById(R.id.edName_dialogAdd_User);
        Button btnAdd = dialogView.findViewById(R.id.btnAddUser_dialogAdd_User);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_dialogAdd_User);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUserName.getText().toString().trim().toUpperCase();
                String passWord = edPassWord.getText().toString().trim();
                String passWord2 = edPassWord2.getText().toString().trim();
                String name = edName.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();

                if (userName.isEmpty() || passWord.isEmpty() || passWord2.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    if (userName.isEmpty())
                        edUserName.setError(getString(R.string.notify_empty_user));
                    if (passWord.isEmpty())
                        edPassWord.setError(getString(R.string.notify_empty_pass));
                    if (passWord2.isEmpty())
                        edPassWord2.setError(getString(R.string.notify_empty_pass2));
                    if (name.isEmpty())
                        edName.setError(getString(R.string.notify_empty_name));
                    if (phone.isEmpty())
                        edPhone.setError(getString(R.string.notify_empty_sdt));
                } else if (passWord2.length() < 6 || passWord.length() < 6 || userName.length() > 50 || passWord.length() > 50 || passWord2.length() > 50 || name.length() > 50) {
                    if (passWord.length() < 6)
                        edPassWord.setError(getString(R.string.notify_length_pass));
                    if (passWord2.length() < 6)
                        edPassWord2.setError(getString(R.string.notify_length_pass));
                    if (userName.length() > 50)
                        edUserName.setError(getString(R.string.notify_length_user));
                    if (passWord.length() > 50)
                        edPassWord.setError(getString(R.string.notify_length_pass2));
                    if (passWord2.length() > 50)
                        edPassWord2.setError(getString(R.string.notify_length_pass2));
                    if (name.length() > 50)
                        edName.setError(getString(R.string.notify_length_name));
                } else if (!passWord.equals(passWord2)) {
                    edPassWord2.setError(getString(R.string.notify_same_pass));
                } else if (!phone.matches(format_phone)) {
                    edPhone.setError(getString(R.string.notify_same_sdt));
                } else {
                    User user2 = userDAO.getUser(userName);
                    if (user2 == null) {
                        if (strUserName.equals(getString(R.string.admin))) {
                            Integer a = userDAO.insertUser(userName, passWord, name, phone);
                            if (a == 1) {
                                Toast.makeText(UserActivity.this, getString(R.string.addUserName) + " " + userName, Toast.LENGTH_SHORT).show();
                                userList.clear();
                                getUser();
                                user_adapter.notifyDataSetChanged();
                            } else if (a == -1) {
                                Toast.makeText(UserActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(UserActivity.this, getString(R.string.permision), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edUserName.setError(getString(R.string.user_exits));
                    }
                }
            }
        });
    }

    private void showDialogChangePassWord() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_change_pass, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();

        Button change = dialogView.findViewById(R.id.btnChange_dialogChange_Pass);
        Button cancel = dialogView.findViewById(R.id.btnCancel_dialogChange_Pass);
        final TextView edpassOld = dialogView.findViewById(R.id.edPassWordOld_dialogChange_Pass);
        final TextView edpassNew = dialogView.findViewById(R.id.edPassWordNew_dialogChange_Pass);
        final TextView edpassNew2 = dialogView.findViewById(R.id.edPassWordNew2_dialogChange_Pass);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passOld = edpassOld.getText().toString().trim();
                String passNew = edpassNew.getText().toString().trim();
                String passNew2 = edpassNew2.getText().toString().trim();

                if (passNew.isEmpty() || passNew2.isEmpty() || passOld.isEmpty()) {
                    if (passOld.isEmpty())
                        edpassOld.setError(getString(R.string.notify_empty_passold));
                    if (passNew.isEmpty())
                        edpassNew.setError(getString(R.string.notify_empty_passnew));
                    if (passNew.isEmpty())
                        edpassNew2.setError(getString(R.string.notify_empty_passnew2));
                } else if (passNew.length() < 6 || passNew2.length() < 6 || passOld.length() < 6||passNew.length() >50 || passNew2.length() >50 || passOld.length() >50) {
                    if (passNew.length() < 6)
                        edpassNew.setError(getString(R.string.notify_length_pass));
                    if (passNew2.length() < 6)
                        edpassNew2.setError(getString(R.string.notify_length_pass));
                    if (passOld.length() < 6)
                        edpassOld.setError(getString(R.string.notify_length_pass));
                    if (passOld.length() >50)
                        edpassOld.setError(getString(R.string.notify_length_pass2));
                    if (passNew.length() >50)
                        edpassNew.setError(getString(R.string.notify_length_pass2));
                    if (passNew2.length() >50)
                        edpassNew2.setError(getString(R.string.notify_length_pass2));
                } else if (!passNew.equals(passNew2)) {
                    edpassNew2.setError(getString(R.string.notify_same_pass));
                } else {
                    User user = userDAO.getUser(strUserName);
                    if (user.getPassword().equals(passOld)) {
                        int result = userDAO.updatePassWord(strUserName, passNew2);
                        if (result == 1) {
                            Toast.makeText(UserActivity.this, getString(R.string.change_pass_success) + " " + strUserName, Toast.LENGTH_SHORT).show();
                        } else if (result == -1) {
                            Toast.makeText(UserActivity.this, getString(R.string.change_pass_failed), Toast.LENGTH_SHORT).show();
                        }
                        dialog1.dismiss();
                    } else {
                        edpassOld.setError(getString(R.string.wrong_pass));
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.question_delete_user));
        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (userList.get(position).getUsername().equals(getString(R.string.admin))) {
                    Toast.makeText(UserActivity.this, getString(R.string.cannot_delete_admin), Toast.LENGTH_SHORT).show();
                } else {
                    if (strUserName.equals(getString(R.string.admin))) {
                        int result = userDAO.deleteUser(userList.get(position).getUsername());
                        if (result == 1) {
                            Toast.makeText(UserActivity.this, getString(R.string.deleted_user) + " " + userList.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                            userList.clear();
                            getUser();
                            user_adapter.notifyDataSetChanged();
                        } else if (result == -1) {
                            Toast.makeText(UserActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserActivity.this, getString(R.string.permision), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onEdit(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_user, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();
        final EditText edName = dialogView.findViewById(R.id.edName_dialogEditUser);
        final EditText edPhone = dialogView.findViewById(R.id.edPhone_dialogEditUser);
        String name_old = userList.get(position).getName();
        String phone_old = userList.get(position).getPhonenumber();
        edName.setText(name_old);
        edPhone.setText(phone_old);


        Button btnEdit = dialogView.findViewById(R.id.btnEdit_dialogEditUser);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name_new = edName.getText().toString().trim();
                final String phone_new = edPhone.getText().toString();
                if (name_new.isEmpty() || phone_new.isEmpty()) {
                    if (name_new.isEmpty()) {
                        edName.setError(getString(R.string.notify_empty_name));
                    }
                    if (phone_new.isEmpty()) {
                        edPhone.setError(getString(R.string.notify_empty_sdt));
                    }
                } else if (name_new.length()>50) {
                        edName.setError(getString(R.string.notify_length_name));
                } else if (!phone_new.matches(format_phone)) {
                    edPhone.setError(getString(R.string.notify_same_sdt));
                } else {
                    if (strUserName.equals(getString(R.string.admin))) {
                        int result = userDAO.updateUser(userList.get(position).getUsername(), name_new, phone_new);
                        if (result == 1) {
                            Toast.makeText(UserActivity.this, getString(R.string.editedUser) + " " + userList.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                            userList.clear();
                            getUser();
                            user_adapter.notifyDataSetChanged();
                        } else if (result == -1) {
                            Toast.makeText(UserActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(UserActivity.this, getString(R.string.permision), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_dialogEditUser);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
