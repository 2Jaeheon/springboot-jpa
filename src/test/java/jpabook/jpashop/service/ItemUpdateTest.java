package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        book.setName("diofajweiofawe");

        // 변경 감지 == dirty checking
        // 기존의 엔티티의 값을 바꾸기만 하면 JPA가 트렌잭션 커밋 시점에 바뀐걸 찾아서
        // db 업데이트문을 날리고 트렌젝션 커밋.
        // flush 할 때 dirty checking이 일어남.

        // 준영속 엔티티
        // JPA의 영속성 컨텍스트가 관리하지 않는 것
        // ex_ updateItem을 보면 BookForm을 가져와서 book.setId(form.getId()) 와 같은 걸 쓰는데,
        // 이는 곧 JPA를 한 번 접근한것
        // new로 생성을 하기는 했지만, 이미 DB에 저장되고 불러온 애

        // 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준영속 엔티티
        // 이러한 준영속 엔티티는 JPA가 관리를 해주고있지 않음
        // Book에다가 값을 바꾸고 해도 JPA가 업데이트를 해주지 못함.
        // 1. 변경감지 (Dirty Checking)
        // 2. Merge



    }
}
