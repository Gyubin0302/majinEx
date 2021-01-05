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
public class MemberService implements UserDetailsService {
	@Autowired
	private MemberDao memberDao;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		//Optional<Member> memberEntity = memberDao.findById(id);
		//Member member = memberEntity.orElse(null);
		Member member = memberDao.findById(id);
		if(member.getAble()==1) {
			throw new UsernameNotFoundException(id);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
		
		return new User(member.getId(), member.getPw(), authorities);
	}

	@Transactional
    public String save(MemberDTO memberDTO) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDTO.setPw(passwordEncoder.encode(memberDTO.getPw()));
        memberDTO.setEmail(memberDTO.getEmail().replace(",", "@"));
        memberDTO.setRole(memberDTO.getId().equals("admin") ? "ADMIN" : "USER");
		Member member = memberDTO.toEntity();
        return memberDao.save(member).getId();
    }
	
	public void newPw(String id,String newPw) {
		Member member = memberDao.findById(id);
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmail(member.getEmail());
		memberDTO.setId(member.getId());
		memberDTO.setMid(member.getMid());
		memberDTO.setName(member.getName());
		memberDTO.setNick(member.getNick());
		memberDTO.setAble(member.getAble());
		memberDTO.setPw(newPw);
		save(memberDTO);
	}
	
	public boolean checkPw(String id,String oldpw,String pw) {
		Member member = memberDao.findById(id);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(passwordEncoder.matches(oldpw, member.getPw())) {
			newPw(id,pw);
			return true;
		}else {
			return false;
		}
	}

	public String findEmail(String id) {
		Member member = memberDao.findById(id);
		return member.getEmail();
	}
	
	public void disableId(String id) {
		Member member = memberDao.findById(id);
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmail(member.getEmail());
		memberDTO.setId(member.getId());
		memberDTO.setMid(member.getMid());
		memberDTO.setName(member.getName());
		memberDTO.setNick(member.getNick());
		memberDTO.setPw(member.getPw());
		memberDTO.setRole(member.getRole());
		memberDTO.setAble(1);
		member = memberDTO.toEntity();
		memberDao.save(member);
	}
	
	public boolean idcheck(String id) {
		return memberDao.findById(id) != null ? true : false;
	}
	
	public List<Member> findAll() {
		List<Member> members = memberDao.findAll();
		return members;
	}
}
