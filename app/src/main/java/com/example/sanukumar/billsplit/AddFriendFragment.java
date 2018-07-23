package com.example.sanukumar.billsplit;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFriendFragment extends Fragment {

    RecyclerView frinedsList;
    LinearLayoutManager listManager;
    FriendsListAdapter listAdapter;
    LinearLayout addFriendLayout;
    CardView friendListCard;

    EditText addFriendName, addFriendPhone, addFriendEmail, addFriendNickName;
    ImageButton addFriendButton, addFriendCancelButton;
    boolean addTabHidden = true;

    ArrayList<FriendsDataModel> DataList;

    public AddFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_add_friend, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        frinedsList = v.findViewById(R.id.friendsList);
        addFriendName = v.findViewById(R.id.addFriendName);
        addFriendEmail = v.findViewById(R.id.addFriendEmailAddress);
        addFriendPhone = v.findViewById(R.id.addFriendPhoneNumber);
        addFriendNickName = v.findViewById(R.id.addFriendNickName);
        addFriendButton = v.findViewById(R.id.addFriendButton);
        addFriendCancelButton = v.findViewById(R.id.addFriendCancelButton);
        addFriendLayout = v.findViewById(R.id.addFriendLayout);
        friendListCard = v.findViewById(R.id.friendsListCard);

        DataList = new ArrayList<>(0);

//        To test the recycler view, uncomment:
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());
        DataList.add(new FriendsDataModel());

        //expensesList.setHasFixedSize(true);
        listManager = new LinearLayoutManager(getActivity().getApplicationContext());
        listManager.setReverseLayout(true);
        listManager.setStackFromEnd(true);
        frinedsList.setLayoutManager(listManager);

        listAdapter = new FriendsListAdapter(DataList);
        frinedsList.setAdapter(listAdapter);

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addTabHidden == true) {
                    expandAddExpense();
                } else {
                    String friendName = addFriendName.getText().toString();
                    String phone = addFriendPhone.getText().toString();
                    String email = addFriendEmail.getText().toString();
                    String nickName = addFriendNickName.getText().toString();

                    if (friendName.length() >= 1 && phone.length() >= 1 && email.length() >= 1) {

                        DataList.add(new FriendsDataModel(friendName, phone, email, nickName));
                        listAdapter.notifyItemInserted(DataList.size());

                        frinedsList.smoothScrollToPosition(DataList.size());
                        collapseAddExpense();
                        clearFields();

                        hideKeyboard(addFriendButton);

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), "Enter Valid Data", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });

        addFriendCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addTabHidden == false) {
                    collapseAddExpense();
                    hideKeyboard(addFriendCancelButton);
                }
            }
        });



        return v;
    }

    private void expandAddExpense() {
        addFriendLayout.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),210)).setDuration(300).start();
        friendListCard.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),210)).setDuration(300).start();
        addFriendButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),60)).setDuration(600).start();
        addFriendCancelButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),-60)).setDuration(600).start();
        addTabHidden = false;
    }

    private void collapseAddExpense() {
        addFriendLayout.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),-210)).setDuration(300).start();
        friendListCard.animate().translationYBy(Converter.pxFromDp(getActivity().getApplicationContext(),-210)).setDuration(300).start();
        addFriendButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),-60)).setDuration(600).start();
        addFriendCancelButton.animate().translationXBy(Converter.pxFromDp(getActivity().getApplicationContext(),60)).setDuration(600).start();
        addTabHidden = true;
    }

    private void clearFields() {
        addFriendName.setText(null);
        addFriendPhone.setText(null);
        addFriendEmail.setText(null);
        addFriendNickName.setText(null);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
