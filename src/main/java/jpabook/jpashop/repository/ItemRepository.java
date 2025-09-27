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
            // 영속성 컨텍스트에서 Db를 뒤져서 똑같은 식별자로 아이템을 찾음
            // 파라미터 값으로 모든 데이터를 바꿔치기를 해버림
            // 바꿔치기한 item을 반환함.
            // 여기 merge(item) 여기서의 item은 파라미터로 온거는 영속상태로 바뀌지는 않음

            // 병합을 쓰면 모든 속성이 다 변경되어버림
            // 병합시에 값이 없다면 null로 다 업데이트 해버릴 위험이 존재함.
            // 선택을 할수가 없음.
            // 기왕이면 변경감지를 써야한다
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
