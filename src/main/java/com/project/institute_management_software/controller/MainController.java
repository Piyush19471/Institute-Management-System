package com.project.institute_management_software.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.institute_management_software.repository.CourseRepository;
import com.project.institute_management_software.repository.InstructorRepository;
import com.project.institute_management_software.repository.StudentRepository;

@Controller
public class MainController {
    
@Autowired
public StudentRepository studentRepository;
@Autowired
public InstructorRepository instructorRepository;
@Autowired
public CourseRepository courseRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalStudents", studentRepository.count());
        model.addAttribute("totalInstructors", instructorRepository.count());
        model.addAttribute("totalCourses", courseRepository.count());
        return "dashboard";
    }
}