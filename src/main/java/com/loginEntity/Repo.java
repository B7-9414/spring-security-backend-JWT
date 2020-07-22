package com.loginEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
SELECT * FROM test4.login where username ='khm'
join  test4.todo 
on test4.todo.username = test4.login.username;
*/
@Repository
public interface Repo extends JpaRepository<Login, Long> {

	Login findByUsername(String username);
}