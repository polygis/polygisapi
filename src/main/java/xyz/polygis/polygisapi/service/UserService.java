package xyz.polygis.polygisapi.service;


import xyz.polygis.polygisapi.model.User;
import xyz.polygis.polygisapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User User) {
        return userRepository.save(User);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}