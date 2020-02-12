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
 * {@link ReporterDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReporterDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReporterDetailsFragment extends Fragment implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;

    //UI components
    private EditText nameText;
    private EditText emailText;
    private EditText phoneText;

    public ReporterDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *

     * @return A new instance of fragment ReporterDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReporterDetailsFragment newInstance() {
        return new ReporterDetailsFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reporter_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameText=view.findViewById(R.id.nameEditText);
        emailText=view.findViewById(R.id.emailEditText);
        phoneText=view.findViewById(R.id.phoneEditText);

        view.findViewById(R.id.submitButton).setOnClickListener(this);
        view.findViewById(R.id.backButton).setOnClickListener(this);
    }


    public void submitButtonPressed() {
        if (mListener != null) {
            mListener.onSubmitReport(nameText.getText().toString(),
                    emailText.getText().toString(),
                    phoneText.getText().toString());
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
        switch (v.getId()){
            case R.id.submitButton:
                submitButtonPressed();
                break;
            case R.id.backButton:
                //TODO: Backwards Navigation
                break;
        }
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
        void onSubmitReport(String fullName,String email,String phone);

        void onBackButtonPressed();
    }
}
