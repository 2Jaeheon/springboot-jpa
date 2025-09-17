package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // order 테이블에 있는 member필드에 의해서 매핑
    // => 읽기 전용이 됨
    private List<Order> orders = new ArrayList<>();
    // Order와 Member의 연관관계에서 둘 중 하나만 주인으로 잡아서 사용해야함
    // 객체는 변경포인트가 두 군데이지만 DB에서는 FK만 변경하면 되는것.
    // 따라서 하나만 주인으로 잡으면 됨.
}
