package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 변경 감지(Dirty Checking)
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        // findItem은 영속 상태임.
        // 그러면 값을 세팅하면 알아서 다 Transactinal에 의해서 commit을 하고
        // flush를 해서 변경 사항을 모두 찾고 업데이트 쿼리를 날려서 업데이트를 함.
        Item findItem = itemRepository.findOne(itemId);

        // 원래는 의미있는 비즈니스 로직을 만들어서 써야함.
        // findItem.change(price, name, stockQuantity);
        // findItem.addStockQuantity()

        // set은 실제로 쓰지 말자!
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        // 호출할 필요 없음
        // itemRepository.save(findItem);
    }

    // 병합: 준영속 상태의 엔티티를 영속 상태로 변경

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
