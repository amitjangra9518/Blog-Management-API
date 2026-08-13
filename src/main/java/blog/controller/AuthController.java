package blog.controller;

import blog.Security.JwtTokenHelper;
import blog.payloads.ApiResponse;
import blog.payloads.JwtAuthRequest;
import blog.payloads.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @PostMapping("/login")
    public ResponseEntity<?> createToken(
            @RequestBody JwtAuthRequest request) {

        // NOTE: authenticate() throws BadCredentialsException /
        // DisabledException for a wrong password or a disabled
        // account. Previously this was uncaught, so bad login
        // attempts returned a raw 500 Internal Server Error
        // instead of a proper 401/403 with a clean message.
        try {
            authenticate(
                    request.getUsername(),
                    request.getPassword()
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new ApiResponse("Invalid username or password", false),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (DisabledException e) {
            return new ResponseEntity<>(
                    new ApiResponse("User account is disabled", false),
                    HttpStatus.FORBIDDEN
            );
        }


        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        request.getUsername()
                );


        String token =
                jwtTokenHelper.generateToken(userDetails);


        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(token);


        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    private void authenticate(
            String username,
            String password) {


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                );


        authenticationManager.authenticate(
                authenticationToken
        );
    }
}