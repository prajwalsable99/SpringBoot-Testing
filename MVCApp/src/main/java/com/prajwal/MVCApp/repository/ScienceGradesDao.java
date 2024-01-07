package com.prajwal.MVCApp.repository;

import com.prajwal.MVCApp.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;

public interface ScienceGradesDao extends CrudRepository<ScienceGrade,Integer> {
    public Iterable<ScienceGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
