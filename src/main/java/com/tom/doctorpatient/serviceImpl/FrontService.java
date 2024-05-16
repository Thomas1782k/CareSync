package com.tom.doctorpatient.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Service
public class FrontService {

    public ModelAndView select( String role,  String opt){
        ModelAndView mv = new ModelAndView();
        switch(role) {
            case "docter":
                if(opt.equals("Login")) {
                    mv.setViewName("dlogin");
                } else {
                    mv.setViewName("dreg1");
                }
                break;
            case "patient":
                if(opt.equals("Login")) {
                    mv.setViewName("patientlogin");
                } else {
                    mv.setViewName("patientreg");
                }
                break;
            default:
                mv.setViewName("adminlogin");
                break;
        }
        return mv;
    }


}
