package com.javadev.springbootpostgresqlrestapi.controller;

import com.javadev.springbootpostgresqlrestapi.dto.StudentDTO;
import com.javadev.springbootpostgresqlrestapi.dto.build.BuildStudentProperty;
import com.javadev.springbootpostgresqlrestapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<BuildStudentProperty> create(@Validated @RequestBody StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO,
                                    @PathVariable Long id) {
        studentService.updateStudent(id, studentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> delete(@PathVariable Long id,StudentDTO studentDTO){
        studentService.deleteStudent(id, studentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable("id") Long id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> productDTOS = studentService.getAllStudents();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
}
