package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TasksFragment extends Fragment {

    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    // Member variables
    public static TaskViewModel mTaskViewModel;

    public TasksFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize variables
        ExtendedFloatingActionButton btnFAB = requireActivity().findViewById(R.id.btnFAB);
        ViewPager2 viewPager2 = requireActivity().findViewById(R.id.viewPager);
        TabLayout tabLayout = requireActivity().findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    setTab(tab, R.string.label_open_tasks, R.drawable.ic_wb_incandescent_24px, 100);
                    break;
                }
                case 1: {
                    setTab(tab, R.string.label_pending_tasks, R.drawable.ic_hourglass_empty_black_24dp, 75);
                    break;
                }
                case 2: {
                    setTab(tab, R.string.label_finished_tasks, R.drawable.ic_done_all_24px, 50);
                    break;
                }
                case 3: {
                    setTab(tab, R.string.label_overdue_tasks, R.drawable.ic_error_outline_black_24dp, 25);
                    break;
                }
            }
        });

        btnFAB.setText(getResources().getString(R.string.label_task));
        viewPager2.setAdapter(new TasksPagerAdapter(this.requireActivity()));
        tabLayoutMediator.attach();

        // Add event listeners
        btnFAB.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), NewTaskActivity.class);
            startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        requireActivity();
        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task task = (Task) data.getSerializableExtra(NewTaskActivity.EXTRA_REPLY);
            mTaskViewModel.insert(task);
            Toast.makeText(requireContext(), "Saved.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(requireContext(), "Not saved.", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setTab(TabLayout.Tab tab, int label, int icon, int num) {
        tab.setText(getResources().getString(label));
        tab.setIcon(getResources().getDrawable(icon));

        // Add a badge
        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
        badgeDrawable.setNumber(num);
        badgeDrawable.setMaxCharacterCount(3);
    }
}