package com.prajwal.MVCApp.repository;

import com.prajwal.MVCApp.models.MathGrade;
import org.springframework.data.repository.CrudRepository;

public interface MathGradesDao extends CrudRepository<MathGrade,Integer> {

    public Iterable<MathGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
