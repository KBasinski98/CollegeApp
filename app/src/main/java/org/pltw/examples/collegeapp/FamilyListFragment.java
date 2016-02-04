package org.pltw.examples.collegeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ListFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by PLTW on 12/15/2015.
 */
public class FamilyListFragment extends ListFragment {

    private static final String TAG = "FamilyListFragment";
       private static final int WITHIN_8_YEARS = 2007;
    public static final String EXTRA_DOB =
            "org.pltw.examples.collegeapp.dob";
    Family mFamily;
    private EditText mEnterName;


    public FamilyListFragment(){
        mFamily = Family.get();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.family_members_title);

        FamilyMemberAdapter adapter = new FamilyMemberAdapter(mFamily.getFamily());
        setListAdapter(adapter);
        setHasOptionsMenu(true);
    }

    private class FamilyMemberAdapter extends ArrayAdapter {
        public FamilyMemberAdapter(ArrayList family) {
            super(getActivity(), 0, family);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_family_member, null);
            }

            FamilyMember f = (FamilyMember) getItem(position);

            Log.d(TAG, "The type of FamilyMember at position " + position + " is " + f.getClass().getName());

            TextView nameTextView =
                    (TextView)convertView
                            .findViewById(R.id.family_member_list_item_nameTextView);
            nameTextView.setText(f.toString());



            return convertView;


        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
         ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_family_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();

        switch (item.getItemId()) {

            case R.id.menu_item_new_guardian:
                Log.d(TAG, "Selected add new guardian.");
                String[] stringArray;

                Guardian guardian = new Guardian();
                for (FamilyMember f: Family.get().getFamily())
                    if (f.compareTo(guardian) == 0) {
                        Log.i(TAG, guardian + "That person already exists.");

                    } else {

                    }
                Family.get().addFamilyMember(guardian);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.menu_item_new_sibling:
                Log.d(TAG, "Selected add new sibling.");
                Sibling sibling = new Sibling();
                for (FamilyMember f: Family.get().getFamily()) {

                    if (f.compareTo(sibling) == 0) {
                        Log.i(TAG, sibling + "That person already exists.");
                    }
                }
                Family.get().addFamilyMember(sibling);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }
    /*

    private abstract class DatePickerOnDateChangedListener implements DatePicker.OnDateChangedListener {
        @Override
        public void onDateChanged(EditText view, String Name) {
            mEnterName = new EditText(editText2);
            getArguments();

        }

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerOnDateChangedListener mDatePickerOnDateChangedListener = new DatePickerOnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        };
        mEnterName = (Date)getArguments().getSerializable(EXTRA_DOB);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_dob, null);

        DatePicker DoBPicker = (DatePicker)v.findViewById(R.id.dialog_dob_dobPicker);
        DoBPicker.setCalendarViewShown(false);
        DoBPicker.init(year, monthOfYear, dayOfMonth, mDatePickerOnDateChangedListener);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dob_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) return;
        try {
            if (mDoB.getYear() <= WITHIN_8_YEARS) {
                String message = NumberOutOfRangeException.joinMessageAndYear(
                        "Who are you, Michael Kearney?", mDoB.getYear());
                throw new NumberOutOfRangeException(message);
            }
        } catch (NumberOutOfRangeException s) {
            Log.e(TAG, s.getMessage());
        }
        Intent i = new Intent();
        i.putExtra(EXTRA_DOB, mDoB);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }
    */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.d(TAG, "Creating Context Menu.");
        getActivity().getMenuInflater().inflate(R.menu.family_list_item_context,
                menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "Context item selected.");
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();
        FamilyMember familyMember = (FamilyMember) adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_family_member:
                Family.get().deleteFamilyMember(familyMember);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FamilyMember f = (FamilyMember) ((FamilyMemberAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, f.toString() + " was clicked." + FamilyMemberActivity.class);
        Intent i = new Intent(getActivity(), FamilyMemberActivity.class);
        i.putExtra(FamilyMember.EXTRA_RELATION, f.getRelation());
        i.putExtra(FamilyMember.EXTRA_INDEX, position);
        startActivity(i);
    }



}
