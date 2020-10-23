package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;

public class TaskIndividualActivity extends AppCompatActivity {

    @SuppressLint({"SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_individual);
        // Initialize task
        Task task = (Task) getIntent().getSerializableExtra("task");
        assert task != null;
        // Initialize and setup UI elements
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        FloatingActionButton btnEdit = findViewById(R.id.btnEdit);
        topAppBar.setTitle(task.getLabel());
        topAppBar.setBackgroundColor(task.getThemeId());
        topAppBar.setTitleTextColor(task.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
        topAppBar.getMenu().findItem(R.id.menuDelete).getIcon().setColorFilter(new PorterDuffColorFilter(task.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        ((TextView) findViewById(R.id.imgLabel)).getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(task.getThemeId(), PorterDuff.Mode.SRC_IN));
        ((TextView) findViewById(R.id.txtLabel)).setText(task.getLabel());
        ((ImageView) findViewById(R.id.imgStatus)).setImageResource(task.getStatusId() == 0 ? R.drawable.ic_wb_incandescent_24px : task.getStatusId() == 1 ? R.drawable.ic_hourglass_empty_black_24dp : R.drawable.ic_done_all_24px);
        ((TextView) findViewById(R.id.txtStatus)).setText(getResources().getStringArray(R.array.arr_task_status)[task.getStatusId()]);
        ((TextView) findViewById(R.id.txtDueDate)).setText(new SimpleDateFormat("EEE., MMM. dd, yyyy").format(task.getDue()));
        ((TextView) findViewById(R.id.txtTime)).setText(new SimpleDateFormat("hh:mm aa").format(task.getDue()));
        ((TextView) findViewById(R.id.txtNotes)).setText(task.getNotes());
        findViewById(R.id.divider3).setVisibility(task.getNotes() == null || task.getNotes().equals("") ? View.GONE : View.VISIBLE);
        findViewById(R.id.imgNotes).setVisibility(task.getNotes() == null || task.getNotes().equals("") ? View.GONE : View.VISIBLE);
        btnEdit.setBackgroundTintList(ColorStateList.valueOf(task.getThemeId()));
        btnEdit.getDrawable().setColorFilter(new PorterDuffColorFilter(task.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        // Add event listeners
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menuDelete) {
                new AlertDialog.Builder(this).setMessage(this.getText(R.string.prompt_delete_task)).setPositiveButton(this.getText(R.string.yes), (dialog, which) -> {
                    MainActivity.taskViewModel.delete(task);
                    finish();
                }).setNegativeButton(this.getText(R.string.no), null).create().show();
            }
            return true;
        });
        btnEdit.setOnClickListener(v -> this.startActivityForResult(new Intent(this, TaskFormActivity.class).putExtra("task", task), MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task result = (Task) data.getSerializableExtra(TaskFormActivity.EXTRA_REPLY);
            MainActivity.taskViewModel.update(result);
            startActivity(getIntent().putExtra("task", result));
            finish();
        }
    }
}