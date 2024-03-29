package com.marketkurly.util;

import com.marketkurly.dto.requsetDto.SignupRequestDto;
import com.marketkurly.exception.DuplicateUserException;
import com.marketkurly.exception.EmailFormException;
import com.marketkurly.exception.EmptyException;
import com.marketkurly.exception.LengthException;
import com.marketkurly.model.User;
import com.marketkurly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SignupValidator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User validate(SignupRequestDto signupRequestDto) {

        String email = signupRequestDto.getEmail().trim();
        String username = signupRequestDto.getUsername().trim();
        String password = signupRequestDto.getPassword().trim();
        Optional<User> userByEmail = userRepository.findByEmail(email);
        String pattern = "(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%^&*]*$";

        if (email.equals("") || username.equals("") || password.equals("")) {
            throw new EmptyException("모든 내용을 입력해주세요.");
        } else if (userByEmail.isPresent()) {
            throw new DuplicateUserException("이메일이 중복되었습니다.");
        } else if (!isValidEmail(email)) {
            throw new EmailFormException("이메일 형식으로 입력해주세요.");
        } else if ( (password.length() < 8 || password.length() > 16) || !Pattern.matches(pattern, password)) {
            throw new LengthException("비밀번호 8~16자리의 영문과 숫자를 조합해주세요.");
        }
        // 패스워드 인코딩
        password = passwordEncoder.encode(password);

        return User.builder()
                .email(email)
                .password(password)
                .username(username).build();
    }
    public static boolean isValidEmail(String email) {
        // 이메일 형식
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) { err = true; } return err;
    }

    public boolean validate(String email) {
        // 이메일 중복 확인을 위한 체크
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if(userByEmail.isPresent())
            throw new DuplicateUserException("이메일이 중복되었습니다.");
        return true;
    }
}
