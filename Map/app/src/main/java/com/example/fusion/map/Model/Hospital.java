package com.example.fusion.map.Model;

import java.util.List;

/**
 * Created by puchi on 13-Oct-17.
 */

public class Hospital {
    public double latituade;
    public double longitude;
    public String name;
    public String contactNumber;
    public String address;
    public String catagory;
    public HospitalSpeciality speciality;
    public BlodBank blodbank;
    public String time;
    public List<HospitalDoctor> doctorList;


    public Hospital() {
    }
}
