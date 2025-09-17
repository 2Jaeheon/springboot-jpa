package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 내장타입 -> Embeddable (내장될 수 있다)
@Getter
// 값 타입은 변경 불가능하게 설계해야 한다
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA 스펙상 엔티티나 임베디드 타입(`@Embeddable` )은 자바 기본 생성자(default constructor)를 `public` 또는 `protected` 로 설정해야 한다.
    // `public`으로 두는 것 보다는 `protected`로 설정하는 것이 그나마 더 안전하다.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
