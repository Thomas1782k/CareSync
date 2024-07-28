package com.tom.doctorpatient.controller;

import com.tom.doctorpatient.entity.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tom.doctorpatient.serviceImpl.DoctorServiceImpl;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import com.tom.doctorpatient.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class DoctorController {
	/*************Docter************/

	private final DoctorServiceImpl doctorService;

	public DoctorController(DoctorServiceImpl doctorService){
		this.doctorService=doctorService;
	}

	@RequestMapping("/doctorLoginAuthentication")
	public ModelAndView doctorLoginAuthentication(@RequestParam("uid") String uid, @RequestParam("pass") String pass, HttpSession session) {
		return doctorService.doctorLoginAuthentication(uid, pass, session);
	}

	@RequestMapping("/doctorRegistration")
	public ModelAndView doctorRegistration(Doctor doc, String userId) {
		//return doctorService.doctorRegistration(doc, userId);
		Doctor registeredDoctor = doctorService.registerDoctor(doc);
		ModelAndView mv = new ModelAndView();

		if (registeredDoctor != null && registeredDoctor.equals(doc)) {
			mv.setViewName("dlogin");
			mv.addObject("errormsg", "Registered Successfully");
		} else {
			mv.setViewName("dreg1");
			mv.addObject("errormsg", "User Id already selected");
		}

		return mv;
	}

	@RequestMapping("/dhome")
	public ModelAndView dhome(String dname) {
		ModelAndView mv = new ModelAndView("dhome1");
		mv.addObject("dname", dname);
		return mv;
	}

	@RequestMapping("/appointment")
	public ModelAndView appointmentDetails(HttpSession ses, String doctorName) {
		List<Appointment> appo= doctorService.appointmentDetails(ses, doctorName);
		ModelAndView mv = new ModelAndView();
		mv.addObject("dname", ses);
		mv.addObject("docApp", appo);
		mv.setViewName("appointment");
		return mv;
	}

	@RequestMapping("/updaterec")
	public ModelAndView updateRecordsDetails(HttpSession ses, String doctorName) {
		return doctorService.updateRecordsDetails(ses, doctorName);
	}

	@RequestMapping("/outPatientDetails")
	public ModelAndView outPatientDetails(HttpSession ses, String pid, String pname, String doc, String symp,
										  String date, String dname, String prescription) {
		return doctorService.outPatientDetails(ses, pid, pname, doc, symp, date, dname, prescription);
	}

	@RequestMapping("/updateOutpatient")
	public ModelAndView updateOutpatient(HttpSession ses, String pid, String pname, String prescription, String dname) {
		return doctorService.updateOutpatient(ses, pid, pname, prescription, dname);
	}

	@RequestMapping("/outPatientGetDetails")
	public ModelAndView outPatientGetDetails(String pid, String doc, String date, String symp, String pname, String dname) {
		return doctorService.outPatientGetDetails(pid, doc, date, symp, pname, dname);
	}

	@RequestMapping("/patientHistoryGetDetails")
	public ModelAndView patientHistoryGetDetails(HttpServletRequest req, String doctorName) {
		return doctorService.patientHistoryGetDetails(req, doctorName);
	}

}
