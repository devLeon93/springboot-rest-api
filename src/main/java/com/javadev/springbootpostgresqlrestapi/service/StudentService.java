package com.javadev.springbootpostgresqlrestapi.service;

import com.javadev.springbootpostgresqlrestapi.dto.StudentDTO;
import com.javadev.springbootpostgresqlrestapi.dto.build.BuildStudentProperty;

import java.util.List;

public interface StudentService {

    BuildStudentProperty createStudent(StudentDTO studentDTO);
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    BuildStudentProperty updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id, StudentDTO studentDTO);

}
