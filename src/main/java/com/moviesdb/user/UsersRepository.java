package com.moviesdb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findByUsername(String userName);
}
