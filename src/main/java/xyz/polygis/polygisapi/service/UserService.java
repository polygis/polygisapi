package xyz.polygis.polygisapi.service;

import xyz.polygis.polygisapi.model.User;
import xyz.polygis.polygisapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<User> update(Long id, String membership) {
        @SuppressWarnings("null")
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return user;
        }
        User dbItem = user.get();
        dbItem.setMembership(membership);
        dbItem = userRepository.save(dbItem);
        return Optional.of(dbItem);
    }

    public void updateUserMembershipByID(Long id, String membership) {
        if (id != null) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                user.get().setMembership(membership);
            }
        }
    }

    public Optional<User> findByEmail(String email) {
        if (email != null) {
            User userToFind = new User();
            userToFind.setEmail(email);
            Example<User> userExample = Example.of(userToFind);
            Optional<User> existingUser = userRepository.findOne(userExample);
            return existingUser;
        } else {
            return null;
        }
    }

    public User save(User user) {
        if (user != null) {
            try {
                Optional<User> existingUser = findByEmail(user.getEmail());
                if (!existingUser.isPresent()) {
                    return userRepository.save(user);
                } else {
                    return existingUser.get();
                }
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            userRepository.deleteById(id);
        } else {
            throw new NullPointerException();
        }
    }
}