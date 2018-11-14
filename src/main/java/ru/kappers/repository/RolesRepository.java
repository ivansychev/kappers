package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.Role;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    Role getByName(String roleName);
}
