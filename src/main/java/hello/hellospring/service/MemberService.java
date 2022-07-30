package hello.hellospring.service;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; //외부에서 넣어주도록 바꿔줌
    } //이렇게 생성자를 통해 들어오는 것을 생성자 주입이라고 한다

    //회원 가입
    public Long join(Member member){
        //long start = System.currentTimeMillis();
     //   try{
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
     //   }finally {
       //     long finish = System.currentTimeMillis();
         //   long timeMs = finish - start;
          //  System.out.println("join = ", timeMs + "ms");
      //  }
    }
        //같은 이름이 있는 중복회원은 안된다
        private void validateDuplicateMember(Member member); //중복회원 안되는 메소드 추출 ctrl + Alt + m
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers() {
       // long start = System.currentTimeMillis();
       // try {
            return memberRepository.findAll();
      //  }finally {
        //    long finish = System.currentTimeMillis();
         //   long timeMs = finish - start;
          //  System.out.println("findMembers " + timeMs +"ms");
        }
      //  return memberRepository.findAll();
    //}

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
