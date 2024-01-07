package com.prajwal.MVCApp.service;

import com.prajwal.MVCApp.models.*;
import com.prajwal.MVCApp.repository.HistoryGradesDao;
import com.prajwal.MVCApp.repository.MathGradesDao;
import com.prajwal.MVCApp.repository.ScienceGradesDao;
import com.prajwal.MVCApp.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    StudentGrades studentGrades;


    public void createStudent(String firstName, String lastName, String emailAddress) {

        CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }


    public boolean checkIfStudentIsNul(int id) {

        Optional<CollegeStudent> student = studentDao.findById(id);
        if (student.isPresent()) return true;
        return false;
    }

    public void deleteStudentById(int id) {

        if (checkIfStudentIsNul(id)) {
            studentDao.deleteById(id);

            // to delete grades
            mathGradesDao.deleteByStudentId(id);
            scienceGradesDao.deleteByStudentId(id);
            historyGradesDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {

        return studentDao.findAll();
    }

    //------------------------------------------grade s


    public boolean createGrade(double grade, int studentId, String gradeType) {

        if (!checkIfStudentIsNul(studentId)) return false;

        if (grade >= 0 && grade <= 100) {
            if (gradeType.equals("math")) {
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }
            if (gradeType.equals("science")) {
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesDao.save(scienceGrade);
                return true;
            }
            if (gradeType.equals("history")) {
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradesDao.save(historyGrade);
                return true;
            }
        }
        return false;

    }

    public int deleteGrade(int id, String gradeType) {


        int studentId = 0;
        if (gradeType.equals("math")) {

            Optional<MathGrade> mathGrade1 = mathGradesDao.findById(id);
            if (!mathGrade1.isPresent()) {
                return studentId;
            }
            studentId = mathGrade1.get().getStudentId();
            mathGradesDao.deleteById(id);

        }


        if (gradeType.equals("science")) {


            Optional<ScienceGrade> scienceGrade1 = scienceGradesDao.findById(id);
            if (!scienceGrade1.isPresent()) {
                return studentId;
            }
            studentId = scienceGrade1.get().getStudentId();
           scienceGradesDao.deleteById(id);

        }
        if (gradeType.equals("history")) {

            Optional<HistoryGrade>historyGrade1 = historyGradesDao.findById(id);
            if (!historyGrade1.isPresent()) {
                return studentId;
            }
            studentId = historyGrade1.get().getStudentId();
            historyGradesDao.deleteById(id);

        }

        return studentId;

    }

    public GradebookCollegeStudent studentInformation(int id) {

        if(!checkIfStudentIsNul(id)) return null;

       Optional<CollegeStudent> student=studentDao.findById(id);

//       if(student.isEmpty())return null;


       Iterable<MathGrade> mathGrades=mathGradesDao.findGradeByStudentId(id);
       Iterable<HistoryGrade> historyGrades=historyGradesDao.findGradeByStudentId(id);
       Iterable<ScienceGrade> scienceGrades=scienceGradesDao.findGradeByStudentId(id);

        List<Grade> mathGradesList=new ArrayList<>();
        mathGrades.forEach(mathGradesList::add);

        List<Grade> scienceGradesList=new ArrayList<>();
        scienceGrades.forEach(scienceGradesList::add);


        List<Grade> historyGradesList=new ArrayList<>();
        historyGrades.forEach(historyGradesList::add);

        studentGrades.setMathGradeResults(mathGradesList);
        studentGrades.setScienceGradeResults(scienceGradesList);
        studentGrades.setHistoryGradeResults(historyGradesList);

        GradebookCollegeStudent gradebookCollegeStudent=new GradebookCollegeStudent(student.get().getId(), student.get().getFirstName(),student.get().getLastName(), student.get().getEmailAddress(), studentGrades);





        return gradebookCollegeStudent;
    }

    public  void configureStudentInfo(int id ,Model m){


        GradebookCollegeStudent gradebookCollegeStudent=studentInformation(id);
        m.addAttribute("student",gradebookCollegeStudent);


        if(!gradebookCollegeStudent.getStudentGrades().getMathGradeResults().isEmpty()){

            m.addAttribute("mathAverage",gradebookCollegeStudent.getStudentGrades().
                    findGradePointAverage(
                            gradebookCollegeStudent.getStudentGrades().getMathGradeResults()
                    ));

        }else {
            m.addAttribute("mathAverage","N/A");
        }
        if(!gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().isEmpty()){
            m.addAttribute("scienceAverage",gradebookCollegeStudent.getStudentGrades().
                    findGradePointAverage(
                            gradebookCollegeStudent.getStudentGrades().getScienceGradeResults()
                    ));
        }else {
            m.addAttribute("scienceAverage","N/A");

        }

        if(!gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().isEmpty()){
            m.addAttribute("historyAverage",gradebookCollegeStudent.getStudentGrades().
                    findGradePointAverage(
                            gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults()
                    ));
        }else {
            m.addAttribute("historyAverage","N/A");

        }

    }
}
