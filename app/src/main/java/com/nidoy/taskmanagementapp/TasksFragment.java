package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TasksFragment extends Fragment {

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
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
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
            }
        });

        btnFAB.setText(getResources().getString(R.string.label_task));
        viewPager2.setAdapter(new TasksPagerAdapter(this.requireActivity()));
        tabLayoutMediator.attach();

        // Add event listeners
        btnFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });
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