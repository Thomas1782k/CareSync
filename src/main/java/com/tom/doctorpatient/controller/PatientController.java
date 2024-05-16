package com.tom.doctorpatient.controller;


import com.tom.doctorpatient.entity.*;
//import jakarta.servlet.http.HttpServletRequest;
import com.tom.doctorpatient.serviceImpl.PatientServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import com.tom.doctorpatient.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PatientController {

	private final PatientServiceImpl patientService;

	public PatientController(PatientServiceImpl patientService) {
		this.patientService = patientService;
	}

	@RequestMapping("/patientLoginAuthentication")
	public ModelAndView patientLoginAuthentication(String userId, String password) {
		return patientService.patientLoginAuthentication(userId, password);
	}

	@RequestMapping("/patientRegistration")
	public ModelAndView patientRegistration(Patient patient, String userId) {
			return patientService.patientRegistration(patient, userId);
	}

	@RequestMapping("/phome")
	public ModelAndView patientHomeButton(String pid){
		return patientService.patientHomeButton(pid);
	}

	@RequestMapping("/patienthome")
	public ModelAndView patienthome()	{
		ModelAndView mv=new ModelAndView("patienthome");
		return mv;
	}
	
	@RequestMapping("/plogin")
	public ModelAndView plogin()	{
		ModelAndView mv=new ModelAndView("patientlogin");
		return mv;
	}

	@RequestMapping("/seeHealthCondition")
	public ModelAndView seeHealthCondition(String pid) {
			return patientService.seeHealthCondition(pid);
	}

	@RequestMapping("preferredDoctor")
	public ModelAndView preferredDoctor(String pid, String pname, String doctor) {
			return patientService.preferredDoctor(pid, pname, doctor);
	}

	@RequestMapping("/seeYourPrescription")
	public ModelAndView seeYourPrescription(String pid) {
		return patientService.seeYourPrescription(pid);
	}

	@RequestMapping("/bmiCalculate")
	public ModelAndView bmiCalculate(int pid, float height, float weight) {
		return patientService.bmiCalculate(pid, height, weight);
	}

	@RequestMapping("/active")
	public ModelAndView activityTracker(int pid, @RequestParam("dateOfActivity") String gtime, String activity, @RequestParam("timeOfWorkOut") String workOut) {
		return patientService.activityTracker(pid, gtime, activity, workOut);
	}

	@RequestMapping("/bgLevel")
	public ModelAndView BloodGlucose(int pid, @RequestParam("timeOfGlucose") String gtime, String bloodGlucose) {
		return patientService.bloodGlucose(pid, gtime, bloodGlucose);
	}

	@RequestMapping("/blood")
	public ModelAndView bloodcount(int pid, @RequestParam("timeOfBlood") String gtime, String rbcCount, String wbcCount, String plateletCount) {
		return patientService.bloodcount(pid, gtime, rbcCount, wbcCount, plateletCount);
	}

	@RequestMapping("/cholesterolMonitor")
	public ModelAndView cholesterolTracker(int pid, @RequestParam("timeOfCholesterol") String gtime, String cholesterol) {
		return patientService.cholesterolTracker(pid, gtime, cholesterol);
	}

	@RequestMapping("/pressureMonitor")
	public ModelAndView pressureTracker(int pid, @RequestParam("timeOfPressure") String gtime, String pressure) {
		return patientService.pressureTracker(pid, gtime, pressure);
	}

	@RequestMapping("/thyroidMonitor")
	public ModelAndView thyroidTracker(int pid, @RequestParam("timeOfThyroid") String gtime, String thyroid) {
		return patientService.thyroidTracker(pid, gtime, thyroid);
	}

	@RequestMapping("/caloryMonitor")
	public ModelAndView caloriesTracker(int pid, @RequestParam("dateOfCalories") String gtime, String calories, @RequestParam("timeOfCaloriesIntake") String caloryIntake) {
		return patientService.caloriesTracker(pid, gtime, calories, caloryIntake);
	}

	@RequestMapping("/dietMonitor")
	public ModelAndView dietTracker(int pid, @RequestParam("dateOfDiet") String gtime,
									String diet, @RequestParam("timeOfDietIntake") String dietTime) {
		return patientService.dietTracker(pid, gtime, diet, dietTime);
	}

	@RequestMapping("/bmi")
	public ModelAndView bmi(int pid) {
		System.out.println(pid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("bmi");
		return mv;
	}

	@RequestMapping("/glucose")
	public ModelAndView glucose(int pid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("glucose");
		return mv;
	}

	@RequestMapping("/bcount")
	public ModelAndView bcount(int pid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("bloodcount");
		mv.addObject("pid", pid);
		return mv;
	}

	@RequestMapping("/diabetes")
	public ModelAndView diabetes(int pid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("diabetes");
		return mv;
	}

	@RequestMapping("/activity")
	public ModelAndView activity(int pid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("activity");
		return mv;
	}

	@RequestMapping("/fat")
	public ModelAndView fat(int pid)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("cholesterol");
		return mv;
	}

	@RequestMapping("/pressure")
	public ModelAndView pressure(int pid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("pressure");
		return mv;
	}

	@RequestMapping("/thyroid")
	public ModelAndView thyroid(int pid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("thyroid");
		return mv;
	}

	@RequestMapping("/calories")
	public ModelAndView calories(int pid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pid", pid);
		mv.setViewName("calories");
		return mv;
	}

	@RequestMapping("/diettracker")
	public ModelAndView diettracker(int pid) {
		ModelAndView mv = new ModelAndView("");
		mv.addObject("pid", pid);
		mv.setViewName("diet");
		return mv;
	}



	/**********forgot***********/

	@RequestMapping("/forgotPass")
	public ModelAndView forgotPass(@RequestParam String userId, String ContactNumber,
								   String EmailAddress, HttpSession session, Model model) {
		return patientService.forgotPass(userId, ContactNumber, EmailAddress, session, model);
	}

	@RequestMapping("/changePass")
	public ModelAndView changePass(@RequestParam String userId, String Password,
								   String newPassword, HttpSession session, Model model) {
		return patientService.changePass(userId, Password, newPassword, session, model);
	}

	@RequestMapping("/dforgotPass")
	public ModelAndView forgotPass1(@RequestParam String userId, String ContactNumber, String EmailAddress,
									HttpSession session, Model model) {
		return patientService.forgotPass(userId, ContactNumber, EmailAddress, session, model);
	}

	@RequestMapping("/dchangePass")
	public ModelAndView dchangePass(@RequestParam String userId, String Password, String newPassword,
									HttpSession session, Model model) {
		return patientService.changePass(userId, Password, newPassword, session, model);
	}

	@RequestMapping("/forgetpassword1")
	public ModelAndView forgetPass() {
		ModelAndView mv = new ModelAndView("forgetpassword");
		return mv;
	}

	@RequestMapping("/forgotpassword2")
	public ModelAndView changePass(HttpSession session, Model model) {
		ModelAndView mv = new ModelAndView("dforgotpassword");
		return mv;
	}
		
}
