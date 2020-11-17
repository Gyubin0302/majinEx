package com.majin.bit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.MemberDao;
import com.majin.bit.dto.Member;
import com.majin.bit.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements UserDetailsService {
	@Autowired
	private MemberDao memberDao;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		//Optional<Member> memberEntity = memberDao.findById(id);
		//Member member = memberEntity.orElse(null);
		Member member = memberDao.findById(id);
		System.out.println("가져온 맴버는 : "+member);
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
		
		return new User(member.getId(), member.getPw(), authorities);
	}

	@Transactional
    public String save(MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPw(passwordEncoder.encode(member.getPw()));
        member.setEmail(member.getEmail().replace(",", "@"));
		member.setRole(member.getId().equals("admin") ? "ADMIN" : "USER");
		System.out.println("저장된 멤버는 : "+member);
        return memberDao.save(member).getId();
    }

	public boolean idcheck(String id) {
		return memberDao.findById(id) != null ? true : false;
	}
}
