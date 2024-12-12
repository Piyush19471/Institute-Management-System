package com.project.institute_management_software.services;

import com.project.institute_management_software.dto.AddInstructorRequest;
import com.project.institute_management_software.dto.EditInstructorRequest;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public Instructor addInstructor(AddInstructorRequest addInstructorRequest){
        Instructor instructor = new Instructor();
        instructor.setName(addInstructorRequest.getName());
        instructor.setEmail(addInstructorRequest.getEmail());
        instructor.setSpecialization(addInstructorRequest.getSpecialization());
        return instructorRepository.save(instructor);
    }

    public Instructor getInstructorById(int id){
        return instructorRepository.findById(id).orElse(null);
    }

    public List<Instructor> getAllInstructors(){
        return instructorRepository.findAll();
    }

    public Instructor editInstructor(int id, EditInstructorRequest editInstructorRequest) {
        // Find the instructor by ID, or throw an exception if not found
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + id));

        // Update the instructor's fields with the new data
        instructor.setName(editInstructorRequest.getName());
        instructor.setEmail(editInstructorRequest.getEmail());
        instructor.setSpecialization(editInstructorRequest.getSpecialization());

        // Save the updated instructor object to the database
        return instructorRepository.save(instructor);
    }

    public Instructor deleteInstructor(int id){
        Instructor existingInstructor = instructorRepository.findById(id).orElse(null);
        if(existingInstructor != null){
            instructorRepository.deleteById(id);
        }
        return existingInstructor;
    }
}
