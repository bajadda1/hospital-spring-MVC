package com.example.hospital_mvc.web;

import com.example.hospital_mvc.entities.Patient;
import com.example.hospital_mvc.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/user/index")
    public String getPatients(Model model) {
        List<Patient> patients = this.patientRepository.findAll();
        model.addAttribute("patientsList", patients);
        return "patients";
    }

    @GetMapping(path = "/")
    public String redirectToHome() {
        return "redirect:/user/index-page";
    }

    @GetMapping(path = "/user/index-page")
    public String getPatientsPage(Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "2") int size,
                                  @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Patient> patientsPageable = this.patientRepository.findByNameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("patientsListPageable", patientsPageable.getContent());
        model.addAttribute("pages", new int[patientsPageable.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("searchKeyword", keyword);
        return "patients-page";
    }

    @GetMapping(path = "/admin/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePatient(@RequestParam(name = "id") Long id, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "keyword") String keyword) {
        this.patientRepository.deleteById(id);

        return "redirect:/user/index-page?page=" + page + "&size=" + size + "&keyword=" + keyword;
    }

    @GetMapping(path = "/admin/add-patient")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientForm";
    }

    @PostMapping(path = "/admin/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String save(Model model, @Valid @ModelAttribute Patient patient,
                       BindingResult bindingResult,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "2") int size,
                       @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("searchKeyword", keyword);
        if (bindingResult.hasErrors()) {
            return "patientForm";
        }

        this.patientRepository.save(patient);
        return "redirect:/user/index-page?page=" + page + "&size=" + size + "&keyword=" + keyword;
    }

    @GetMapping(path = "/admin/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editPatient(Model model, @RequestParam(name = "id") Long id,
                              @RequestParam(name = "page",defaultValue = "0") int page,
                              @RequestParam(name = "size",defaultValue = "2") int size,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Patient patient = this.patientRepository.findById(id).orElse(null);
        if (patient == null) {
            throw new RuntimeException("patient not found");
        }
        model.addAttribute("patient", patient);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("searchKeyword", keyword);
        return "editForm";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }
    @GetMapping(path = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping("/user/profile")
    @ResponseBody
    public Authentication authentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
