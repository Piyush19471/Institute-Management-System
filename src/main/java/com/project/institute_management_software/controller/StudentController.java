package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.AddStudentRequest;
import com.project.institute_management_software.dto.StudentResponse;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Student;
import com.project.institute_management_software.services.CourseService;
import com.project.institute_management_software.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller

@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    // Display Form to Add a New Student
    @GetMapping("/form")
public String showStudentForm(Model model) {    
    model.addAttribute("student", new AddStudentRequest());
    List<Course> courses = courseService.getAllCourses();

    // If courses are null, assign an empty list to prevent Thymeleaf errors
    model.addAttribute("courses", courses != null ? courses : new ArrayList<>());
    return "students/form";
}
    // Save New Student
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute AddStudentRequest addStudentRequest) {
        studentService.addStudent(addStudentRequest);
        return "redirect:/students/list";
    }

    // List All Students
    @GetMapping("/list")
    public String listStudents(Model model) {
        List<StudentResponse> students = studentService.getAllStudents()
                .stream()
                .map(StudentResponse::toStudentResponse)
                .toList();
        model.addAttribute("students", students);
        return "students/list";
    }

    // Edit Student
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", StudentResponse.toStudentResponse(student));
        model.addAttribute("courses", courseService.getAllCourses());
        return "students/form";
    }

    // Delete Student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentService.deleteStudentById(id);
        return "redirect:/students/list";
    }

    


    @GetMapping("/search")
    public String searchByCourseAndStudent(@RequestParam("keyword") String keyword, Model model) {
        // Call service to get search results
        List<StudentResponse> results = studentService.search(keyword);

        // Add results to the model
        model.addAttribute("results", results);

        // Return the Thymeleaf view
        return "search/student";
    }

}
