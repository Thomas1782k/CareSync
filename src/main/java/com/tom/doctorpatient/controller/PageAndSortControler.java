package com.tom.doctorpatient.controller;

import com.tom.doctorpatient.dto.DoctorDTO;
import com.tom.doctorpatient.dto.PatientDTO;
import com.tom.doctorpatient.serviceImpl.AdminServiceImpl;
import com.tom.doctorpatient.serviceImpl.DoctorServiceImpl;
import com.tom.doctorpatient.serviceImpl.PatientServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/backend/page")
public class PageAndSortControler {

    private final AdminServiceImpl adminService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;

    public PageAndSortControler(AdminServiceImpl adminService, PatientServiceImpl patientService,
                                DoctorServiceImpl doctorService) {
        this.adminService = adminService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Cacheable(value = "patients")
    public Page<PatientDTO> getCachedPatients(String query, int pageSize, int pageNo) {
        return patientService.findAll(query, pageNo, pageSize);
    }

    @GetMapping("/patients/all")
    //@Cacheable(value = "patients")
    public ResponseEntity<Page<PatientDTO>> getPatients( @RequestParam(value = "q", required = false) String query,
                                                         @RequestParam(value= "pageSize", defaultValue = "3") int pageSize,
                                                         @RequestParam(value = "pageNo") int pageNo) {
        //return ResponseEntity.ok(patientService.findAll(query,pageNo,pageSize));
        Page<PatientDTO> patientsPage = getCachedPatients(query, pageSize, pageNo);
        return ResponseEntity.ok(patientsPage);
    }

    @GetMapping("/patients")
    public ResponseEntity<Page<PatientDTO>> getPatientsByName( @RequestParam(value = "q") String query,
                                                         @RequestParam(value= "pageSize", defaultValue = "3") int pageSize,
                                                         @RequestParam(value = "pageNo") int pageNo) {
        return ResponseEntity.ok(patientService.searchByPatient(query,pageNo,pageSize));
    }

    @GetMapping("/doctors/all")
    public ResponseEntity<Page<DoctorDTO>> getDoctors(@RequestParam(value = "q", required = false) String query,
                                                      @RequestParam(value= "pageSize", defaultValue = "3") int pageSize,
                                                      @RequestParam(value = "pageNo") int pageNo) {
        return ResponseEntity.ok(doctorService.findAll(query,pageNo,pageSize));
    }

    @GetMapping("/doctors")
    public ResponseEntity<Page<DoctorDTO>> getDoctorByName( @RequestParam(value = "q") String query,
                                                               @RequestParam(value= "pageSize", defaultValue = "3") int pageSize,
                                                               @RequestParam(value = "pageNo") int pageNo) {
        return ResponseEntity.ok(doctorService.searchByDoctor(query,pageNo,pageSize));
    }

}
