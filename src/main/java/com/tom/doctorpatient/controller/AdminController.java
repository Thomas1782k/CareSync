package com.tom.doctorpatient.controller;

import com.tom.doctorpatient.entity.Admin;
import com.tom.doctorpatient.entity.Appointment;
import com.tom.doctorpatient.entity.Doctor;
import com.tom.doctorpatient.entity.Patient;
import com.tom.doctorpatient.repository.*;
import com.tom.doctorpatient.serviceImpl.AdminServiceImpl;
import com.tom.doctorpatient.serviceImpl.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AdminController {

	private FrontService fs;
	private AdminServiceImpl adminService;

	public AdminController( FrontService fs, AdminServiceImpl adminService) {
		this.fs = fs;
		this.adminService = adminService;
	}
	
	@RequestMapping("/")
	public String docHome()
	{
		return "index1";
	}
	
	@RequestMapping("/ind")
	public String ind()
	{
		return "ind";
	}
	
	@RequestMapping("/selection")
	public ModelAndView select(@RequestParam("flexRadioDefault") String role, @RequestParam("opt") String option) {
			return fs.select(role, option);
	}

	@RequestMapping("/about")
	public ModelAndView about() {
		ModelAndView mv = new ModelAndView("about");
		return mv;
	}

	@RequestMapping("/contact")
	public ModelAndView contact() {
		ModelAndView mv = new ModelAndView("contact");
		return mv;
	}

	@RequestMapping("/didreg")
	public ModelAndView didreg() {
		ModelAndView mv = new ModelAndView("dreg1");
		return mv;
	}

	@RequestMapping("/dipreg")
	public ModelAndView dipreg() {
		ModelAndView mv = new ModelAndView("patientreg");
		return mv;
	}


	/****************Admin**************/

	@RequestMapping("/adminLoginAuthentication")
	public ModelAndView adminLoginAuthentication(@RequestParam("userId") String userId, @RequestParam("password") String password) {
			return adminService.adminLoginAuthentication(userId, password);
	}

	@RequestMapping("/admhome")
	public ModelAndView admhome() {
		ModelAndView mv = new ModelAndView("adminhome");
		return mv;
	}

	@RequestMapping("/appo")
	public ModelAndView appo(int pid, String pname, @RequestParam("appointmentDate") String dtime,
							 String symptoms, String doctor) {
			return adminService.appointmentFixing(pid, pname, dtime, symptoms, doctor);
	}


}
