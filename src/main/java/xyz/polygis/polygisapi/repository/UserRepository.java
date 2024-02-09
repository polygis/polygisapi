package xyz.polygis.polygisapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.polygis.polygisapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
