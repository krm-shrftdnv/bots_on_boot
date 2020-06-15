package kpfu.itis.g804.bots_project.service;

import kpfu.itis.g804.bots_project.model.User;
import kpfu.itis.g804.bots_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUserPoint(Long userId, String messenger){
        if(userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            user.setRating(user.getRating() + 1);
            userRepository.save(user);
        } else {
            User user = User.builder()
                    .messenger(messenger)
                    .id(userId)
                    .rating(1)
                    .build();
            userRepository.save(user);
        }
    }

    @Override
    public List<User> getTopUsers(){
        int size = userRepository.findAll().size();
        if (size > 10) {
            return userRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rating"))).getContent().subList(0,2);
        } else
            return userRepository.findAll(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "rating"))).getContent().subList(0,2);
    }

    @Override
    public Integer getUserRating(Long userId){
        if(userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get().getRating();
        } else return 0;
    }

}
