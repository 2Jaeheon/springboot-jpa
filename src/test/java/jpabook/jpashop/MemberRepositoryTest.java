package jpabook.jpashop;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    // Transactional이 없다면 안 됨
    // 엔티티 매니저를 통한 모든 데이터 변경은 트렌젝션 안에서 일어나야함.
    // 이게 테스트에 존재하면 테스트 끝난뒤 DB를 롤백해버림
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); //true
        // findMember 랑 member랑 같음
        // 같은 영속성 컨텍스트 안에서는 id값이 같으면 같은 엔티티로 식별함.
        // JPA에서 1차 캐시를 해주는 것.
        // 이전에 em.find, em.save를 했기 때문에 동일하게 나온 것
    }
}