package org.pltw.examples.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wdumas on 2/18/2015.
 */
public class SiblingFragment extends FamilyMemberFragment {
    private static final String KEY_FIRST_NAME = "firstname";
    private final String FILENAME = "sibling.json";

    private static final String TAG = "SiblingFragment";
    private Sibling mSibling;
    private TextView mFirstName;
    private TextView mLastName;
    private EditText mEnterFirstName;
    private EditText mEnterLastName;
    private Context mAppContext;



    //@Override
    /*public void onCreate(Bundle savedInstanceState) {
        mGuardian = new Guardian();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sibling, container, false);

        mSibling = (Sibling)Family.get().getFamily().get(getActivity().
                getIntent().getIntExtra(FamilyMember.EXTRA_INDEX,0));

        mSibling = new Sibling();

        mFirstName = (TextView)rootView.findViewById(R.id.first_name);
        mEnterFirstName = (EditText)rootView.findViewById(R.id.enter_first_name);
        mLastName = (TextView)rootView.findViewById(R.id.last_name);
        mEnterLastName = (EditText)rootView.findViewById(R.id.enter_last_name);

        mFirstName.setText(mSibling.getFirstName());
        mLastName.setText(mSibling.getLastName());

        FirstNameTextChanger firstNameTextChanger = new FirstNameTextChanger();
        LastNameTextChanger lastNameTextChanger = new LastNameTextChanger();

        mEnterFirstName.addTextChangedListener(firstNameTextChanger);

        mAppContext = this.getActivity();
        Log.d(TAG, "Context: " + mAppContext);
        // mStorer = new GuardianJSONStorer(mAppContext, FILENAME);

        mEnterLastName.addTextChangedListener(lastNameTextChanger);
        return rootView;
    }

    private class FirstNameTextChanger implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mSibling.setFirstName(s.toString());
            ArrayList family = Family.get().getFamily();
            int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX,0);
            family.set(index, (FamilyMember) mSibling);
        }

        @Override
        public void afterTextChanged(Editable s) {
            mFirstName.setText(mSibling.getFirstName());
        }
    }

    private class LastNameTextChanger implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mSibling.setLastName(s.toString());
            ArrayList family = Family.get().getFamily();
            int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX,0);
            family.set(index, (FamilyMember)mSibling);
        }

        @Override
        public void afterTextChanged(Editable s) {
            mLastName.setText(mSibling.getLastName());
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment started.");
    }

    public boolean saveGuardian() {
        try {
            //   mStorer.save(mGuardian);
            Log.d(TAG, "profile saved to file.");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving profile: ", e);
            return false;
        }
    }

    public SiblingFragment() {
        try {

            //    mGuardian = mStorer.load(mProfile);//looks for if there is already a profile and loads it
        } catch (Exception e) {//if there isn't a profile is catches it and handles the exception
            mSibling= new Sibling();//creates a new profile
            Log.e(TAG, "Error loading profile: " + FILENAME, e);//tells the user that it couldn't load the profile in the Logcat.
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveGuardian();//saves all recent entered stuff when paused
        Log.d(TAG, "Fragment paused.");
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            //     mGuardian = mStorer.load(mProfile);//profile thinhgs are stored in storer menu
            Log.d(TAG, "Loaded " + mSibling.getFirstName());//lists the first name of the applicant
            mFirstName.setText(mSibling.getFirstName());//sets the firstname to what the getter has the first name as.
            mLastName.setText(mSibling.getLastName());//does the same as first name but with the last name.

        } catch (Exception e) {
            mSibling = new Sibling();
            Log.e(TAG, "Error loading profile from: " + FILENAME, e);
        }
        Log.d(TAG, "Fragment resumed.");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Fragment stopped.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment destroyed.");
    }

}