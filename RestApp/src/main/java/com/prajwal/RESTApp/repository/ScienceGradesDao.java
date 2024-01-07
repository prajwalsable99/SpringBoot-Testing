package com.prajwal.RESTApp.repository;

import com.prajwal.RESTApp.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;

public interface ScienceGradesDao extends CrudRepository<ScienceGrade,Integer> {
    public Iterable<ScienceGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
