// InstructorController.java (Updated for MVC integration)
package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.*;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/list")
    public String listInstructors(Model model) {
        List<Instructor> instructors = instructorService.getAllInstructors();
        model.addAttribute("instructors", instructors);
        return "instructors/list";
    }

    @GetMapping("/form")
    public String showInstructorForm(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructors/form";
    }

    @PostMapping("/save")
    public String saveInstructor(@ModelAttribute Instructor instructor) {
        // Convert Instructor to AddInstructorRequest
        AddInstructorRequest addInstructorRequest = new AddInstructorRequest();
        addInstructorRequest.setName(instructor.getName());
        addInstructorRequest.setEmail(instructor.getEmail());
        addInstructorRequest.setSpecialization(instructor.getSpecialization());

        // Call the service
        instructorService.addInstructor(addInstructorRequest);

        return "redirect:/instructors/list";
    }

    @GetMapping("/edit/{id}")
    public String editInstructor(@PathVariable int id, Model model) {
        Instructor instructor = instructorService.getInstructorById(id);
        model.addAttribute("instructor", instructor);
        return "instructors/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteInstructor(@PathVariable int id) {
        instructorService.deleteInstructor(id);
        return "redirect:/instructors/list";
    }
}