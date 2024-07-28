package com.tom.doctorpatient.serviceImpl;

import com.tom.doctorpatient.dto.PatientDTO;
import com.tom.doctorpatient.entity.Doctor;
import com.tom.doctorpatient.entity.Patient;
import com.tom.doctorpatient.entity.PatientRecord;
import com.tom.doctorpatient.entity.Updateapp;
import com.tom.doctorpatient.repository.*;
import com.tom.doctorpatient.service.PatientServiceInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientServiceInterface {

    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRecordRepo patientRecordRepo;
    private final UpdateRepo updateRepo;
    //private final PatientDTORepo patientDTORepo;

    public PatientServiceImpl(PatientRepo patientRepo, DoctorRepo doctorRepo,
                              PatientRecordRepo patientRecordRepo, UpdateRepo updateRepo) {
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.patientRecordRepo = patientRecordRepo;
        this.updateRepo = updateRepo;
        //this.patientDTORepo = patientDTORepo;
    }

    @Override
    public ModelAndView patientLoginAuthentication(String userId, String password) {
        try {
            Patient p = patientRepo.findByUserId(userId);
            System.out.println(p);
            ModelAndView mv = new ModelAndView();

            if (p.getUserId().equals(userId) && p.getPassword().equals(password)) {
                List<Doctor> doc = doctorRepo.findAll();
                mv.addObject("pname", p.getFirstName());
                mv.addObject("pid", p.getPid());
                mv.addObject("doctors", doc);
                mv.setViewName("patienthome");
            } else if (p.getUserId().equals(userId) && !(p.getPassword().equals(password))) {
                mv.addObject("errmsg", "Incorrect Password");
                mv.setViewName("patientlogin");
            }

            return mv;
        } catch(Exception e) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("errmsg", "Invalid Patient Id");
            mv.setViewName("patientlogin");
            return mv;
        }
    }

    @Override
    public ModelAndView patientRegistration(Patient patient, String userId) {
        ModelAndView mv = new ModelAndView();
        Patient d = patientRepo.findByUserId(userId);

        if (d == null || !d.getUserId().equals(patient.getUserId())) {
            patientRepo.save(patient);
            mv.setViewName("patientlogin");
            mv.addObject("errmsg", "Registered Successfully");
        } else {
            mv.addObject("errmsg", "User ID already selected");
            mv.setViewName("patientreg");
        }

        return mv;
    }

    @Override
    public ModelAndView patientHomeButton(String pid) {
        ModelAndView mv=new ModelAndView("patienthome");
        int id=Integer.parseInt(pid);
        Patient p=patientRepo.findByPid(id);
        List<Doctor> doc=doctorRepo.findAll();
        mv.addObject("doctors", doc);
        mv.addObject("pname", p.getFirstName());
        mv.addObject("pid", p.getPid());
        return mv;
    }

    @Override
    public ModelAndView preferredDoctor(String pid, String patientName, String doctor) {
        ModelAndView mv = new ModelAndView();
        int id = Integer.parseInt(pid);
        List<Doctor> doc = doctorRepo.findAll();
        Patient p = patientRepo.findByPid(id);
        if (p != null) {
            p.setDoctor(doctor);
            patientRepo.save(p);
            mv.addObject("pid", id);
            mv.addObject("pname", patientName);
            mv.addObject("doctors", doc);
            mv.setViewName("patienthome");
        } else {
            mv.addObject("pid", id);
            mv.addObject("pname", patientName);
            mv.setViewName("patienthome");
        }
        return mv;
    }

    @Override
    public ModelAndView bmiCalculate(int pid, float height, float weight) {
        ModelAndView mv = new ModelAndView();
        float BMI = (((weight) / (height * height)) * 10000);
        String value = String.valueOf(BMI);

        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();

        patientRecordRepo.save(pr);
        if (!(p == null)) {
            p.setBmi(value);
            patientRecordRepo.save(p);
        } else {
            pr.setPid(pid);
            pr.setBmi(value);
            patientRecordRepo.save(pr);
        }
        mv.addObject("pid", pid);
        mv.addObject("msg", "Data are Added Successfully");
        mv.setViewName("bmi");
        return mv;
    }

    @Override
    public ModelAndView bloodGlucose(int pid, String gtime, String bloodGlucose) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);
        ModelAndView mv = new ModelAndView();

        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();

        if (p != null) {
            p.setPid(pid);
            p.setBloodGlucose(bloodGlucose);
            p.setTimeOfGlucose(glutime);
            patientRecordRepo.save(p);
        } else {
            pr.setPid(pid);
            pr.setBloodGlucose(bloodGlucose);
            pr.setTimeOfGlucose(glutime);
            patientRecordRepo.save(pr);
        }
        mv.addObject("pid", pid);
        mv.addObject("msg", "Data are Added Successfully");
        mv.setViewName("glucose");
        return mv;
    }

    @Override
    public ModelAndView bloodcount(int pid, String gtime, String rbcCount, String wbcCount, String plateletCount) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);

        ModelAndView mv = new ModelAndView();
        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();

        if (p != null) {
            p.setTimeOfBlood(glutime);
            p.setRbcCount(rbcCount);
            p.setWbcCount(wbcCount);
            p.setPlateletCount(plateletCount);
            patientRecordRepo.save(p);
        } else {
            pr.setTimeOfBlood(glutime);
            pr.setRbcCount(rbcCount);
            pr.setWbcCount(wbcCount);
            pr.setPlateletCount(plateletCount);
            patientRecordRepo.save(pr);
        }

        mv.addObject("msg", "Data are Added Successfully");
        mv.addObject("pid", pid);
        mv.setViewName("bloodcount");
        return mv;
    }

    @Override
    public ModelAndView activityTracker(int pid, String gtime, String activity, String workOut) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);

        String valueFromHtml1 = workOut;
        String dateTimeString1 = valueFromHtml1.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime1 = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime1);

        ModelAndView mv = new ModelAndView();
        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();

        if (p != null) {
            p.setDateOfActivity(glutime);
            p.setActivity(activity);
            p.setTimeOfWorkOut(glutime1);
            patientRecordRepo.save(p);
        } else {
            pr.setDateOfActivity(glutime);
            pr.setActivity(activity);
            pr.setTimeOfWorkOut(glutime1);
            patientRecordRepo.save(pr);
        }
        mv.addObject("msg", "Data are Added Successfully");
        mv.addObject("pid", pid);
        mv.setViewName("activity");
        return mv;
    }

    @Override
    public ModelAndView cholesterolTracker(int pid, String gtime, String cholesterol) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);

        ModelAndView mv = new ModelAndView();
        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();
        if (p != null) {
            p.setTimeOfCholesterol(glutime);
            p.setCholesterol(cholesterol);
            patientRecordRepo.save(p);
        } else {
            pr.setTimeOfCholesterol(glutime);
            pr.setCholesterol(cholesterol);
            patientRecordRepo.save(pr);
        }
        mv.addObject("msg", "Data are Added Successfully");
        mv.addObject("pid", pid);
        mv.setViewName("cholesterol");
        return mv;
    }

    @Override
    public ModelAndView pressureTracker(int pid, String gtime, String pressure) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);
        ModelAndView mv = new ModelAndView();
        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();
        if (p != null) {
            p.setTimeOfPressure(glutime);
            p.setPressure(pressure);
            patientRecordRepo.save(p);
        } else {
            pr.setTimeOfPressure(glutime);
            pr.setPressure(pressure);
            patientRecordRepo.save(pr);
        }
        mv.addObject("msg", "Data are Added Successfully");
        mv.addObject("pid", pid);
        mv.setViewName("pressure");
        return mv;
    }

    @Override
    public ModelAndView thyroidTracker(int pid, String gtime, String thyroid) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);
        ModelAndView mv = new ModelAndView();
        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();
        if (p != null) {
            p.setTimeOfThyroid(glutime);
            p.setThyroid(thyroid);
            patientRecordRepo.save(p);
        } else {
            pr.setTimeOfThyroid(glutime);
            pr.setThyroid(thyroid);
            patientRecordRepo.save(pr);
        }
        mv.addObject("msg", "Data are Added Successfully");
        mv.addObject("pid", pid);
        mv.setViewName("thyroid");
        return mv;
    }

    @Override
    public ModelAndView caloriesTracker(int pid, String gtime, String calories, String caloryIntake) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);

        String valueFromHtml1 = caloryIntake;
        String dateTimeString1 = valueFromHtml1.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime1 = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime1);
        ModelAndView mv = new ModelAndView();
        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();
        if (p != null) {
            p.setDateOfCalories(glutime);
            p.setCalories(calories);
            p.setTimeOfCaloriesIntake(glutime1);
            patientRecordRepo.save(p);
        } else {
            pr.setDateOfCalories(glutime);
            pr.setCalories(calories);
            pr.setTimeOfCaloriesIntake(glutime1);
            patientRecordRepo.save(pr);
        }
        mv.addObject("msg", "Data are Added Successfully");
        mv.addObject("pid", pid);
        mv.setViewName("calories");
        return mv;
    }

    @Override
    public ModelAndView dietTracker(int pid, String gtime, String diet, String dietTime) {
        String valueFromHtml = gtime;
        String dateTimeString = valueFromHtml.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime);

        String valueFromHtml1 = dietTime;
        String dateTimeString1 = valueFromHtml1.replace("T", " ");
        System.out.println();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime glutime1 = LocalDateTime.parse(dateTimeString, formatter);
        System.out.println(glutime1);
        ModelAndView mv = new ModelAndView();
        PatientRecord p = patientRecordRepo.findByPid(pid);
        PatientRecord pr = new PatientRecord();
        if (!(p == null)) {
            p.setDateOfDiet(glutime);
            p.setDiet(diet);
            p.setTimeOfDietIntake(glutime1);
            patientRecordRepo.save(p);
        } else {
            pr.setDateOfDiet(glutime);
            pr.setDiet(diet);
            pr.setTimeOfDietIntake(glutime1);
            patientRecordRepo.save(pr);
        }
        mv.addObject("msg", "Data are Added Successfully");
        mv.addObject("pid", pid);
        mv.setViewName("diet");
        return mv;
    }

    @Override
    public ModelAndView seeHealthCondition(String pid) {
        ModelAndView mv = new ModelAndView();
        int id = Integer.parseInt(pid);
        PatientRecord vh = patientRecordRepo.findByPid(id);
        if (vh != null) {
            mv.addObject("act", vh.getActivity());
            mv.addObject("blglu", vh.getBloodGlucose());
            mv.addObject("bmi", vh.getBmi());
            mv.addObject("cal", vh.getCalories());
            mv.addObject("chol", vh.getCholesterol());
            mv.addObject("diet", vh.getDiet());
            mv.addObject("plc", vh.getPlateletCount());
            mv.addObject("rbc", vh.getRbcCount());
            mv.addObject("wbc", vh.getWbcCount());
            mv.addObject("pre", vh.getPressure());
            mv.addObject("thy", vh.getThyroid());
        } else {
            mv.addObject("pri", "no record");
        }
        mv.addObject("pid", id);
        mv.setViewName("hrecord");
        return mv;
    }

    @Override
    public ModelAndView seeYourPrescription(String pid) {
        ModelAndView mv = new ModelAndView();
        int id = Integer.parseInt(pid);
        Updateapp pres = updateRepo.findByPid(id);

        if (pres != null) {
            int did = Integer.parseInt(pres.getDoctor());
            Doctor dn = doctorRepo.findByDid(did);
            mv.addObject("pid", pres.getPid());
            mv.addObject("pname", pres.getPname());
            mv.addObject("symp", pres.getSymptoms());
            mv.addObject("date", pres.getAppointmentDate());
            mv.addObject("pres", pres.getPrescription());
            mv.addObject("doc", dn.getFirstName());
            mv.addObject("id", id);
        } else {
            mv.addObject("msg", "Fix the appointment!");
            mv.addObject("id", id);
        }
        mv.setViewName("prescription");
        return mv;
    }

                /*---------------------Forgot Password-------------------*/

    @Override
    public ModelAndView forgotPass(String userId, String ContactNumber, String EmailAddress, HttpSession session, Model model) {
        try {
            ModelAndView mv = new ModelAndView("revoverpassword");
            Patient patient = patientRepo.findByUserId(userId);
            if (patient.getContactNumber().equals(ContactNumber) && patient.getEmailAddress().equals(EmailAddress)) {
                mv = new ModelAndView("revoverpassword");
                return mv;
            } else {
                mv = new ModelAndView("forgetpassword");
                session.setAttribute("errormsg", "Invalid Details!");
            }
            return mv;
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("forgetpassword");
            session.setAttribute("errormsg", "Invalid User Id!");
            return mv;
        }
    }

    @Override
    public ModelAndView changePass(String userId, String Password, String newPassword, HttpSession session, Model model) {
        try {
            ModelAndView mv = new ModelAndView("patientlogin");
            Patient patient = patientRepo.findByUserId(userId);
            if (Password.equals(newPassword)) {
                patient.setPassword(newPassword);
                patientRepo.save(patient);
                mv = new ModelAndView("patientlogin");
                mv.addObject("errmsg", "Password Changed");
                return mv;
            } else {
                mv = new ModelAndView("revoverpassword");
                session.setAttribute("errmsg", "Both password should Same!");
            }
            return mv;
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("revoverpassword");
            session.setAttribute("errmsg", "Invalid User ID!");
            return mv;
        }
    }

    @Override
    public ModelAndView forgotPass1(String userId, String ContactNumber, String EmailAddress, HttpSession session, Model model) {
        try {
            ModelAndView mv = new ModelAndView("drevoverpassword");
            Doctor doctor = doctorRepo.findByUserId(userId);
            if (doctor.getContactNumber().equals(ContactNumber) && doctor.getEmailAddress().equals(EmailAddress)) {
                mv = new ModelAndView("drevoverpassword");
                return mv;
            } else {
                mv = new ModelAndView("dforgotpassword");
                session.setAttribute("errormsg", "Invalid Details!");
            }
            return mv;
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("dforgotpassword");
            session.setAttribute("errormsg", "Invalid User Id!");
            return mv;
        }
    }

    @Override
    public ModelAndView dchangePass(String userId, String Password, String newPassword, HttpSession session, Model model) {
        try {
            ModelAndView mv = new ModelAndView("dlogin");
            Doctor doctor = doctorRepo.findByUserId(userId);
            if (Password.equals(newPassword)) {
                doctor.setPassword(newPassword);
                doctorRepo.save(doctor);
                mv = new ModelAndView("dlogin");
                mv.addObject("errmsg", "Password Changed");
                return mv;
            } else {
                mv = new ModelAndView("drevoverpassword");
                session.setAttribute("errmsg", "Both password should Same!");
            }
            return mv;
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("drevoverpassword");
            session.setAttribute("errmsg", "Invalid User ID!");
            return mv;
        }
    }

    public PatientDTO createDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setPid(patient.getPid());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setEmailAddress(patient.getEmailAddress());
        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setCity(patient.getCity());
        dto.setContactNumber(patient.getContactNumber());
        dto.setState(patient.getState());
        PatientRecord patientRecord = patientRecordRepo.findByPid(patient.getPid());
        dto.setPatientRecord(patientRecord);
        return dto;
    }

    @Override
    public Page<PatientDTO> findAll(String query, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Patient> patientPage = patientRepo.findAll(pageable);

        List<PatientDTO> dtos = patientPage.stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
        System.out.println(dtos.size());
        return new PageImpl<>(dtos, pageable, patientPage.getTotalElements());
    }


    @Override
    public Page<PatientDTO> searchByPatient(String query, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Patient> patientPage = patientRepo.searchByPatient(query,pageable);

        List<PatientDTO> dtos = patientPage.stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
        System.out.println(dtos.size());
        return new PageImpl<>(dtos, pageable, patientPage.getTotalElements());
    }
}
