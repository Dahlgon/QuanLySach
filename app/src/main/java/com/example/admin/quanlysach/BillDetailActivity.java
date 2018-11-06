package com.example.admin.quanlysach;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanlysach.adapter.BillDetailAdapter;
import com.example.admin.quanlysach.listener.OnDelete;
import com.example.admin.quanlysach.listener.OnEdit;
import com.example.admin.quanlysach.model.BillDetail;
import com.example.admin.quanlysach.model.Book;
import com.example.admin.quanlysach.DAO.BillDetailDAO;
import com.example.admin.quanlysach.DAO.BookDAO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BillDetailActivity extends AppCompatActivity implements OnDelete, OnEdit {
    private Toolbar toolbarBillDetail;
    private FloatingActionButton fabAddBillDetail;
    private RecyclerView lvListBillDetail;
    private List<BillDetail> billDetailList;
    private BillDetailAdapter detailAdapter;
    private String billID = null;
    private BillDetailDAO billDetailDAO;
    private BookDAO bookDAO;
    private TextView tvRevenue;
    private Integer sum = 0;
    private List<BillDetail> billDetails;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        initViews();
        initAction();
        bookDAO = new BookDAO(this);
        billDetailDAO = new BillDetailDAO(this);
        billDetailList = billDetailDAO.getAllBillDetailsByBillID(billID);
        Collections.reverse(billDetailList);
        detailAdapter = new BillDetailAdapter(billDetailList, this, this);
        lvListBillDetail.setAdapter(detailAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        lvListBillDetail.setLayoutManager(manager);
        tvRevenue.setText(getSum() + " VNĐ");
    }

    private void initViews() {
        lvListBillDetail = findViewById(R.id.lvListBillDetail);
        fabAddBillDetail = findViewById(R.id.fabAddBillDetail);
        toolbarBillDetail = findViewById(R.id.toolbarBillDetail);
        setSupportActionBar(toolbarBillDetail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarBillDetail.setTitleTextColor(Color.WHITE);
        toolbarBillDetail.setNavigationIcon(R.drawable.undo);
        billID = getIntent().getStringExtra("ID");
        toolbarBillDetail.setTitle(billID);
        tvRevenue = findViewById(R.id.tvRevenue);

    }

    private void initAction() {
        fabAddBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddBillDetail();
            }
        });
        toolbarBillDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvListBillDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAddBillDetail.getVisibility() == View.VISIBLE) {
                    fabAddBillDetail.hide();
                } else if (dy < 0 && fabAddBillDetail.getVisibility() != View.VISIBLE) {
                    fabAddBillDetail.show();
                }
            }
        });
    }

    private void showDialogAddBillDetail() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_bill_detail, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        Button btnAdd = dialogView.findViewById(R.id.btnAdd_dialogBillDetail);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_dialogBillDetail);
        final EditText edBookID = dialogView.findViewById(R.id.edBookID_dialogBillDetail);
        final EditText edQuantity = dialogView.findViewById(R.id.edQuantity_dialogBillDetail);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strBookID = edBookID.getText().toString().trim().toUpperCase();
                String strQuantity = edQuantity.getText().toString().trim();
                if (strBookID.isEmpty() || strQuantity.isEmpty()) {
                    if (strBookID.isEmpty())
                        edBookID.setError(getString(R.string.notify_empty_BookID));
                    if (strQuantity.isEmpty())
                        edQuantity.setError(getString(R.string.notify_empty_Quantity));
                } else if (Integer.parseInt(strQuantity) <= 0) {
                    edQuantity.setError(getString(R.string.notify_Quantity_equal_0));
                } else {

                    Book book = bookDAO.getBookByID(strBookID);
                    if (book != null) {
                        billDetails = billDetailDAO.getAllBillDetailsByBookID(strBookID);
                        int quantity = getAllQuantity();
                        if (Integer.parseInt(strQuantity) + quantity >
                                Integer.parseInt(book.getQuantity())) {
                            edQuantity.setError(getString(R.string.notify_Quantity) + " " + (Integer.parseInt(book.getQuantity()) - (quantity)) + " cuốn");
                        } else {
                            int checkBook = checkBookID(strBookID);
                            // hàm check lấy vị trí xuất hiện của bookID nếu có thì chỉ sửa nếu chưa có thì thêm vào
                            if (checkBook >= 0) {
                                long result = billDetailDAO.updateBillDetail(billDetailList.get(checkBook).getBillDetailID(), billID, strBookID, "" + (Integer.parseInt(billDetailList.get(checkBook).getQuantity()) + Integer.parseInt(strQuantity)));
                                if (result > 0) {
                                    billDetailList = billDetailDAO.getAllBillDetailsByBillID(billID);
                                    detailAdapter.changeDataset(billDetailList);
                                    Collections.reverse(billDetailList);
                                    Toast.makeText(BillDetailActivity.this, getString(R.string.addBillDetail), Toast.LENGTH_SHORT).show();
                                    changeSum();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(BillDetailActivity.this, getString(R.string.add_failed_bill), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                            } else {
                                long result = billDetailDAO.insertBillDetail(billID, strBookID, strQuantity);
                                if (result > 0) {
                                    billDetailList = billDetailDAO.getAllBillDetailsByBillID(billID);
                                    detailAdapter.changeDataset(billDetailList);
                                    Collections.reverse(billDetailList);
                                    Toast.makeText(BillDetailActivity.this, getString(R.string.addBillDetail), Toast.LENGTH_SHORT).show();
                                    changeSum();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(BillDetailActivity.this, getString(R.string.add_failed_bill), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                            }
                        }
                    } else {
                        edBookID.setError(getString(R.string.notify_BookID_not_exists));
                    }

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.delete_message_billdetail));
        builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long result = billDetailDAO.deleteBillDetail(billDetailList.get(position).getBillDetailID());
                if (result > 0) {
                    Toast.makeText(BillDetailActivity.this, getString(R.string.delete), Toast.LENGTH_SHORT).show();
                    billDetailList = billDetailDAO.getAllBillDetailsByBillID(billID);
                    detailAdapter.changeDataset(billDetailList);
                    Collections.reverse(billDetailList);
                    changeSum();
                } else {
                    Toast.makeText(BillDetailActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onEdit(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_billdetail, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        Button btnEdit = dialogView.findViewById(R.id.btnEdit_dialogEdit_BillDetail);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_dialogEdit_BillDetail);
        final EditText edBookID = dialogView.findViewById(R.id.edBookID_dialogEdit_BillDetail);
        final EditText edQuantity = dialogView.findViewById(R.id.edQuantity_dialogEdit_BillDetail);
        Log.e("Bill ID",billDetailList.get(position).getBillID());
        edBookID.setText(billDetailList.get(position).getBookID());
        edQuantity.setText(billDetailList.get(position).getQuantity());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strBookID_New = edBookID.getText().toString().trim().toUpperCase();
                String strQuantity_New = edQuantity.getText().toString().trim();
                if (strBookID_New.isEmpty() || strQuantity_New.isEmpty()) {
                    if (strBookID_New.isEmpty())
                        edBookID.setError(getString(R.string.notify_empty_BookID));
                    if (strQuantity_New.isEmpty())
                        edQuantity.setError(getString(R.string.notify_empty_Quantity));
                } else if (Integer.parseInt(strQuantity_New) <= 0) {
                    edQuantity.setError(getString(R.string.notify_Quantity_equal_0));
                } else {
                    Book book = bookDAO.getBookByID(strBookID_New);
                    if (book != null) {
                        billDetails = billDetailDAO.getAllBillDetailsByBookID(strBookID_New);
                        int quantity = getAllQuantity();
                        int checkBook = checkBookID(strBookID_New);
                        if (checkBook >= 0) {
                            if (checkBook != position) {
                                if (Integer.parseInt(strQuantity_New) + quantity > Integer.parseInt(book.getQuantity())) {
                                    edQuantity.setError(getString(R.string.notify_Quantity) + " " + (Integer.parseInt(book.getQuantity()) - (quantity)) + " cuốn");
                                } else {
                                    billDetailDAO.deleteBillDetail(billDetailList.get(position).getBillDetailID());
                                    long result = billDetailDAO.updateBillDetail(billDetailList.get(checkBook).getBillDetailID(), billID, strBookID_New, "" + (Integer.parseInt(billDetailList.get(checkBook).getQuantity()) + Integer.parseInt(strQuantity_New)));
                                    if (result > 0) {
                                        billDetailList = billDetailDAO.getAllBillDetailsByBillID(billID);
                                        detailAdapter.changeDataset(billDetailList);
                                        Collections.reverse(billDetailList);
                                        Toast.makeText(BillDetailActivity.this, getString(R.string.editedBill_Detail), Toast.LENGTH_SHORT).show();
                                        changeSum();
                                        Log.e("TH1", "TH1: Có rồi thì ghi đè lên và xóa vị trí cũ");
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(BillDetailActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            } else {
                                if (Integer.parseInt(strQuantity_New) + quantity - Integer.parseInt(billDetailList.get(position).getQuantity()) > Integer.parseInt(book.getQuantity())) {
                                    edQuantity.setError(getString(R.string.notify_Quantity) + " " + (Integer.parseInt(book.getQuantity()) - (quantity)) + " cuốn");
                                } else {
                                    long result = billDetailDAO.updateBillDetail(billDetailList.get(position).getBillDetailID(), billID, strBookID_New, strQuantity_New);
                                    if (result > 0) {
                                        billDetailList = billDetailDAO.getAllBillDetailsByBillID(billID);
                                        detailAdapter.changeDataset(billDetailList);
                                        Collections.reverse(billDetailList);
                                        Toast.makeText(BillDetailActivity.this, getString(R.string.editedBill_Detail), Toast.LENGTH_SHORT).show();
                                        changeSum();
                                        Log.e("TH2", "TH2: Sửa vị trí cũ cùng mã sách");
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(BillDetailActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }
                        } else {
                            if (Integer.parseInt(strQuantity_New) + quantity > Integer.parseInt(book.getQuantity())) {
                                edQuantity.setError(getString(R.string.notify_Quantity) + " " + (Integer.parseInt(book.getQuantity()) - (quantity)) + " cuốn");
                            } else {
                                long result = billDetailDAO.updateBillDetail(billDetailList.get(position).getBillDetailID(), billID, strBookID_New, strQuantity_New);
                                if (result > 0) {
                                    billDetailList = billDetailDAO.getAllBillDetailsByBillID(billID);
                                    detailAdapter.changeDataset(billDetailList);
                                    Collections.reverse(billDetailList);
                                    Toast.makeText(BillDetailActivity.this, getString(R.string.editedBill_Detail), Toast.LENGTH_SHORT).show();
                                    changeSum();
                                    Log.e("TH3", "TH3: Không cùng mã sách");
                                    dialog.dismiss();

                                } else {
                                    Toast.makeText(BillDetailActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        }

                    } else {
                        edBookID.setError(getString(R.string.notify_BookID_not_exists));
                    }

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private Integer getSum() {
        for (int i = 0; i < billDetailList.size(); i++) {
            Book book = bookDAO.getBookByID(billDetailList.get(i).getBookID());
            sum = sum + (Integer.parseInt(book.getPrice()) * Integer.parseInt(billDetailList.get(i).getQuantity()));
        }
        return sum;
    }

    @SuppressLint("SetTextI18n")
    private void changeSum() {
        sum = 0;
        tvRevenue.setText(getSum() + " VNĐ");
    }

    private int checkBookID(String ID) {
        int position = -1;
        for (int i = 0; i < billDetailList.size(); i++) {
            if (ID.equalsIgnoreCase(billDetailList.get(i).getBookID())) {
                position = i;
            }
        }
        return position;
    }

    private int getAllQuantity() {
        int quantity = 0;
        for (int i = 0; i < billDetails.size(); i++) {
            quantity = quantity + Integer.parseInt(billDetails.get(i).getQuantity());
        }
        return quantity;
    }

}


