package com.example.sanukumar.billsplit;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class addExpenseFragment extends Fragment {

    RecyclerView expensesList;
    LinearLayoutManager listManager;
    expensesListAdapter listAdapter;

    EditText addProductName, addCost, addFriends, addCategory;
    TextView addDate;
    ImageButton addCostButton, addCostCancelButton;
    LinearLayout addExpenseLayout;
    CardView expenseListCard;

    boolean addTabHidden = true;

    ArrayList<DataModel> DataList;

    DatePickerDialog.OnDateSetListener onDateSetListener;

    public addExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_expense_fragment, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        expensesList = v.findViewById(R.id.expensesList);
        addProductName = v.findViewById(R.id.addProductName);
        addCost = v.findViewById(R.id.addCost);
        addDate = v.findViewById(R.id.addDate);
        addFriends = v.findViewById(R.id.addFriends);
        addCostButton = v.findViewById(R.id.addCostButton);
        addCostCancelButton = v.findViewById(R.id.addCostCancelButton);
        addExpenseLayout = v.findViewById(R.id.addExpenseLayout);
        addCategory = v.findViewById(R.id.addCategory);
        expenseListCard = v.findViewById(R.id.expenseListCard);

        DataList = new ArrayList<>(0);

//        To test the recycler view, uncomment:
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());
        DataList.add(new DataModel());



        //expensesList.setHasFixedSize(true);
        listManager = new LinearLayoutManager(getActivity().getApplicationContext());
        listManager.setReverseLayout(true);
        listManager.setStackFromEnd(true);
        expensesList.setLayoutManager(listManager);

        listAdapter = new expensesListAdapter(DataList);
        expensesList.setAdapter(listAdapter);

        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        DateFormat df = new SimpleDateFormat("h:mm a");
        String time = df.format(Calendar.getInstance().getTime());

        addDate.setHint(day + "/" + month + "/" + year + " - " + time);

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Material_Dialog_NoActionBar,
                        onDateSetListener,
                        year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00bbee")));
                datePickerDialog.show();

                hideKeyboard(addDate);

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                addDate.setText(i1 + "/" + i2 + "/" + i);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        addDate.setText(addDate.getText() + " - " + i + ":" + i1);

                    }
                }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);

                timePickerDialog.show();

            }
        };

        addCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addTabHidden == true) {
                    expandAddExpense();
                } else {
                    String articleName = addProductName.getText().toString();
                    String addCateg = addCategory.getText().toString();
                    String addCostVal = addCost.getText().toString();
                    String dateTime = addDate.getText().toString();
                    String addFriendNames = addFriends.getText().toString();

                    if (articleName.length() >= 1 && addCostVal.length() >= 1 && addFriendNames.length() >= 1) {

                        DataList.add(new DataModel(articleName, addCostVal, dateTime, addFriendNames));
                        listAdapter.notifyItemInserted(DataList.size());

                        expensesList.smoothScrollToPosition(DataList.size());
                        collapseAddExpense();
                        clearFields();

                        hideKeyboard(addCost);

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), "Enter Valid Data", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });

        addCostCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addTabHidden == false) {
                    collapseAddExpense();
                    hideKeyboard(addCostCancelButton);
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (HomeScreenActivityActivity.displayedAddExpenseForTheFirstTime) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    expandAddExpense();

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseAddExpense();
                        }
                    }, 600);

                }
            }, 1400);
            HomeScreenActivityActivity.displayedAddExpenseForTheFirstTime = false;
        }
    }

    private void expandAddExpense() {
        addExpenseLayout.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),260)).setDuration(400).start();
        expenseListCard.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),260)).setDuration(400).start();
        addCostButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),60)).setDuration(800).start();
        addCostCancelButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),-60)).setDuration(800).start();
        addTabHidden = false;
    }

    private void collapseAddExpense() {
        addExpenseLayout.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),-260)).setDuration(400).start();
        expenseListCard.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),-260)).setDuration(400).start();
        addCostButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),-60)).setDuration(800).start();
        addCostCancelButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),60)).setDuration(800).start();
        addTabHidden = true;
    }

    private void clearFields() {
        addProductName.setText(null);
        addCost.setText(null);
        addDate.setText(null);
        addFriends.setText(null);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

