package com.sniper.riskprofiler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sniper.riskprofiler.business.Report;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    //UI components
    private EditText nameText;
    private EditText surnameText;
    private EditText idText;
    private EditText detailsText;



    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     * @return A new instance of fragment ReportFragment.
     */

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameText=view.findViewById(R.id.nameEditText);
        surnameText=view.findViewById(R.id.surnameEditText);
        idText=view.findViewById(R.id.idEditText);
        detailsText=view.findViewById(R.id.detailsEditText);

        //CLick listeners
        view.findViewById(R.id.nextButton).setOnClickListener(this);
        view.findViewById(R.id.cancelButton).setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    private void nextButtonPressed() {
        if (mListener != null) {
            Report aReport=new Report(nameText.getText().toString(),
                    surnameText.getText().toString(),
                    idText.getText().toString(),
                    detailsText.getText().toString());
            Log.d("Click","in nextButtonPressed");
            mListener.onFragmentInteraction(aReport);
        }
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
        Log.d("Click","in OnClick listener");
        switch(v.getId()){
            case R.id.nextButton:
                Log.d("Click","next button clicked");
                nextButtonPressed();
                break;
            case R.id.cancelButton:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
                //TODO: action when cancelled
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Report aReport);
    }
}
