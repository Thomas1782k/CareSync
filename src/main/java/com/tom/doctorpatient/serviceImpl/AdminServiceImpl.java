package com.tom.doctorpatient.serviceImpl;

import com.tom.doctorpatient.entity.Admin;
import com.tom.doctorpatient.entity.Appointment;
import com.tom.doctorpatient.entity.Doctor;
import com.tom.doctorpatient.entity.Patient;
import com.tom.doctorpatient.repository.AdminRepo;
import com.tom.doctorpatient.repository.AppointmentRepo;
import com.tom.doctorpatient.repository.DoctorRepo;
import com.tom.doctorpatient.repository.PatientRepo;
import com.tom.doctorpatient.service.AdminServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminServiceInterface {

    private DoctorRepo dr;
    private PatientRepo pr;
    private AdminRepo ar;
    private AppointmentRepo apr;

    public AdminServiceImpl (DoctorRepo dr, PatientRepo pr, AdminRepo ar,
                             AppointmentRepo apr){
        this.dr = dr;
        this.pr = pr;
        this.ar = ar;
        this.apr = apr;
    }

    @Override
    public ModelAndView adminLoginAuthentication(String uid, String pass) {
        try {
            Admin d = ar.findByUserId(uid);
            ModelAndView mv = new ModelAndView();
            if (d.getUserId().equals(uid) && d.getPassword().equals(pass)) {
                List<Doctor> doc = dr.findAll();
                List<Patient> pat = pr.findAll();

                mv.addObject("doctors", doc);
                mv.addObject("patients", pat);
                mv.setViewName("adminhome");
            } else if (d.getUserId().equals(uid) && !(d.getPassword().equals(pass))) {
                mv.addObject("errmsg", "Incorrect Password");
                mv.setViewName("adminlogin");
            }
            return mv;

        } catch (Exception e) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("errmsg", "Invalid UserId");
            mv.setViewName("adminlogin");
            //System.out.println(e);
            return mv;
        }
    }

    @Override
    public ModelAndView appointmentFixing(int pid, String pname, String dtime, String symptoms, String doctor) {
        ModelAndView mv = new ModelAndView();

        String valueFromHtml = dtime;
        String dateTimeString = valueFromHtml.replace("T", " ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime appointmentDate = LocalDateTime.parse(dateTimeString, formatter);

        Appointment ap = apr.findByPid(pid);
        Appointment dcr = apr.findByAppointmentDate(appointmentDate);

        if (dcr != null) {
            if (!(dcr.getAppointmentDate().equals(appointmentDate) && dcr.getDoctor().equals(doctor))) {
                if (ap != null) {
                    ap.setAppointmentDate(appointmentDate);
                    ap.setSymptoms(symptoms);
                    ap.setDoctor(doctor);
                    mv.addObject("msg1", "Appointment Fixed");
                    apr.save(ap);
                } else {
                    Appointment appo = new Appointment(pid, pname, appointmentDate, symptoms, doctor);
                    mv.addObject("msg1", "Appointment Fixed");
                    apr.save(appo);
                }
            } else {
                mv.addObject("msg", "Change the time/date");
            }
        } else {
            if (ap != null) {
                ap.setAppointmentDate(appointmentDate);
                ap.setSymptoms(symptoms);
                ap.setDoctor(doctor);
                mv.addObject("msg1", "Appointment Fixed");
                apr.save(ap);
            } else {
                Appointment appo = new Appointment(pid, pname, appointmentDate, symptoms, doctor);
                mv.addObject("msg1", "Appointment Fixed");
                apr.save(appo);
            }
        }

        List<Doctor> doc = dr.findAll();
        List<Patient> pat = pr.findAll();
        Patient pch = pr.findByPid(pid);

        mv.addObject("doctors", doc);
        mv.addObject("patients", pat);
        mv.setViewName("adminhome");
        return mv;
    }
}
