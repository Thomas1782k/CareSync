package com.tom.doctorpatient.service;

import com.tom.doctorpatient.dto.DoctorDTO;
import com.tom.doctorpatient.entity.Appointment;
import com.tom.doctorpatient.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public interface DoctorServiceInterface {

    public ModelAndView doctorLoginAuthentication(String uid, String pass , HttpSession session);
    public ModelAndView doctorRegistration(Doctor doc, String userId);
    public List<Appointment> appointmentDetails(HttpSession session, String doctorName);
    public ModelAndView updateRecordsDetails(HttpSession ses,String dname);
    public ModelAndView outPatientDetails(HttpSession ses , String pid,String pname,String doc,String symp,
                                          String date, String dname,String prescription);
    public ModelAndView updateOutpatient(HttpSession ses, String pid,String pname,
                                         String prescription,String dname);
    public ModelAndView outPatientGetDetails(String pid, String doc, String date, String symp,
                                             String pname, String dname);
    public ModelAndView patientHistoryGetDetails(HttpServletRequest req, String dname);


    Page<DoctorDTO> findAll(String query, int pageNo, int pageSize);
    Page<DoctorDTO> searchByDoctor(String query, int pageNo, int pageSize);
}
