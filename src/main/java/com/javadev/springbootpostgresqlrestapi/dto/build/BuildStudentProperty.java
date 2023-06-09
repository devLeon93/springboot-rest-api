package com.javadev.springbootpostgresqlrestapi.dto.build;

import com.javadev.springbootpostgresqlrestapi.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildStudentProperty {

    private String firstName;
    private String lastname;


    public static BuildStudentProperty fromStudent(Student student){
        BuildStudentProperty buildStudentProperty = new BuildStudentProperty();
        buildStudentProperty.setFirstName(student.getLastName());
        buildStudentProperty.setLastname(student.getLastName());


        return buildStudentProperty;
    }


}
