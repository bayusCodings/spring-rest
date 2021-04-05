package com.app.manager.controller;

import com.app.manager.dto.CreateUser;
import com.app.manager.dto.EditUser;
import com.app.manager.dto.Response;
import com.app.manager.exception.ResourceNotFoundException;
import com.app.manager.model.User;
import com.app.manager.service.user.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value="user-resource", description="manage users")
public class UserController {
    @Autowired private IUserService userService;

    @PostMapping("/user/new")
    @ApiOperation(value = "Create new user", response = User.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUser user) {
        User newUser = new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setGender(user.getGender());
            newUser.setDateOfBirth(user.getDateOfBirth());
            newUser.setPhone(user.getPhone());
            newUser.setNationality(user.getNationality());

        return new ResponseEntity<>(userService.save(newUser), HttpStatus.CREATED);
    }

    @GetMapping("/user/block/{userId}")
    @ApiOperation(value = "block user", response = User.class)
    public ResponseEntity<?> blockUser(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return new ResponseEntity<>(userService.blockUser(user), HttpStatus.OK);
    }

    @GetMapping("/user/unblock/{userId}")
    @ApiOperation(value = "unblock user", response = User.class)
    public ResponseEntity<?> unblockUser(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return new ResponseEntity<>(userService.unblockUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{userId}")
    @ApiOperation(value = "delete user", response = User.class)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        userService.delete(userId);
        Response response = Response.builder()
            .statusCode(200)
            .message("user deleted successfully")
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/user/multiple/delete")
    @ApiOperation(value = "delete multiple users", response = User.class)
    public ResponseEntity<?> deleteMultipleUsers(@Valid @RequestBody List<Long> userIds) {
        userService.multipleDelete(userIds);
        Response response = Response.builder()
            .statusCode(200)
            .message("all users deleted successfully")
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/user/update/{userId}")
    @ApiOperation(value = "update user details", response = User.class)
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody EditUser userDetails) {
        User user = userService.getUserById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setGender(userDetails.getGender());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setNationality(userDetails.getNationality());

        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping("/user")
    @ApiOperation(value = "search", response = User.class)
    public ResponseEntity<?> searchForUsers(@RequestParam("search") String searchPhrase) {
        return new ResponseEntity<>(userService.search(searchPhrase), HttpStatus.OK);
    }
}