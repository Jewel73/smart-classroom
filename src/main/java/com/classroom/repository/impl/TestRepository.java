package com.classroom.repository.impl;

import com.classroom.domain.Test.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = true)
public interface TestRepository extends JpaRepository<Test, Long> {}

