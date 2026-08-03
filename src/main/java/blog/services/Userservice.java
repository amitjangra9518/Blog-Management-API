package blog.services;

import blog.payloads.UserDto;
import blog.payloads.UserResponse;

import java.util.List;

public interface Userservice {

    UserDto createUser(UserDto user);
    UserDto updateuser(UserDto user, Integer userid);
    UserDto getUserById(Integer userId);
   UserResponse getAllUsers(Integer pageNumber, Integer pageSize,String sortBy);
    void deleteUser(Integer userId);
    List<UserDto>searchUser(String keyword);
}