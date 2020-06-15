package kpfu.itis.g804.bots_project.service;

import kpfu.itis.g804.bots_project.model.Session;

import java.util.Optional;

public interface SessionService {
    Session startSession(Long sessionId, Long starterId, String imgUrl, String answer);
    Optional<Session> getSession(Long sessionId);
    Session updateSession(Long sessionId, int lastId, String imgUrl, String answer, boolean isGuessed);
    void stopSession (Long sessionId);
    void deleteSession(Long sessionId);
}
