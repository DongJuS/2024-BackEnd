package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.example.demo.domain.Member;

public class MemberRepositoryMemory implements MemberRepository {

    private static final Map<Long, Member> members = new HashMap<>();
    private static final AtomicLong autoincrement = new AtomicLong(1);

    static {
        // 1번 유저를 미리 만들어둔다.
        members.put(autoincrement.getAndIncrement(), new Member("최준호", "temp@gmail.com", "password"));
    }

    @Override
    public List<Member> findAll() {
        return members.entrySet().stream()
            .map(it -> {
                Member member = it.getValue();
                member.setId(it.getKey());
                return member;
            }).toList();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(members.get(id));
    }
    @Override
    public Member insert(Member member) {
        long id = autoincrement.getAndIncrement();
        members.put(id, member);
        member.setId(id);
        return member;
    }

    @Override
    public Member update(Member member) {
        return members.put(member.getId(), member);
    }

    @Override
    public void deleteById(Long id) {
        members.remove(id);
    }
    @Override
    public boolean existsByEmail(String email) {
        return members.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }
    @Override
    public boolean existsById(Long id) {
        return members.containsKey(id);
    }
}
