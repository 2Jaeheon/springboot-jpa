package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // id가 없으면 신규로 보고 persist 실행
        // id가 있으면 저장된 걸 수정한다고 보고 merge 실행
        if (item.getId() == null) {
            em.persist(item); // 만약 없으면 새로 생성한 객체임
        } else {
            em.merge(item); // update와 비슷한데 뒤에서 다시 다룸
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
            .getResultList();
    }
}
