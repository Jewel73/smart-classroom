package com.classroom.service.user;

import com.classroom.domain.user.Role;

/**
 * Role service to provide implementation for the definitions about a role.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
public interface RoleService {

  /**
   * Create the role with the role instance given.
   *
   * @param role the role
   * @return the persisted role with assigned id
   */
  Role save(final Role role);

  /**
   * Retrieves the role with the specified name.
   *
   * @param name the name of the role to retrieve
   * @return the role tuple that matches the id given
   */
  Role findByName(final String name);
}
