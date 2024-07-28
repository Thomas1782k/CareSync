package com.tom.doctorpatient.service;

import com.tom.doctorpatient.dto.PatientDTO;
import com.tom.doctorpatient.entity.Patient;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface PatientServiceInterface {

    public ModelAndView patientLoginAuthentication(String userId, String password);
    public ModelAndView patientRegistration(Patient patient, String userId);
    public ModelAndView patientHomeButton(String pid);
    public ModelAndView preferredDoctor(String pid, String patientName, String doctor);

    public ModelAndView bmiCalculate(int pid, float height, float weight);
    public ModelAndView bloodGlucose(int pid, String gtime, String bloodGlucose);
    public ModelAndView bloodcount(int pid,String gtime, String rbcCount, String wbcCount, String plateletCount);
    public ModelAndView activityTracker(int pid, String gtime, String activity, String workOut);
    public ModelAndView cholesterolTracker(int pid, String gtime, String cholesterol);
    public ModelAndView pressureTracker(int pid, String gtime, String pressure);
    public ModelAndView thyroidTracker(int pid, String gtime, String thyroid);
    public ModelAndView caloriesTracker(int pid, String gtime, String calories, String caloryIntake);
    public ModelAndView dietTracker(int pid, String gtime, String diet, String dietTime);

    public ModelAndView seeHealthCondition(String pid);
    public ModelAndView seeYourPrescription(String pid);

    /**********forgot password***********/

    public ModelAndView forgotPass(String userId, String ContactNumber, String EmailAddress, HttpSession session, Model model);
    public ModelAndView changePass(String userId, String Password, String newPassword, HttpSession session, Model model);
    public ModelAndView forgotPass1(String userId, String ContactNumber, String EmailAddress, HttpSession session, Model model);
    public ModelAndView dchangePass(String userId, String Password, String newPassword, HttpSession session, Model model);

     /*************Paging*********/
    Page<PatientDTO> findAll(String query, int pageNo, int pageSize);

    Page<PatientDTO> searchByPatient(String query, int pageNo, int pageSize);
}
