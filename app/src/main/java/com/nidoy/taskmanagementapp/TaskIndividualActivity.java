package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;

public class TaskIndividualActivity extends AppCompatActivity {
    @SuppressLint({"SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_individual);
        // Initialize UI elements + variables
        Task task = (Task) getIntent().getSerializableExtra("task");
        assert task != null;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        FloatingActionButton btnEdit = findViewById(R.id.btnEdit);
        int dynamicColor = task.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE;
        // Setup
        topAppBar.setTitle(task.getLabel());
        topAppBar.setBackgroundColor(task.getThemeId());
        topAppBar.setTitleTextColor(dynamicColor);
        topAppBar.getMenu().findItem(R.id.menuDelete).getIcon().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        ((TextView) findViewById(R.id.imgLabel)).getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(task.getThemeId(), PorterDuff.Mode.SRC_IN));
        ((TextView) findViewById(R.id.txtLabel)).setText(task.getLabel());
        ((TextView) findViewById(R.id.txtDueDate)).setText(task.getDue().format(DateTimeFormatter.ofPattern("EEE., MMMM dd, yyyy")));
        ((TextView) findViewById(R.id.txtTime)).setText(task.getDue().format(DateTimeFormatter.ofPattern("hh:mm a")));
        ((ImageView) findViewById(R.id.imgStatus)).setImageResource(task.getStatusId() == 0 ? R.drawable.ic_wb_incandescent_24px : task.getStatusId() == 1 ? R.drawable.ic_hourglass_empty_black_24dp : R.drawable.ic_done_all_black_24dp);
        ((TextView) findViewById(R.id.txtStatus)).setText(task.getStatusId() == 2 ? getString(R.string.finished_on) + " " + task.getFinish().format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")) : getResources().getStringArray(R.array.arr_task_status)[task.getStatusId()]);
        ((TextView) findViewById(R.id.txtNotes)).setText(task.getNotes());
        btnEdit.setBackgroundTintList(ColorStateList.valueOf(task.getThemeId()));
        btnEdit.getDrawable().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        // Add event listeners
        findViewById(R.id.menuDelete).setOnClickListener(v -> new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.dialog_title_delete_task))
                .setMessage(getString(R.string.dialog_message_delete_task))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    MainActivity.taskViewModel.delete(task);
                    finish();
                }).setNegativeButton(getString(R.string.cancel), null)
                .show());
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