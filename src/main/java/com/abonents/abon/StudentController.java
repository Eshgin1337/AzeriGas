package com.abonents.abon;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class StudentController {
    
    @GetMapping("/student")
    public Student getMethodName() {
        return new Student("Eshgin", "Hasanov");
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Kerim", "Hasanov"));
        students.add(new Student("Eshgin", "Hasanov"));
        students.add(new Student("Kamil", "Aghayev"));
        return students;
    }
    
    @GetMapping("/student/{fName}/{lName}")
    public Student studentPathVariable(@PathVariable("fName") String fName, @PathVariable("lName") String lName) {
        return new Student(fName, lName);
    }

    @GetMapping("/studentparam")
    public Student studentRequestParam(@RequestParam(name = "fname") String fName, @RequestParam(name = "lname") String lName) {
        return new Student(fName, lName);
    }
    
}
