package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 * implements 받기.
 */
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {     //null 대신 Optional value로 표현되는 형식의 솔루션 제공 -> nullPountException을 해결하기 위한 방법.
        return Optional.ofNullable(store.get(id)); //ofNullable 사용하면 파라미터가 null이여도 예외가 발생하지 않고 빈Optional 객체가 반환.
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()  //반복을 통해 member와 name값이 같으면 값을 반환.
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    public void clearStore() {
        store.clear();
    }


}
