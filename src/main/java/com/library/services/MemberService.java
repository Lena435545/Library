package com.library.services;

import com.library.models.Member;
import com.library.repositories.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(int id) {
        Optional<Member> foundMember = memberRepository.findById(id);
        return foundMember.orElse(null);
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void update(int id, Member updatedMember) {
        updatedMember.setMemberId(id);
        memberRepository.save(updatedMember);
    }
    @Transactional
    public void delete(int id) {
        memberRepository.deleteById(id);
    }
}
