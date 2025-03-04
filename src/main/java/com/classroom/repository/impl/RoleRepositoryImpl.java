package com.classroom.repository.impl;

import com.classroom.domain.user.Role;
import com.classroom.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * The RoleRepositoryImpl class provides implementation for the RoleRepository definitions.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
  @PersistenceContext private final EntityManager entityManager;

  @Override
  public Role merge(Role role) {
    entityManager.merge(role);
    return role;
  }

  @Override
  public Role findByName(String name) {

    return entityManager
        .createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
        .setParameter("name", name)
        .getResultList()
        .stream()
        .findFirst()
        .orElse(null);
  }
}
