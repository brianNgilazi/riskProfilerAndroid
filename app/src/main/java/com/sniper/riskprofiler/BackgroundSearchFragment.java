package com.sniper.riskprofiler;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BackgroundSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BackgroundSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BackgroundSearchFragment extends Fragment  implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;

    //UI components
    private EditText searchText;


    public BackgroundSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     * @return A new instance of fragment BackgroundSearchFragment.
     */

    public static BackgroundSearchFragment newInstance() {
        return new BackgroundSearchFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_background_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchText=view.findViewById(R.id.searchText);
        view.findViewById(R.id.searchButton).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        String query=searchText.getText().toString();
        mListener.onSearchButtonCLicked(query);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSearchButtonCLicked(String query);
    }
}
