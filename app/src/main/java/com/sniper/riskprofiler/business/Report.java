package com.sniper.riskprofiler.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Report {
    private String name;
    private String surname;
    private String id;
    private String details;
    private String reporterName;
    private String reporterEmail;
    private String reporterNumber;
    public static final String COLLECTION_PATH="reports";


    public Report(){}
    public Report(String name, String surname, String id, String details){
        this.name=name;
        this.surname=surname;
        this.id=id;
        this.details=details;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getReporterNumber() {
        return reporterNumber;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public void setReporterNumber(String reporterNumber) {
        this.reporterNumber = reporterNumber;
    }

    public static Map<String,Object> asMap(Report r){
        Map<String, Object> aReportMap=new HashMap<>();
        aReportMap.put("name",r.name);
        aReportMap.put("surname",r.surname);
        aReportMap.put("identification",r.id);
        aReportMap.put("details",r.details);
        aReportMap.put("reporter_name",r.reporterName);
        aReportMap.put("reporter_email",r.reporterEmail);
        aReportMap.put("reporter_number",r.reporterNumber);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        aReportMap.put("date",sdf.format(new Date()));

        return aReportMap;
    }
}
