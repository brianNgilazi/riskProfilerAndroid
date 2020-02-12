package com.sniper.riskprofiler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.sniper.riskprofiler.business.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters
    private static final String QUERY_PARAMETER = "QUERY_PARAMETER";

    //Firebase sendmail keys
    private static final String NAME_KEY = "name";
    private static final String EMAIL_KEY = "email";
    private static final String REPORT_ON_KEY = "report_on";

    private FirebaseFirestore db;
    private FirebaseFunctions firebaseFunctions;
    private List<Report> results;
    private String query;

    //UI components
    private EditText nameText;
    private EditText emailText;
    private EditText phoneText;
    private TextView titleText;


    private OnFragmentInteractionListener mListener;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param query Parameter 1.
     * @return A new instance of fragment SearchResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultsFragment newInstance(String query) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(QUERY_PARAMETER, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(QUERY_PARAMETER);
        }
        else query="";
        results=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameText=view.findViewById(R.id.nameEditText);
        emailText=view.findViewById(R.id.emailEditText);
        phoneText=view.findViewById(R.id.phoneEditText);

        titleText=view.findViewById(R.id.titleText);
        titleText.setText(String.format(Locale.getDefault(),"Request Report For: %s",query));

        view.findViewById(R.id.submitButton).setOnClickListener(this);
        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.setVisibility(View.INVISIBLE);
        performSearch(query);
    }


    public void onRequest() {
        if (mListener != null) {
            sendMail(nameText.getText().toString(),emailText.getText().toString(),query);
            mListener.onRequestMade();
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


    public void performSearch(String query){
        if(db==null)db=FirebaseFirestore.getInstance();
        db.collection(Report.COLLECTION_PATH)
                .whereEqualTo("identification",query)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                results.add(document.toObject(Report.class));
                            }
                            showResultsDialog();
                        }
                    }
                });
    }


    private void showResultsDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Risk Profiler");
        String message=results.size()>0?
                String.format(Locale.getDefault(),"We have %d hit(s) on your target.",results.size()):
                "No hits found on your target.";
        builder.setMessage(message);
        builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.request, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Objects.requireNonNull(getView()).setVisibility(View.VISIBLE);
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitButton:
                onRequest();
                break;
            case R.id.backButton:
                //TODO: Backwards Navigation
                break;

        }
    }

    private  void sendMail(String name, String email, String id){
        if(firebaseFunctions==null)firebaseFunctions=FirebaseFunctions.getInstance();
        Map<String, Object> data=new HashMap<>();
        data.put(NAME_KEY,name);
        data.put(EMAIL_KEY,email);
        data.put(REPORT_ON_KEY,id);

        firebaseFunctions.getHttpsCallable("sendMail")
                .call(data)
                .addOnCompleteListener(new OnCompleteListener<HttpsCallableResult>() {
            @Override
            public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                String message;
                if(task.isSuccessful())message="Request Email Sent";
                else message="Request Email Not Sent";
                Snackbar.make(Objects.requireNonNull(getView()),message,Snackbar.LENGTH_LONG).show();

            }
        });

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
        void onRequestMade();
    }
}
