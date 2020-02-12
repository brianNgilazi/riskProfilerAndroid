package com.sniper.riskprofiler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SearchActivity extends AppCompatActivity
        implements BackgroundSearchFragment.OnFragmentInteractionListener,
        SearchResultsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        loadFragment(BackgroundSearchFragment.newInstance());
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();

    }


    @Override
    public void onSearchButtonCLicked(String query) {
        loadFragment(SearchResultsFragment.newInstance(query));
    }


    @Override
    public void onRequestMade() {

    }
}
