package com.tumo.model.service;

import java.util.Collections;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.model.UserDto;
import com.tumo.model.dao.UserDao;

@Component("userDetailService")
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByEmail(username);
				
		Optional<UserDto> opt = Optional.of(userDto);
		
		return opt.map(this::createUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
		
	}
	
	// DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(UserDto userDto) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userDto.getLogin_type());

        return new User(
                String.valueOf(userDto.getEmail()),
                userDto.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
		
}
