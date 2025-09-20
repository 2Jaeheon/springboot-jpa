package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /*
    // 스프링이 엔티티 메니저를 만들어서 주입해줌
    @PersistenceContext
    private EntityManager em;
    */

    private final EntityManager em;
    // 이렇게 해도 가능해진다.

    // 직접 주입하고 싶으면 다음과 같이 구축
    /*
    @PersistenceUnit
    private EntityManagerFactory emf;
    */

    public void save(Member member) {
        // persist 영속성 컨텍스트에 Member entity 객체를 넣어줌
        // 트렌젝션이 커밋되는 순간에 DB에 인스턴스 쿼리가 날라가서 반영됨
        em.persist(member);
    }

    public Member findOne(Long id) {
        // 단건 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // sql은 테이블을 대상으로 쿼리하지만,
        // member에 대한 엔티티 객체에 대해서 쿼리를 날림
        // jpql 참고할 것

        // JPQL은 SQL이랑 동일하지만 테이블이 아닌 엔티티로 넣으면 됨
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByName(String name) {
        // 파라미터를 바인딩해서 특정 회원만 탐색
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name) // 이렇게 하면 파라미터가 바인딩됨
            .getResultList();
    }
}
