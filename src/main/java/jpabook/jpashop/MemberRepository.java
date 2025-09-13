package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    // 스프링부트가 엔티티 메니저를 주입해줌 => 따로 설정 안 해도 됨
    @PersistenceContext
    private EntityManager em;

    // 왜 Id를 반환하지?
    // 커맨드와 쿼리를 분리해라라는 원칙에 의해
    // 저장을 하고나면 사이드 이펙트를 일으키지 않기 위해서 리턴값을 만들지 않음
    // 하지만 조회를 위해서는 ID 정도는 있는게 좋은듯
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}