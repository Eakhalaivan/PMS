package com.pharmadesk.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    @Column(name = "doctor_name")
    private String doctorName;

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    @Column(name = "prescription_date", nullable = false)
    private LocalDateTime prescriptionDate;

    public LocalDateTime getPrescriptionDate() { return prescriptionDate; }
    public void setPrescriptionDate(LocalDateTime prescriptionDate) { this.prescriptionDate = prescriptionDate; }

    @Column(nullable = false)
    private String status; // PENDING, DISPENSED, CANCELLED

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
