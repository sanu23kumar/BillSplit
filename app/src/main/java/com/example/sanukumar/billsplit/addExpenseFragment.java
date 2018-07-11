package com.example.sanukumar.billsplit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class addExpenseFragment extends Fragment {

    RecyclerView expensesList;
    RecyclerView.LayoutManager listManager;
    RecyclerView.Adapter listAdapter;
    TextView addProductName, addCost, addDate, addFriends;
    ImageButton addCostButton, addCostCancelButton;
    LinearLayout addExpenseLayout;
    boolean addTabHidden = true;

    public addExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_expense_fragment, container, false);

        expensesList = v.findViewById(R.id.expensesList);
        addProductName = v.findViewById(R.id.addProductName);
        addCost = v.findViewById(R.id.addCost);
        addDate = v.findViewById(R.id.addDate);
        addFriends = v.findViewById(R.id.addFriends);
        addCostButton = v.findViewById(R.id.addCostButton);
        addCostCancelButton = v.findViewById(R.id.addCostCancelButton);
        addExpenseLayout = v.findViewById(R.id.addExpenseLayout);

        addCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addTabHidden == true) {
                    addExpenseLayout.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),200)).setDuration(200).start();
                    expensesList.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),200)).setDuration(200).start();
                    addCostButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),60)).setDuration(200).start();
                    addCostCancelButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),-60)).setDuration(200).start();
                    addTabHidden = false;
                } else {

                }
            }
        });

        addCostCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addTabHidden == false) {
                    addExpenseLayout.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),-200)).setDuration(200).start();
                    expensesList.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),-200)).setDuration(200).start();
                    addCostButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),-60)).setDuration(200).start();
                    addCostCancelButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),60)).setDuration(200).start();
                    addTabHidden = true;
                }
            }
        });

        //expensesList.setHasFixedSize(true);
        listManager = new LinearLayoutManager(getActivity().getApplicationContext());

        expensesList.setLayoutManager(listManager);

        listAdapter = new expensesListAdapter(new String[]{"Ram", "Shyam", "Mohan", "Gopal", "Dinesh", "Suresh"});
        expensesList.setAdapter(listAdapter);

        return v;
    }

}
