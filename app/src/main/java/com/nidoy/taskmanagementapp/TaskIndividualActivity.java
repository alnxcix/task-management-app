package com.nidoy.taskmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class TaskIndividualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_individual);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        Task mTask = (Task) getIntent().getSerializableExtra("task");
        topAppBar.setTitle(Objects.requireNonNull(mTask).getmLabel());

        topAppBar.setOnMenuItemClickListener(v -> {
            switch (v.getItemId()) {
                case R.id.edit:
                    this.startActivityForResult(new Intent(this, TaskFormActivity.class).putExtra("task", mTask), MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE);
                    return true;
                case R.id.delete:
                    new AlertDialog.Builder(this).setMessage(this.getText(R.string.prompt_delete_task)).setPositiveButton(this.getText(R.string.yes), (dialog, which) -> {
                        MainActivity.mTaskViewModel.delete(mTask);
                        finish();
                    }).setNegativeButton(this.getText(R.string.no), null).create().show();
                    return true;
                default:
                    return false;
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task result = (Task) data.getSerializableExtra(TaskFormActivity.EXTRA_REPLY);
            MainActivity.mTaskViewModel.update(result);
            startActivity(getIntent().putExtra("task", result));
            finish();
        }
    }
}