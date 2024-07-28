package com.tom.doctorpatient.serviceImpl;

import com.tom.doctorpatient.dto.DoctorDTO;
import com.tom.doctorpatient.dto.PatientDTO;
import com.tom.doctorpatient.entity.*;
import com.tom.doctorpatient.repository.AppointmentRepo;
import com.tom.doctorpatient.repository.DoctorRepo;
import com.tom.doctorpatient.repository.PatientRecordRepo;
import com.tom.doctorpatient.repository.UpdateRepo;
import com.tom.doctorpatient.service.DoctorServiceInterface;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DoctorServiceImpl implements DoctorServiceInterface {

    private final DoctorRepo doctorReop;
    private final AppointmentRepo appointmentRepo;
    private final UpdateRepo updateRepo;
    private final PatientRecordRepo patientRepo;
    private final DoctorRepo doctorRepo;

    public DoctorServiceImpl(DoctorRepo doctorReop, AppointmentRepo appointmentRepo,
                             UpdateRepo updateRepo, PatientRecordRepo patientRepo, DoctorRepo doctorRepo) {
        this.doctorReop = doctorReop;
        this.appointmentRepo = appointmentRepo;
        this.updateRepo = updateRepo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
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

    @CachePut(key = "#doc.userId", value = "doctors")
    public Doctor registerDoctor(Doctor doc) {
        Doctor existingDoctor = doctorRepo.findByUserId(doc.getUserId());
        if (existingDoctor == null || !existingDoctor.getUserId().equals(doc.getUserId())) {
            doctorRepo.save(doc);
            return doc;
        } else {
            return existingDoctor;
        }
    }

    @Override
    public ModelAndView doctorRegistration(Doctor doc, String userId) {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @Override
    @Cacheable(value="appointments")
    public List<Appointment> appointmentDetails(HttpSession session, String doctorName) {
        // System.out.println("Hi"+session.getAttribute("did"));
        List<Appointment> appo =  appointmentRepo.findByDoctor(session.getAttribute("did").toString());
        return appo;
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

    public DoctorDTO createDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDid(doctor.getDid());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setEmailAddress(doctor.getEmailAddress());
        dto.setAge(doctor.getAge());
        dto.setGender(doctor.getGender());
        dto.setCity(doctor.getCity());
        dto.setContactNumber(doctor.getContactNumber());
        dto.setState(doctor.getState());
//        PatientRecord patientRecord = patientRecordRepo.findByPid(patient.getPid());
//        dto.setPatientRecord(patientRecord);
        return dto;
    }

    @Override
    public Page<DoctorDTO> findAll(String query, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Doctor> doctorPage = doctorRepo.findAll(pageable);

        List<DoctorDTO> dtos = doctorPage.stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
        System.out.println(dtos.size());
        return new PageImpl<>(dtos, pageable, doctorPage.getTotalElements());
    }


    @Override
    public Page<DoctorDTO> searchByDoctor(String query, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Doctor> doctorPage = doctorRepo.searchByDoctor(query,pageable);

        List<DoctorDTO> dtos = doctorPage.stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
        System.out.println(dtos.size());
        return new PageImpl<>(dtos, pageable, doctorPage.getTotalElements());
    }


}
