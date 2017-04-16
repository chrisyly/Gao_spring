package hello.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import hello.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
