package kpfu.itis.g804.bots_project.repository;

import kpfu.itis.g804.bots_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
