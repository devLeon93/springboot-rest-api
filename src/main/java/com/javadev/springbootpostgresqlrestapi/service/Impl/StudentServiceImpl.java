package com.javadev.springbootpostgresqlrestapi.service.Impl;

import com.javadev.springbootpostgresqlrestapi.dto.StudentDTO;
import com.javadev.springbootpostgresqlrestapi.dto.build.BuildStudentProperty;
import com.javadev.springbootpostgresqlrestapi.model.Student;
import com.javadev.springbootpostgresqlrestapi.repository.StudentRepository;
import com.javadev.springbootpostgresqlrestapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    public static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;


    @Override
    public BuildStudentProperty createStudent(StudentDTO studentDTO) {
        Optional<Student> studentByEmail = studentRepository.findByEmail(studentDTO.getEmail());

        if (studentByEmail.isPresent()) {
            throw new EntityNotFoundException(
                    "The Student " + studentDTO.getEmail() + " already exist. Please check credentials");
        }

        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setTitleFaculty(studentDTO.getTitleFaculty());
        student.setEmail(studentDTO.getEmail());
        LOG.info("Creating Student with email =  {}", studentDTO.getEmail());
        studentRepository.save(student);
        return BuildStudentProperty.fromStudent(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .map(student -> {
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setFirstName(student.getFirstName());
                    studentDTO.setLastName(student.getLastName());
                    studentDTO.setTitleFaculty(student.getTitleFaculty());
                    studentDTO.setEmail(student.getEmail());
                    return studentDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setFirstName(student.getFirstName());
                    studentDTO.setLastName(student.getLastName());
                    studentDTO.setTitleFaculty(student.getTitleFaculty());
                    studentDTO.setEmail(student.getEmail());
                    return studentDTO;
                }).orElseThrow(()-> new EntityNotFoundException("Student with id " + id + " does not exist!"));
    }

    @Override
    public BuildStudentProperty updateStudent(Long id, StudentDTO studentDTO) {
        Student studentEdit = studentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student not found by id " + id));
        if (!studentEdit.getEmail().equals(studentDTO.getEmail())){
            throw new EntityNotFoundException("Student " + studentEdit.getEmail() + " is not allowed to update  with id " + id);
        }
        studentEdit.setFirstName(studentDTO.getFirstName());
        studentEdit.setLastName(studentDTO.getLastName());
        studentEdit.setTitleFaculty(studentDTO.getTitleFaculty());
        LOG.info("Updating Student  {}", " id = " + studentEdit.getId());
        studentRepository.save(studentEdit);
        return BuildStudentProperty.fromStudent(studentEdit);
    }

    @Override
    public void deleteStudent(Long id, StudentDTO studentDTO) {
        Student studentDelete = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student not found by  id " + id));
        LOG.info("Deleted Product for User {}", " id = " + studentDelete.getId());
        studentRepository.deleteById(id);
    }
}
