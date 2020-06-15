package kpfu.itis.g804.bots_project.service;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session startSession(Long sessionId, Long starterId, String imgUrl, String answer) {
        if (sessionRepository.findById(sessionId).isPresent()) {
            Session session = sessionRepository.findById(sessionId).get();
            if (!session.isOpen()) {
                session.setGameStarterId(starterId);
                session.setImgUrl(imgUrl);
                session.setAnswer(answer);
                session.setGuessed(false);
                session.setOpen(true);
                sessionRepository.save(session);
            }
            return session;
        } else {
            Session session = Session.builder()
                    .id(sessionId)
                    .gameStarterId(starterId)
                    .imgUrl(imgUrl)
                    .isOpen(true)
                    .lastId(-1)
                    .answer(answer)
                    .isGuessed(false)
                    .build();
            sessionRepository.save(session);
            return session;
        }
    }

    @Override
    public Optional<Session> getSession(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    public Session updateSession(Long sessionId, int lastId, String imgUrl, String answer, boolean isGuessed) {
        Session session = sessionRepository.findById(sessionId).get();
        session.setGuessed(isGuessed);
        session.setLastId(lastId);
        session.setImgUrl(imgUrl);
        session.setAnswer(answer);
        sessionRepository.save(session);
        return session;
    }

    @Override
    public void stopSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).get();
        session.setOpen(false);
        sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

}
