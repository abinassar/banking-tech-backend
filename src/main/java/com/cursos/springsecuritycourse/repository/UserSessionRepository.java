package com.cursos.springsecuritycourse.repository;

import com.cursos.springsecuritycourse.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
}

