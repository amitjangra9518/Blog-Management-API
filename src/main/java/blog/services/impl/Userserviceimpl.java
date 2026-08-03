package blog.services.impl;

import blog.entity.User;
import blog.exception.ResourceNotFoundException;
import blog.payloads.UserDto;
import blog.payloads.UserResponse;
import blog.repo.Userrepo;
import blog.services.Userservice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Userserviceimpl implements Userservice {

    @Autowired
    private Userrepo userrepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);

        User savedUser = this.userrepo.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateuser(UserDto userDto, Integer userid) {
        User user = this.userrepo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userid));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout((userDto.getAbout()));
        User updatedUser=this.userrepo.save(user);
        UserDto userDto1=this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userrepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return this.userToDto(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize,String sortBy) {
        PageRequest p= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<User> page=this.userrepo.findAll(p);
        List<User> users= page.getContent();
       List<UserDto> userDtos=users.stream().map
               (user -> this.userToDto(user))
               .collect(Collectors.toList());
        UserResponse userResponse=new UserResponse();
        userResponse.setContent(userDtos);
        userResponse.setPageNumber(page.getNumber());
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalUser(page.getTotalElements());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLastPage(page.isLast());
       return userResponse;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userrepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userrepo.delete(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users= this.userrepo.findByNameContaining(keyword);
        List<UserDto> userDtos=users.stream()
                .map((user)->this.modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }

    // Convert DTO to Entity
    private User dtoToUser(UserDto userDto) {

        User user =this.modelMapper.map(userDto,User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());

        return user;
    }

    // Convert Entity to DTO
    public UserDto userToDto(User user) {

        UserDto userDto = this.modelMapper.map(user,UserDto.class);

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());

        return userDto;
    }
}