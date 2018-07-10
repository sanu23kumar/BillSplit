package com.example.sanukumar.billsplit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class addExpenseFragment extends Fragment {

    RecyclerView expensesList;
    RecyclerView.LayoutManager listManager;
    RecyclerView.Adapter listAdapter;

    public addExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_expense_fragment, container, false);

        expensesList = v.findViewById(R.id.expensesList);
        //expensesList.setHasFixedSize(true);
        listManager = new LinearLayoutManager(getActivity().getApplicationContext());

        expensesList.setLayoutManager(listManager);

        listAdapter = new expensesListAdapter(new String[]{"Ram", "Shyam", "Mohan", "Gopal", "Dinesh", "Suresh"});
        expensesList.setAdapter(listAdapter);

        return v;
    }

}
