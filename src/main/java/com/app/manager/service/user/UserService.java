package com.app.manager.service.user;

import com.app.manager.model.User;
import com.app.manager.repository.UserRepository;
import com.app.manager.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User blockUser(User user) {
        user.setBlocked(true);
        return save(user);
    }

    @Override
    public User unblockUser(User user) {
        user.setBlocked(false);
        return save(user);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void multipleDelete(List<Long> userIds) {
        userRepository.deleteByIdIn(userIds);
    }

    @Override
    public List<User> search(String searchPhrase) {
        return userRepository.findAll(Specification.where(UserSpecification.containsTextInName(searchPhrase)));
    }
}
