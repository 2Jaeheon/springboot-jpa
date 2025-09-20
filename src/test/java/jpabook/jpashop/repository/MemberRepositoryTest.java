package jpabook.jpashop.repository;

import static org.junit.jupiter.api.Assertions.*;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 테스트에서 이거 쓰면 롤백됨.
class MemberRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false) // 확인해보고 싶으면 이렇게
    public void 회원가입() throws Exception {

        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);
        // 기본적으로 persist를 한다고 insert문이 나가지 않음 (Generated 전략에서)
        // 왜냐면 트랜젝션이 커밋될 때 flush 되면서 커밋되는 것.

        // then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}