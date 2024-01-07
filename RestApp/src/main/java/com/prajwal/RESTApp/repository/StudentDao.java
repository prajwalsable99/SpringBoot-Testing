package com.prajwal.RESTApp.repository;

import com.prajwal.RESTApp.models.CollegeStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends CrudRepository <CollegeStudent,Integer>{

    public CollegeStudent findByEmailAddress(String emailAddress);



}
