package com.site.joblisting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.site.joblisting.dao.JobDao;
import com.site.joblisting.dao.UserDao;
import com.site.joblisting.dto.UserJobDTO;
import com.site.joblisting.entities.Job;
import com.site.joblisting.entities.User;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    JobDao jobDao;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return new ResponseEntity<>(userDao.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> insertUser(@Validated @RequestBody User user) {
        userDao.insertUser(user);
        return new ResponseEntity<>("User inserted successfully!!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User user) {
        userDao.updateUser(id, user);
        return new ResponseEntity<>("User update successfully!!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userDao.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully!!", HttpStatus.OK);
    }

    @GetMapping("/jobs/{userId}")
    public ResponseEntity<UserJobDTO> getUserAppliedJobs(@PathVariable int userId){
        User user = userDao.getUserById(userId);
        String userName = user.getUserName();
        List<Job> jobs = userDao.getAllJobIdByUserId(userId).stream().map(id -> jobDao.getJob(id)).toList();

        return new ResponseEntity<>(new UserJobDTO(userName, jobs), HttpStatus.OK);
    }

}
