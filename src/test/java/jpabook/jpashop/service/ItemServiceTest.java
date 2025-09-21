package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void 아이템_저장() throws Exception {
        // given
        Item item = new Album();
        item.setName("앨범1");
        itemService.saveItem(item);

        // when
        List<Item> all = itemRepository.findAll();

        // then
        assertEquals(1, all.size());
        assertEquals("앨범1", all.get(0).getName());
        assertTrue(all.get(0) instanceof Album);
    }

    @Test
    void 아이템_조회() throws Exception {
        //given
        Item item = new Book();
        item.setName("책1");
        itemService.saveItem(item);

        //when
        Item one = itemRepository.findOne(item.getId());

        // then
        assertEquals(item.getId(), one.getId());
        assertEquals("책1", one.getName());
    }
}