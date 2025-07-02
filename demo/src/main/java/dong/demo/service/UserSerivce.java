package dong.demo.service;

import dong.demo.domain.User;
import dong.demo.dto.UserDto;
import dong.demo.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSerivce {
    private final UserMapper  userMapper;


    public List<UserDto> findAll() {
        System.out.println("서비스접근");
        List<UserDto> user = userMapper.findAll();
        return user;
    }
}
