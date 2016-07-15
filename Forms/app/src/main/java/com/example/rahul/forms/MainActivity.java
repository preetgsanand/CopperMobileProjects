package com.example.rahul.forms;

import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;

import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager pager;
    MyPagerAdapter adapter;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSignInForm();
    }
    public void checkPersonal(View view) {
        if(adapter.getSize() > 1){
            return;
        }
        view = adapter.mfragments.get(0).getView();
        EditText firstName = (EditText)view.findViewById(R.id.firstName);
        EditText lastName = (EditText)view.findViewById(R.id.lastName);
        Spinner codeSpinner = (Spinner)view.findViewById(R.id.countryCode);
        String code = codeSpinner.getSelectedItem().toString();
        EditText phone = (EditText)view.findViewById(R.id.phone);
        EditText username = (EditText)view.findViewById(R.id.username);
        EditText domain = (EditText)view.findViewById(R.id.domain);
        EditText password = (EditText)view.findViewById(R.id.password);
        TextView error = (TextView)view.findViewById(R.id.error);
        if(firstName.getText().length() != 0 && lastName.getText().length() != 0 &&
                phone.getText().length() != 0 &&
                username.getText().length() != 0 &&
                domain.getText().length() != 0 &&
                password.getText().length() != 0) {
            error.setText("");
            Personal personal = new Personal(firstName.getText().toString() + lastName.getText().toString(),
                    code + " " + phone.getText().toString(),
                    username.getText().toString() + "@" + domain.getText().toString(),
                    password.getText().toString());
            personal.save();
            adapter.addTab(AdditionalInfoFragment.newInstance());
            pager.setCurrentItem(adapter.getSize()-1);
        }
        else {
            error.setText("Fill all the fields properly..");
        }
    }
    public void checkAdditional(View view) {
        if(adapter.getSize() > 2){
            return;
        }
        view = adapter.mfragments.get(1).getView();
        EditText fatherName = (EditText)view.findViewById(R.id.fatherName);
        EditText motherName = (EditText)view.findViewById(R.id.motherName);
        EditText houseNumber = (EditText)view.findViewById(R.id.houseNo);
        EditText locality = (EditText)view.findViewById(R.id.locality);
        EditText streetLine = (EditText)view.findViewById(R.id.streetLine);
        EditText city = (EditText)view.findViewById(R.id.city);
        EditText pincode = (EditText)view.findViewById(R.id.pincode);
        TextView error = (TextView)view.findViewById(R.id.error);
        if(fatherName.getText().length() != 0 &&
                motherName.getText().length() != 0 &&
                houseNumber.getText().length() != 0 &&
                locality.getText().length() != 0 &&
                streetLine.getText().length() != 0 &&
                city.getText().length() != 0 &&
                pincode.getText().length() != 0) {
            error.setText("");
            Additional additional = new Additional(fatherName.getText().toString(),
                    motherName.getText().toString(),
                    houseNumber.getText().toString() + ", " + locality.getText().toString() + ", " +
                            streetLine.getText().toString() + ", " + city.getText().toString() + ", " + pincode.getText().toString());
            additional.save();
            adapter.addTab(EducationInfoFragment.newInstance());
            pager.setCurrentItem(adapter.getSize() - 1);

        }
        else {
            error.setText("Fill all the fields properly..");
        }
    }
    public void checkEducation(View view) {
        if(adapter.getSize() > 3){
            return;
        }
        view = adapter.mfragments.get(2).getView();
        EditText schoolName = (EditText)view.findViewById(R.id.schoolName);
        EditText schoolCity = (EditText)view.findViewById(R.id.schoolCity);
        EditText schoolState = (EditText)view.findViewById(R.id.schoolState);
        EditText xMarks = (EditText)view.findViewById(R.id.xMarks);
        EditText x2Marks = (EditText)view.findViewById(R.id.x2Marks);
        TextView error = (TextView)view.findViewById(R.id.error);
        if(schoolName.getText().length() != 0 &&
                schoolCity.getText().length() != 0 &&
                schoolState.getText().length() != 0 &&
                x2Marks.getText().length() != 0 &&
                xMarks.getText().length() != 0) {
            error.setText("");
            Education education = new Education(schoolName.getText().toString(),
                    schoolCity.getText().toString(),
                    schoolState.getText().toString(),
                    xMarks.getText().toString(),
                    x2Marks.getText().toString());
            education.save();
            adapter.addTab(JEEInfoFragment.newInstance());
            pager.setCurrentItem(adapter.getSize() - 1);

        }
        else {
            error.setText("Fill all the fields properly..");
        }
    }
    public void checkJEE(View view) {
        view = adapter.mfragments.get(3).getView();
        EditText entryId = (EditText)view.findViewById(R.id.entryId);
        EditText mainMarks = (EditText)view.findViewById(R.id.mainMarks);
        EditText mainRank = (EditText)view.findViewById(R.id.mainRank);
        EditText advanceMarks = (EditText)view.findViewById(R.id.advanceMarks);
        EditText advanceRank = (EditText)view.findViewById(R.id.advanceRank);
        TextView error = (TextView)view.findViewById(R.id.error);
        if(entryId.getText().length() != 0 &&
                mainMarks.getText().length() != 0 &&
                mainRank.getText().length() != 0 &&
                advanceMarks.getText().length() != 0 &&
                advanceRank.getText().length() != 0) {
            error.setText("");
            Jee jee = new Jee(entryId.getText().toString(),
                    mainRank.getText().toString(),
                    mainMarks.getText().toString(),
                    advanceRank.getText().toString(),
                    advanceMarks.getText().toString());
            jee.save();
            adapter.addTab(PhotoFragment.newInstance());
            pager.setCurrentItem(adapter.getSize() - 1);

        }
        else {
            error.setText("Fill all the fields properly..");
        }
    }
    public void setSignInForm() {
        SignInFragment signInFragment = new SignInFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,signInFragment).commit();
    }

    /**
     *
     * @param view
     */
    public void setSignUpForm(View view) {
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        Log.e("set", "set");
        pager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        adapter = new MyPagerAdapter(getSupportFragmentManager(),pager,tabLayout);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for(int i = 0 ; i < adapter.getSize()  ; i++) {
            switch(i) {
                case 0:tabLayout.getTabAt(i).setIcon(R.drawable.people);
                    break;
                case 1:tabLayout.getTabAt(i).setIcon(R.drawable.book);
                    break;
                case 2:tabLayout.getTabAt(i).setIcon(R.drawable.home);
                    break;
                case 3:tabLayout.getTabAt(i).setIcon(R.drawable.graduation);
                    break;
                case 4:tabLayout.getTabAt(i).setIcon(R.drawable.photo);
                    break;
            }
        }
    }
    public void setProfileDisplay(long id) {
        ProfileDisplayFragment profileDisplayFragment = ProfileDisplayFragment.newInstance(id);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,profileDisplayFragment).commit();
    }
    public void hideSoftKeyboard(View view) {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
