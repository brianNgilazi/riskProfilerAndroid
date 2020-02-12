package com.sniper.riskprofiler;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sniper.riskprofiler.business.Report;

public class NewReportActivity extends AppCompatActivity implements ReportFragment.OnFragmentInteractionListener,ReporterDetailsFragment.OnFragmentInteractionListener {

    private Report aReport;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        loadFragment(new ReportFragment());
        db=FirebaseFirestore.getInstance();
    }

    @Override
    public void onFragmentInteraction(Report aReport) {
        this.aReport=aReport;
        loadFragment(ReporterDetailsFragment.newInstance());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onSubmitReport(String fullName, String email, String phone) {
        aReport.setReporterEmail(email);
        aReport.setReporterName(fullName);
        aReport.setReporterNumber(phone);
        db.collection(Report.COLLECTION_PATH).add(aReport).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(NewReportActivity.this,"Report Created",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackButtonPressed() {
        onBackPressed();
    }
}
