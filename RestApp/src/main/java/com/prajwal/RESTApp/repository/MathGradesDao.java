package com.prajwal.RESTApp.repository;

import com.prajwal.RESTApp.models.MathGrade;
import org.springframework.data.repository.CrudRepository;

public interface MathGradesDao extends CrudRepository<MathGrade,Integer> {

    public Iterable<MathGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
