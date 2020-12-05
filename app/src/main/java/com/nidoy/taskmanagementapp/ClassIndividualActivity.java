package com.nidoy.taskmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClassIndividualActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_individual);
        // Initialize UI elements and variables
        Class c = getIntent().getSerializableExtra("class") == null ? null : (Class) getIntent().getSerializableExtra("class");
        assert c != null;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        FloatingActionButton btnEdit = findViewById(R.id.btnEdit);
        int dynamicColor = c.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE;
        // Setup
        topAppBar.setTitle(c.getName() == null ? getString(R.string.new_class) : c.getName());
        topAppBar.setBackgroundColor(c.getThemeId());
        topAppBar.setTitleTextColor(dynamicColor);
        topAppBar.getMenu().findItem(R.id.menuDelete).getIcon().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        ((TextView) findViewById(R.id.imgClassName)).getCompoundDrawables()[0].setColorFilter(c.getThemeId(), PorterDuff.Mode.SRC_IN);
        ((TextView) findViewById(R.id.txtClassName)).setText(c.getName());
        ((TextView) findViewById(R.id.txtInstructors)).setText(c.getInstructors());
        ((TextView) findViewById(R.id.txtVenue)).setText(c.getVenue());
        ((TextView) findViewById(R.id.txtDay)).setText(getResources().getStringArray(R.array.arr_week)[c.getDayId()]);
        ((TextView) findViewById(R.id.txtStartTime)).setText(c.getStartTime().toString());
        ((TextView) findViewById(R.id.txtEndTime)).setText(c.getEndTime().toString());
        btnEdit.setBackgroundTintList(ColorStateList.valueOf(c.getThemeId()));
        btnEdit.getDrawable().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        // Event listeners
        topAppBar.getMenu().findItem(R.id.menuDelete).setOnMenuItemClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.dialog_title_delete_class))
                    .setMessage(getString(R.string.dialog_message_delete_class))
                    .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                        ScheduleIndividualActivity.classViewModel.delete(c);
                        finish();
                    }).setNegativeButton(getString(R.string.cancel), null)
                    .show();
            return true;
        });
        btnEdit.setOnClickListener(v -> this.startActivityForResult(new Intent(this, ClassFormActivity.class).putExtra("class", c), ScheduleIndividualActivity.UPDATE_CLASS_ACTIVITY_REQUEST_CODE));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScheduleIndividualActivity.UPDATE_CLASS_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ScheduleIndividualActivity.classViewModel.update((Class) data.getSerializableExtra(ClassFormActivity.EXTRA_REPLY));
            finish();
        }
    }
}