package com.example.admin.quanlysach;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.quanlysach.adapter.GenreAdapter;
import com.example.admin.quanlysach.listener.OnDelete;
import com.example.admin.quanlysach.listener.OnEdit;
import com.example.admin.quanlysach.model.Book;
import com.example.admin.quanlysach.model.Genre;
import com.example.admin.quanlysach.DAO.BookDAO;
import com.example.admin.quanlysach.DAO.GenreDAO;
import java.util.List;
import java.util.Objects;

public class GenreActivity extends AppCompatActivity implements OnDelete, OnEdit {

    private Toolbar toolbarTypeBook;
    private FloatingActionButton fabAddTypeBook;
    private RecyclerView lvListTypeBook;
    private List<Genre> genreList;
    private GenreAdapter typeBookAdapter;
    private GenreDAO genreDAO;
    private BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genrre);

        inisViews();
        initAction();
        genreDAO = new GenreDAO(this);
        genreList = genreDAO.getAllTypeBooks();
        bookDAO = new BookDAO(this);
        typeBookAdapter = new GenreAdapter(genreList, this, this);
        lvListTypeBook.setAdapter(typeBookAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        lvListTypeBook.setLayoutManager(manager);
    }

    private void inisViews() {
        toolbarTypeBook = findViewById(R.id.toolbarTypeBook);
        lvListTypeBook = findViewById(R.id.lvListTypeBook);
        fabAddTypeBook = findViewById(R.id.fabAddTypeBook);
        setSupportActionBar(toolbarTypeBook);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarTypeBook.setTitleTextColor(Color.WHITE);
        toolbarTypeBook.setTitle(getString(R.string.title_typebook));
        toolbarTypeBook.setNavigationIcon(R.drawable.undo);
    }

    private void initAction() {
        fabAddTypeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddTypeBook();
            }
        });
        toolbarTypeBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvListTypeBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAddTypeBook.getVisibility() == View.VISIBLE) {
                    fabAddTypeBook.hide();
                } else if (dy < 0 && fabAddTypeBook.getVisibility() != View.VISIBLE) {
                    fabAddTypeBook.show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchViewBook = (SearchView) menu.findItem(R.id.search_item).getActionView();
        searchViewBook.setSearchableInfo(
                Objects.requireNonNull(searchManager).getSearchableInfo(getComponentName()));
        searchViewBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setLvListBook(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setLvListBook(newText);
                return true;
            }
        });
        searchViewBook.clearFocus();
        return true;
    }

    private void setLvListBook(String text) {
        genreList = genreDAO.getAllTypeBooksLike(text.toUpperCase());
        if (genreList.size() > 0) {
            typeBookAdapter.changeDataset(genreList);
        } else {
            genreList = genreDAO.getAllTypeBooks();
            typeBookAdapter.changeDataset(genreList);
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
    private void showDialogAddTypeBook() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_type_book, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();
        Button add = dialogView.findViewById(R.id.btnAdd_dialog_AddTypeBook);
        Button cancel = dialogView.findViewById(R.id.btnCancel_dialog_AddTypeBook);
        final EditText edTypeBookID = dialogView.findViewById(R.id.edTypeBookID_dialog_AddTypeBook);
        final EditText edName = dialogView.findViewById(R.id.edTypeBookName_dialog_AddTypeBook);
        final EditText edPosition = dialogView.findViewById(R.id.edPosition_dialog_AddTypeBook);
        final EditText edDescription = dialogView.findViewById(R.id.edDescription_dialog_AddTypeBook);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strId = edTypeBookID.getText().toString().trim().toUpperCase();
                String strName = edName.getText().toString().trim();
                String strPosition = edPosition.getText().toString().trim();
                String strDescription = edDescription.getText().toString().trim();

                if (strId.isEmpty() || strName.isEmpty() || strDescription.isEmpty() || strPosition.isEmpty()) {
                    if (strId.isEmpty()) {
                        edTypeBookID.setError(getString(R.string.notify_empty_IDTypeBook));
                    }
                    if (strName.isEmpty()) {
                        edName.setError(getString(R.string.notify_empty_NameTypeBook));
                    }
                    if (strDescription.isEmpty()) {
                        edDescription.setError(getString(R.string.notify_empty_Description));
                    }
                    if (strPosition.isEmpty()) {
                        edPosition.setError(getString(R.string.notify_empty_Position));
                    }
                } else if (strId.length()>5||strName.length()>50||strDescription.length()>255) {
                    if(strId.length()>5)
                        edTypeBookID.setError(getString(R.string.notify_length_genreID));
                    if(strName.length()>50)
                        edName.setError(getString(R.string.notify_length_Genre));
                    if(strDescription.length()>255)
                        edDescription.setError(getString(R.string.notify_length_Des));
                } else {
                    Genre genre = genreDAO.getTypeBookByID(strId);
                    if (genre == null) {
                        Genre genre1 = new Genre(strId, strName, strDescription, strPosition);
                        long result = genreDAO.insertTypeBook(genre1);
                        if (result > 0) {
                            genreList = genreDAO.getAllTypeBooks();
                            typeBookAdapter.changeDataset(genreList);
                            dialog.dismiss();
                            Toast.makeText(GenreActivity.this, getString(R.string.add_successfully_typebook) + " " + strId, Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(GenreActivity.this, getString(R.string.add_failed_typebook), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edTypeBookID.setError(getString(R.string.notify_typebook_exists));
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.delete_message_genre));
        builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Book> bookList = bookDAO.getAllBooksByIDTypeBook(genreList.get(position).getId());
                if (bookList.size() > 0) {
                    Toast.makeText(GenreActivity.this, getString(R.string.deleted_genre), Toast.LENGTH_SHORT).show();
                } else {
                    long result = genreDAO.deleteTypeBook(genreList.get(position).getId());
                    if (result > 0) {
                        Toast.makeText(GenreActivity.this, getString(R.string.deleted_Genre) + " " + genreList.get(position).getId(), Toast.LENGTH_SHORT).show();
                        genreList = genreDAO.getAllTypeBooks();
                        typeBookAdapter.changeDataset(genreList);
                    } else {
                        Toast.makeText(GenreActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onEdit(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_genre, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();
        Button btnEdit = dialogView.findViewById(R.id.btnEdit_dialog_editGenre);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_dialog_editGenre);
        final EditText edName = dialogView.findViewById(R.id.edTypeBookName_dialog_editGenre);
        final EditText edPosition = dialogView.findViewById(R.id.edPosition_dialog_editGenre);
        final EditText edDescription = dialogView.findViewById(R.id.edDescription_dialog_editGenre);

        String strName_old = genreList.get(position).getName();
        String strPosition_old = genreList.get(position).getPosition();
        String strDescription_old = genreList.get(position).getDescription();

        edName.setText(strName_old);
        edDescription.setText(strDescription_old);
        edPosition.setText(strPosition_old);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = edName.getText().toString().trim();
                String strPosition = edPosition.getText().toString().trim();
                String strDescription = edDescription.getText().toString().trim();

                if (strName.isEmpty() || strDescription.isEmpty() || strPosition.isEmpty()) {

                    if (strName.isEmpty()) {
                        edName.setError(getString(R.string.notify_empty_NameTypeBook));
                    }
                    if (strDescription.isEmpty()) {
                        edDescription.setError(getString(R.string.notify_empty_Description));
                    }
                    if (strPosition.isEmpty()) {
                        edPosition.setError(getString(R.string.notify_empty_Position));
                    }
                } else if (strName.length()>50||strDescription.length()>255) {
                    if(strName.length()>50)
                        edName.setError(getString(R.string.notify_length_Genre));
                    if(strDescription.length()>255)
                        edDescription.setError(getString(R.string.notify_length_Des));
                } else {
                    Genre genre = new Genre(genreList.get(position).getId(), strName, strDescription, strPosition);
                    long result = genreDAO.updateTypeBook(genre);
                    if (result > 0) {
                        Toast.makeText(GenreActivity.this, getString(R.string.editedTypeBook) + " " + genreList.get(position).getId(), Toast.LENGTH_SHORT).show();
                        genreList = genreDAO.getAllTypeBooks();
                        typeBookAdapter.changeDataset(genreList);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(GenreActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}