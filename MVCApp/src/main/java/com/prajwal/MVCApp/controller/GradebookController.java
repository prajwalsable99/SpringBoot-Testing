package com.prajwal.MVCApp.controller;

import com.prajwal.MVCApp.models.CollegeStudent;
import com.prajwal.MVCApp.models.Gradebook;
import com.prajwal.MVCApp.models.GradebookCollegeStudent;
import com.prajwal.MVCApp.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

    @Autowired
    private Gradebook gradebook;
    @Autowired
    StudentAndGradeService studentService;



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent>students=studentService.getGradebook();
        m.addAttribute("students",students);

        return "index";
    }

    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student ,Model m) {

        studentService.createStudent(student.getFirstName(), student.getLastName(),
                student.getEmailAddress());
//        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
//        m.addAttribute("students", collegeStudents);
        return "redirect:/";
    }






    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id,Model m){

        if(!studentService.checkIfStudentIsNul(id)){
            return "error";
        }else{
            studentService.deleteStudentById(id);

            return "redirect:/";
        }


    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {

        if(!studentService.checkIfStudentIsNul(id))return "error";

       studentService.configureStudentInfo(id,m);

        return "studentInformation";
    }

    @PostMapping("/grades")
    public String createGrade(@RequestParam("grade") double grade ,@RequestParam("gradeType") String gradeType,@RequestParam("studentId") int studentId,Model m){

        if(!studentService.checkIfStudentIsNul(studentId))return "error";

        boolean success=studentService.createGrade(grade,studentId,gradeType);
        if(!success)return "error";

        studentService.configureStudentInfo(studentId,m);
//        return "studentInformation";
        return "redirect:/studentInformation/"+studentId;


    }

    @GetMapping("/grades/{id}/{gradeType}")
    public String delGrade(@PathVariable int id,@PathVariable String gradeType,Model m){

        int studentId=studentService.deleteGrade(id, gradeType);

        if(studentId==0)return "error";


        studentService.configureStudentInfo(studentId,m);

//        return "studentInformation";
        return "redirect:/studentInformation/"+studentId;
    }

}