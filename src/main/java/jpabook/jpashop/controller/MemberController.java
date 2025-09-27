package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import java.util.List;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm.html";
    }

    @PostMapping("/members/new")
    // 모델을 직접 담지 말고 필요한것만 담도록 해서 Controller에서 처리를 해서 사용하도록 하자.
    public String create(@Valid MemberForm form, BindingResult result) {
        // BindingResult를 하면오류가 담겨서 실행이 됨. throw 하는 게 아니라

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // 이것도 Member를 그대로 List로 하는 것보다
        // DTO를 실무레벨에서는 사용하는게 좋다.
        // 여기서는 변경이 없이 출력만 하는 거니까 하는 것
        // 하지만 API를 만들때 엔티티를 절대 넘기지 마라.

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
