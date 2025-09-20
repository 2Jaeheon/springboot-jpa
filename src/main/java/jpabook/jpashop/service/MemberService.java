package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 읽기 적용으로 써두면 좋다.
// JPA의 모든 로직은 가능한 한 트랜젝션 안에서 처리되어야 함.
// 이걸 해야 쓰기 지연이 가능하고 lazy loading도 가능해짐
// atomicity를 보장해줌
public class MemberService {

    /*
    // 이렇게 쓰면 테스트를 하거나 할 때 바꾸기가 어려움.
    @Autowired // 필드 injection
    private MemberRepository memberRepository;
    */

    /*
    setter injection은 개발 중간에 바꿀 일이 없음.
    또한 set을 통해서 중간에 바뀌는 경우가 가능해짐
    대부분은 애플리케이션 로딩시에 대부분 다 끝남.
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository
    }
    */

    // 다음과 같이 생성자 injection 을 사용해줘야 함.
    // 중간에 set으로 바꿀 수 없음
    // 또한 테스트를 하거나 할 때 주입을 해줘야함. new MemberService() 할 때 의존성을 명확히 파악 가능함.
    // 귀찮으면 RequiredArgsConstructor 써주자 (final 붙은 거 constructor 생성해줌)
    // final을 붙이면 런타임이 아닌 compile 시점에 잡아낼 수 있다.
    @Autowired
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // WAS가 동시에 여러개가 뜨면, 동시에 memberA가 동시에 DB에 insert를 하면 문제가 발생할 수 있음.
    // 실무에서는 한 번 더 방어해줘야 함.
    // 데이터베이스 멤버의 네임을 유니크 제약조건을 잡아줘야함.

    /**
     * 회원 가입
     */
    @Transactional // 얘는 수정이기 때문에 readOnly 없어야 함. (이게 우선)
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        // Member 엔티티는 영속화 (persist)될 때 ID가 자동으로 생성됨 (@GeneratedValue 해놨음)
        // DB insert 쿼리가 실행될 때 DB가 PK를 생성
        // Hibernate는 insert 직후에 DB가 생성한 ID를 조회해서 엔티티 객체의 id 필드에 세팅
        // 따라서 save()를 호출한 시점 이후에는 member.getId()가 채워져 있음.
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 실무에서는 name을 유니크 제약을 걸어두어야 함.
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원임");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
