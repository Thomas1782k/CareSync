package com.tom.doctorpatient.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface AdminServiceInterface {

    public ModelAndView adminLoginAuthentication( String uid, String pass);
    public ModelAndView appointmentFixing(int pid, String pname, String dateAndTime,
                             String symptoms, String doctor);
}
