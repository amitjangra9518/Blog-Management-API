package blog.services.impl;

import blog.entity.User;
import blog.enums.Role;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Userserviceimpl implements Userservice {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // ==========================================
    // CREATE USER / REGISTER
    // ==========================================

    @Override
    public UserDto createUser(UserDto userDto) {

        // Convert DTO -> Entity
        User user = this.dtoToUser(userDto);

        // Encrypt password
        user.setPassword(
                this.passwordEncoder.encode(
                        userDto.getPassword()
                )
        );

        // IMPORTANT:
        // Every newly registered user is a normal USER
        user.setRole(Role.ROLE_USER);

        // Save user
        User savedUser = this.userrepo.save(user);

        return this.userToDto(savedUser);
    }


    // ==========================================
    // UPDATE USER
    // ==========================================

    @Override
    public UserDto updateuser(
            UserDto userDto,
            Integer userid) {

        User user = this.userrepo.findById(userid)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                userid
                        )
                );

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());

        // Update password only if provided
        if (userDto.getPassword() != null &&
                !userDto.getPassword().isBlank()) {

            user.setPassword(
                    this.passwordEncoder.encode(
                            userDto.getPassword()
                    )
            );
        }

        /*
         * Don't change the role here.
         *
         * A normal user should not be able to send:
         *
         * "role": "ROLE_ADMIN"
         *
         * and become an admin.
         */

        User updatedUser =
                this.userrepo.save(user);

        return this.userToDto(updatedUser);
    }


    // ==========================================
    // GET USER BY ID
    // ==========================================

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userrepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                userId
                        )
                );

        return this.userToDto(user);
    }


    // ==========================================
    // GET ALL USERS
    // ==========================================

    @Override
    public UserResponse getAllUsers(
            Integer pageNumber,
            Integer pageSize,
            String sortBy) {

        PageRequest p = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(sortBy)
        );

        Page<User> page =
                this.userrepo.findAll(p);

        List<UserDto> userDtos =
                page.getContent()
                        .stream()
                        .map(this::userToDto)
                        .collect(Collectors.toList());

        UserResponse userResponse =
                new UserResponse();

        userResponse.setContent(userDtos);
        userResponse.setPageNumber(
                page.getNumber()
        );
        userResponse.setPageSize(
                page.getSize()
        );
        userResponse.setTotalUser(
                page.getTotalElements()
        );
        userResponse.setTotalPages(
                page.getTotalPages()
        );
        userResponse.setLastPage(
                page.isLast()
        );

        return userResponse;
    }


    // ==========================================
    // DELETE USER
    // ==========================================

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userrepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                userId
                        )
                );

        this.userrepo.delete(user);
    }


    // ==========================================
    // SEARCH USER
    // ==========================================

    @Override
    public List<UserDto> searchUser(
            String keyword) {

        List<User> users =
                this.userrepo.findByNameContaining(
                        keyword
                );

        return users.stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }


    // ==========================================
    // DTO -> ENTITY
    // ==========================================

    private User dtoToUser(UserDto userDto) {

        return this.modelMapper.map(
                userDto,
                User.class
        );
    }


    // ==========================================
    // ENTITY -> DTO
    // ==========================================

    public UserDto userToDto(User user) {

        UserDto userDto =
                this.modelMapper.map(
                        user,
                        UserDto.class
                );

        // Don't return encrypted password
        userDto.setPassword(null);

        return userDto;
    }
}