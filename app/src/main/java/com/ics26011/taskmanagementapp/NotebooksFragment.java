package com.ics26011.taskmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class NotebooksFragment extends Fragment {
    public NotebooksFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notebooks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements and variables
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final NotebookListAdapter adapter = new NotebookListAdapter(view.getContext());
        final ExtendedFloatingActionButton btnNew = requireActivity().findViewById(R.id.btnNew);
        // Setup
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        btnNew.setText(R.string.notebook);
        // Event listeners
        btnNew.setOnClickListener(v -> requireActivity().startActivityForResult(new Intent(view.getContext(), NotebookFormActivity.class), MainActivity.CREATE_NOTEBOOK_ACTIVITY_REQUEST_CODE));
        MainActivity.notebookViewModel = new ViewModelProvider(requireActivity()).get(NotebookViewModel.class);
        // Update the cached copy of the notebooks in the adapter.
        MainActivity.notebookViewModel.getNotebooksSortedByName().observe(getViewLifecycleOwner(), adapter::setNotebooks);
    }
}