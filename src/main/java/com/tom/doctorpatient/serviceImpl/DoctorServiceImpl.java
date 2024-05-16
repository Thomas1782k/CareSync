package com.tom.doctorpatient.serviceImpl;

import com.tom.doctorpatient.entity.Appointment;
import com.tom.doctorpatient.entity.Doctor;
import com.tom.doctorpatient.entity.PatientRecord;
import com.tom.doctorpatient.entity.Updateapp;
import com.tom.doctorpatient.repository.AppointmentRepo;
import com.tom.doctorpatient.repository.DoctorRepo;
import com.tom.doctorpatient.repository.PatientRecordRepo;
import com.tom.doctorpatient.repository.UpdateRepo;
import com.tom.doctorpatient.service.DoctorServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class DoctorServiceImpl implements DoctorServiceInterface {

    private final DoctorRepo doctorReop;
    private final AppointmentRepo appointmentRepo;
    private final UpdateRepo updateRepo;
    private final PatientRecordRepo patientRepo;

    public DoctorServiceImpl(DoctorRepo doctorReop, AppointmentRepo appointmentRepo,
                             UpdateRepo updateRepo, PatientRecordRepo patientRepo) {
        this.doctorReop = doctorReop;
        this.appointmentRepo = appointmentRepo;
        this.updateRepo = updateRepo;
        this.patientRepo = patientRepo;
    }

    @Override
    public ModelAndView doctorLoginAuthentication(String uid, String pass, HttpSession session) {
        try {
            Doctor d = doctorReop.findByUserId(uid);
            System.out.println(d.getUserId() + " & " + d.getPassword());
            ModelAndView mv = new ModelAndView();
            if (d.getUserId().equals(uid) && d.getPassword().equals(pass)) {
                int did = d.getDid();
                mv.addObject("did", did);
                session.setAttribute("did", did);
                mv.addObject("dname", d.getFirstName());
                mv.setViewName("dhome1");
            } else if (d.getUserId().equals(uid) && !(d.getPassword().equals(pass))) {
                mv.addObject("errmsg", "Incorrect Password");
                mv.setViewName("dlogin");
            }
            return mv;

        } catch (Exception e) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("errmsg", "Invalid UserId");
            mv.setViewName("dlogin");
            return mv;
        }
    }

    @Override
    public ModelAndView doctorRegistration(Doctor doc, String userId) {
        ModelAndView mv = new ModelAndView();
        Doctor d = doctorReop.findByUserId(userId);
        if (d == null) {
            doctorReop.save(doc);
            mv.setViewName("dlogin");
            mv.addObject("errormsg", "Registered Sucessfuly");
        } else if (!(d.getUserId().equals(doc.getUserId()))) {
            doctorReop.save(doc);
            mv.setViewName("dlogin");
            mv.addObject("errormsg", "Registered Sucessfuly");
        } else {
            mv.addObject("errormsg", "User Id already selected");
            mv.setViewName("dreg1");
        }
        return mv;
    }

    @Override
    public ModelAndView appointmentDetails(HttpSession session, String doctorName) {
        // System.out.println("Hi"+session.getAttribute("did"));
        List<Appointment> appo =  appointmentRepo.findByDoctor(session.getAttribute("did").toString());
        ModelAndView mv = new ModelAndView();
        mv.addObject("dname", session);
        mv.addObject("docApp", appo);
        mv.setViewName("appointment");
        return mv;
    }

    @Override
    public ModelAndView updateRecordsDetails(HttpSession ses, String dname) {
        //System.out.println("Hi"+ses.getAttribute("did"));
        List<Updateapp> appo = updateRepo.findByDoctor(ses.getAttribute("did").toString());
        ModelAndView mv=new ModelAndView();
        mv.addObject("dname", dname);
        mv.addObject("docApp", appo);
        mv.setViewName("updaterecords");
        return mv;
    }

    @Override
    public ModelAndView outPatientDetails(HttpSession ses, String pid, String pname, String doc, String symp, String date, String dname, String prescription) {
        ModelAndView mv = new ModelAndView();
        System.out.println("final" + doc);

        int id = Integer.parseInt(pid);

        PatientRecord precord = patientRepo.findByPid(id);
        PatientRecord pnull = new PatientRecord();

        if (precord != null) {
            precord.setPrescription(prescription);
            patientRepo.save(precord);
        } else {
            pnull.setPid(id);
            pnull.setPrescription(prescription);
            patientRepo.save(pnull);
        }

        String dateTimeString = date.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime appointmentDate = LocalDateTime.parse(dateTimeString, formatter);
        Updateapp upre = updateRepo.findByPid(id);

        if (upre != null) {
            upre.setPrescription(prescription);
            upre.setDoctor(doc);
            updateRepo.save(upre);
            System.out.println(upre.getDoctor());
        } else {
            Updateapp upp = new Updateapp(id, pname, appointmentDate, symp, doc, prescription);
            updateRepo.save(upp);
        }

        Appointment dapp = appointmentRepo.findByPid(id);
        appointmentRepo.deleteById(dapp.getAppid());
        List<Appointment> appo = appointmentRepo.findByDoctor(ses.getAttribute("did").toString());

        mv.addObject("dname", dname);
        mv.addObject("docApp", appo);
        mv.setViewName("appointment");
        return mv;
    }

    @Override
    public ModelAndView updateOutpatient(HttpSession ses, String pid, String pname, String prescription, String dname) {
        //System.out.println("final" + ses.getAttribute("did"));
        int id = Integer.parseInt(pid);
        PatientRecord pr = patientRepo.findByPid(id);
        PatientRecord prn = new PatientRecord();
        Updateapp precord = updateRepo.findByPid(id);
        Updateapp pnull = new Updateapp();

        if (!(precord == null)) {
            precord.setPrescription(prescription);
            pr.setPrescription(prescription);
            patientRepo.save(pr);
            updateRepo.save(precord);
        } else {
            pnull.setPid(id);
            pnull.setPrescription(prescription);
            updateRepo.save(pnull);
            prn.setPid(id);
            prn.setPrescription(prescription);
            patientRepo.save(prn);
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "report saved.");
        mv.addObject("pname", pname);
        mv.addObject("dname", dname);
        List<Updateapp> ap = updateRepo.findByDoctor(ses.getAttribute("did").toString());
        mv.addObject("docApp", ap);
        mv.setViewName("updaterecords");
        return mv;
    }

    @Override
    public ModelAndView outPatientGetDetails(String pid, String doc, String date, String symp, String pname, String dname) {
        int id = Integer.parseInt(pid);
        ModelAndView mv = new ModelAndView("patientrecord");
        PatientRecord pr = patientRepo.findByPid(id);
        PatientRecord pr1 = new PatientRecord();

        if (pr != null) {
            mv.addObject("pid", id);
            mv.addObject("pname", pname);
            mv.addObject("dis", symp);
            mv.addObject("activity", pr.getActivity());
            mv.addObject("bloodg", pr.getBloodGlucose());
            mv.addObject("bmi", pr.getBmi());
            mv.addObject("calories", pr.getCalories());
            mv.addObject("chol", pr.getCholesterol());
            mv.addObject("palcount", pr.getPlateletCount());
            mv.addObject("thyroid", pr.getThyroid());
        } else {
            mv.addObject("pid", id);
            mv.addObject("pname", pname);
            mv.addObject("dis", symp);
            mv.addObject("activity", "NIL");
            mv.addObject("bloodg", "NIL");
            mv.addObject("bmi", "NIL");
            mv.addObject("calories", "NIL");
            mv.addObject("chol", "NIL");
            mv.addObject("palcount", "NIL");
            mv.addObject("thyroid", "NIL");
        }

        mv.addObject("dname", dname);
        mv.addObject("date", date);
        mv.addObject("doc", doc);
        return mv;
    }

    @Override
    public ModelAndView patientHistoryGetDetails(HttpServletRequest req, String dname) {
        System.out.println(req.getParameter("pid"));
        int pid = Integer.parseInt(req.getParameter("pid"));
        String pname = req.getParameter("pname");
        String dis = req.getParameter("symp");
        ModelAndView mv = new ModelAndView();
        PatientRecord pr = patientRepo.findByPid(pid);
        PatientRecord pr1 = new PatientRecord();
        Updateapp up = updateRepo.findByPid(pid);
        if (!(pr == null)) {
            mv.addObject("pid", pid);
            mv.addObject("pname", pname);
            mv.addObject("dis", dis);
            mv.addObject("activity", pr.getActivity());
            mv.addObject("bloodg", pr.getBloodGlucose());
            mv.addObject("bmi", pr.getBmi());
            mv.addObject("calories", pr.getCalories());
            mv.addObject("chol", pr.getCholesterol());
            mv.addObject("palcount", pr.getPlateletCount());
            mv.addObject("thyroid", pr.getThyroid());
            mv.addObject("pres", up.getPrescription());
        } else {
            mv.addObject("pid", pid);
            mv.addObject("pname", pname);
            mv.addObject("dis", dis);
            mv.addObject("activity", "NIL");
            mv.addObject("bloodg", "NIL");
            mv.addObject("bmi", "NIL");
            mv.addObject("calories", "NIL");
            mv.addObject("chol", "NIL");
            mv.addObject("palcount", "NIL");
            mv.addObject("thyroid", "NIL");
        }
        mv.addObject("dname", dname);
        mv.setViewName("updatepatientrecord");
        return mv;
    }


}
