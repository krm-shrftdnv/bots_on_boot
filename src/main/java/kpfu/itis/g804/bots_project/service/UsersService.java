package kpfu.itis.g804.bots_project.service;

import kpfu.itis.g804.bots_project.model.User;

import java.util.List;

public interface UsersService {
    void addUserPoint(Long userId, String messenger);
    List<User> getTopUsers();
    Integer getUserRating(Long userId);
}
