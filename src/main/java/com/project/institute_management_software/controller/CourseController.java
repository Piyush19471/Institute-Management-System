package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.AddCourseRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditCourseRequest;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.services.CourseService;
import com.project.institute_management_software.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

    // Display the course form
    @GetMapping("/form")
    public String showCourseForm(Model model) {
        List<Instructor> instructors = instructorService.getAllInstructors(); // Assuming you have an InstructorService
        model.addAttribute("instructors", instructors);
        model.addAttribute("course", new AddCourseRequest()); // Empty form object
        return "courses/form"; // form.html will be rendered
    }

    // Save course
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute AddCourseRequest addCourseRequest, Model model) {
        Course course = courseService.addCourse(addCourseRequest);
        model.addAttribute("message", "Course Added Successfully!");
        return "redirect:/courses/list"; // Redirect to the list after saving
    }

    // Display courses list
    @GetMapping("/list")
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses/list"; // list.html will be rendered
    }

    // Display edit course form
    @GetMapping("/edit/{id}")
    public String editCourseForm(@PathVariable int id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            model.addAttribute("course", course); // Passing the course details to form
            return "courses/form"; // Edit using the same form
        }
        return "redirect:/courses/list"; // If course not found, redirect to list
    }

    // Update course
    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable int id, @ModelAttribute EditCourseRequest editCourseRequest, Model model) {
        Course course = courseService.editCourse(id, editCourseRequest);
        model.addAttribute("message", "Course updated successfully!");
        return "redirect:/courses/list"; // Redirect to the list after update
    }

    // Delete course
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable int id, Model model) {
        courseService.deleteCourse(id);
        model.addAttribute("message", "Course deleted successfully!");
        return "redirect:/courses/list"; // Redirect to the list after deletion
    }
}
