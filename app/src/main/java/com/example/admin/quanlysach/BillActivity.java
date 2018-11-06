package com.example.admin.quanlysach;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanlysach.adapter.BillAdapter;
import com.example.admin.quanlysach.listener.OnClick;
import com.example.admin.quanlysach.listener.OnDelete;
import com.example.admin.quanlysach.listener.OnEdit;
import com.example.admin.quanlysach.model.Bill;
import com.example.admin.quanlysach.model.BillDetail;
import com.example.admin.quanlysach.DAO.BillDAO;
import com.example.admin.quanlysach.DAO.BillDetailDAO;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BillActivity extends AppCompatActivity implements OnDelete, OnClick, OnEdit {
    private Toolbar toolbarBill;
    private RecyclerView lvListBill;
    private List<Bill> billList;
    private BillAdapter billAdapter;
    private FloatingActionButton fabAddBill;
    private BillDAO billDAO;
    private BillDetailDAO billDetailDAO;
    private long date = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        initViews();
        initAction();
        billDetailDAO = new BillDetailDAO(this);
        billDAO = new BillDAO(this);
        billList = billDAO.getAllBills();
        Collections.reverse(billList);
        billAdapter = new BillAdapter(billList, this, this, this);
        lvListBill.setAdapter(billAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        lvListBill.setLayoutManager(manager);
    }

    private void initViews() {
        toolbarBill = findViewById(R.id.toolbarBill);
        setSupportActionBar(toolbarBill);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarBill.setTitleTextColor(Color.WHITE);
        toolbarBill.setTitle(getString(R.string.bill));
        toolbarBill.setNavigationIcon(R.drawable.undo);
        fabAddBill = findViewById(R.id.fabAddBill);
        lvListBill = findViewById(R.id.lvListBill);
        lvListBill.setHasFixedSize(true);
    }

    private void initAction() {
        fabAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddBill();
            }
        });

        toolbarBill.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvListBill.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAddBill.getVisibility() == View.VISIBLE) {
                    fabAddBill.hide();

                } else if (dy < 0 && fabAddBill.getVisibility() != View.VISIBLE) {
                    fabAddBill.show();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchViewBill = (SearchView) menu.findItem(R.id.search_item).getActionView();
        searchViewBill.setSearchableInfo(
                Objects.requireNonNull(searchManager).getSearchableInfo(getComponentName()));
        searchViewBill.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setLvListBill(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setLvListBill(newText);
                return true;
            }
        });
        searchViewBill.clearFocus();
        return true;
    }

    private void setLvListBill(String text) {
        billList = billDAO.getAllBillsLike(text.toUpperCase());
        if (billList.size() > 0) {
            billAdapter.changeDataset(billList);
            Collections.reverse(billList);
        } else {
            billList = billDAO.getAllBills();
            billAdapter.changeDataset(billList);
            Collections.reverse(billList);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                break;
        }
        return false;
    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.delete_message_bill));
        builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<BillDetail> billDetail = billDetailDAO.getAllBillDetailsByBillID(billList.get(position).getBill_ID());
                if (billDetail.size() > 0) {
                    Toast.makeText(BillActivity.this, getString(R.string.delete_billDetail), Toast.LENGTH_SHORT).show();
                } else {
                    long result = billDAO.deleteBill(billList.get(position).getBill_ID());
                    if (result > 0) {
                        Toast.makeText(BillActivity.this, getString(R.string.deleted) + " " + billList.get(position).getBill_ID(), Toast.LENGTH_SHORT).show();
                        billList = billDAO.getAllBills();
                        billAdapter.changeDataset(billList);
                        Collections.reverse(billList);
                    } else {
                        Toast.makeText(BillActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        builder.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showDialogAddBill() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_bill, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();
        Button Add = dialogView.findViewById(R.id.btnAddBill_dialog_AddBill);
        Button Cancel = dialogView.findViewById(R.id.btnCancel_dialog_AddBill);
        final EditText edBillID = dialogView.findViewById(R.id.edBillID_dialogAddBill);
        final TextView tvDate_add = dialogView.findViewById(R.id.tvDate);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billID = edBillID.getText().toString().trim().toUpperCase();

                if (date < 0)
                    Toast.makeText(BillActivity.this, R.string.pick_date, Toast.LENGTH_SHORT).show();
                else if (billID.isEmpty())
                    edBillID.setError(getString(R.string.notify_empty_BillID));

                else if (billID.length() > 7) {
                    edBillID.setError(getString(R.string.notify_max_bill_id));
                } else {
                    Boolean check = checkKeyPrimary(billID);
                    if (check) {
                        Bill bill = new Bill(billID, date);
                        long result = billDAO.insertBill(bill);
                        if (result > 0) {
                            billList = billDAO.getAllBills();
                            billAdapter.changeDataset(billList);
                            Collections.reverse(billList);
                            date = -1;
                            Toast.makeText(BillActivity.this, getString(R.string.addBill) + " " + billID, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent intent = new Intent(BillActivity.this, BillDetailActivity.class);
                            intent.putExtra("ID", billID);
                            startActivity(intent);
                        } else {
                            Toast.makeText(BillActivity.this, getString(R.string.add_failed_bill), Toast.LENGTH_SHORT).show();
                            date = -1;
                            dialog.dismiss();

                        }
                    } else {
                        edBillID.setError(getString(R.string.notify_BillID_exists));
                    }
                }
            }

        });

        Button pickDate = dialogView.findViewById(R.id.btnPickDate);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = -1;
                dialog.dismiss();
            }
        });
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tvDate_add);
            }
        });

    }

    @Override
    public void onEdit(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_bill, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        Button btnEdit = dialogView.findViewById(R.id.btnEdit_dialog_EditBill);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_dialog_EditBill);
        Button pickDate = dialogView.findViewById(R.id.btnPickDate_dialog_EditBill);
        final TextView tvDate_edit = dialogView.findViewById(R.id.tvDate_dialog_EditBill);

        tvDate_edit.setText(new Date(billList.get(position).getDate()).toString());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date < 0) {
                    Toast.makeText(BillActivity.this, getString(R.string.pick_date), Toast.LENGTH_SHORT).show();
                } else {
                    Bill bill = new Bill(billList.get(position).getBill_ID(), date);
                    long result = billDAO.updateBill(bill);
                    if (result > 0) {
                        Toast.makeText(BillActivity.this, getString(R.string.editedBill) + " " + billList.get(position).getBill_ID(), Toast.LENGTH_SHORT).show();
                        billList = billDAO.getAllBills();
                        billAdapter.changeDataset(billList);
                        Collections.reverse(billList);
                        dialog.dismiss();
                        date = -1;
                    } else {
                        Toast.makeText(BillActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                        date = -1;
                        dialog.dismiss();

                    }

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                date = -1;
            }
        });
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tvDate_edit);
            }
        });
    }

    private boolean checkKeyPrimary(String ID) {
        Bill bill = billDAO.getBillByID(ID);
        return bill == null;
    }

    private void showDatePicker(final TextView editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                date = calendar.getTimeInMillis();
                editText.setText(new Date(date).toString());
            }
        }, year, month, day);

        datePickerDialog.show();

    }

    @Override
    public void onClick(String billID) {
        Intent intent = new Intent(this, BillDetailActivity.class);
        intent.putExtra("ID", billID);
        startActivity(intent);
    }
}
