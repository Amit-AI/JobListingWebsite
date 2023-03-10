package com.site.joblisting.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.site.joblisting.entities.User;
import com.site.joblisting.exceptions.NotFoundException;
import com.site.joblisting.repositories.UserJobRepository;
import com.site.joblisting.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UserDaoImpl_springDataJpa implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserJobRepository userJobRepository;



    @Override
    public void deleteUser(int id) {

        //cleaning up jobs which this user applied to
        userJobRepository.deleteJobWhenUserDeleted(id);
        
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("User Not Found With ID: " + id);
        }
        
        
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found With ID: " + id));
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);

    }

    @Override
    public void updateUser(int id, User user) {
        User tempUser = getUserById(id);

        tempUser.setUserName(user.getUserName());
        tempUser.setUserPwd(user.getUserPwd());
        tempUser.setUserEmail(user.getUserEmail());
        tempUser.setUserRole(user.getUserRole());
        System.out.println();
        userRepository.save(tempUser);

    }

    @Override
    public List<Integer> getAllJobIdByUserId(int userId){
        return userJobRepository.findAllJobIdByUserId(userId);
    }

}
