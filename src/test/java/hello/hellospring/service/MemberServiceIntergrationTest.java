package hello.hellospring.service;
// 통합테스트 알아보기 : 스프링 컨테이너와 DB 통합하기
//@SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다
//@Transactional:테스트 케이스에 애노테이션이 있으면, 테스트 시작 전에
// 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지
// 않으므로 다음 테스트에 영향을 주지 않는다다import hello.hellospring.domain.Member;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntergrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

//테스트 코드를 잘 짜야한다 중요
    @Test //ctrl + shift + t 로 테스트 자동으로 만들어주었는데
    //test는 한글로 해도 된다고함
    //빌드될때 이 테스트코드는 실행되지 않는다고 함
    @Commit
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        org.assertj.core.api.Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        /*try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        //then
    }

}