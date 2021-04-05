package com.app.manager.service.user;

import com.app.manager.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User save(User user);
    Optional<User> getUserById(Long userId);
    User blockUser(User user);
    User unblockUser(User user);
    void delete(Long userId);
    void multipleDelete(List<Long> userIds);
    List<User> search(String searchPhrase);
}
