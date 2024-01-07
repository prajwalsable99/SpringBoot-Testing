package com.prajwal.RESTApp.repository;

import com.prajwal.RESTApp.models.HistoryGrade;

import org.springframework.data.repository.CrudRepository;

public interface HistoryGradesDao extends CrudRepository<HistoryGrade,Integer> {

    public Iterable<HistoryGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
