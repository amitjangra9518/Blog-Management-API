package blog.controller;

import blog.payloads.ApiResponse;
import blog.payloads.UserDto;
import blog.payloads.UserResponse;
import blog.services.Userservice;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class Usercontroller
{

    @Autowired
    private Userservice userservice;

    // POST - Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {

        UserDto createdUserDto = this.userservice.createUser(userDto);

        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    // PUT - Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser
    (@Valid @RequestBody UserDto userDto, @PathVariable Integer userId)
    {
        UserDto updatedUser=this.userservice.updateuser(userDto,userId);
        return ResponseEntity.ok(updatedUser);
    }
    // DELETE - Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId)
    {
        this.userservice.deleteUser(userId);
        return new ResponseEntity
                (new ApiResponse("User deleted seccussfully",true),HttpStatus.OK);
    }
    // GET - GetAll User
    @GetMapping
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam (value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "userId",required = false)String sortBy
    )
    {
        return ResponseEntity.ok(this.userservice.getAllUsers(pageNumber,pageSize,sortBy));
    }
    // GET - Get User
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId)
    {
        return ResponseEntity.ok(this.userservice.getUserById(userId));
    }
    @GetMapping("/user/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(
            @PathVariable("keywords") String keyword)
    {
        List<UserDto>result=this.userservice.searchUser(keyword);
        return new ResponseEntity<List<UserDto>>(result,HttpStatus.OK);
    }
}