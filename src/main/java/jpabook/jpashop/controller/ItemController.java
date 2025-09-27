package jpabook.jpashop.controller;

import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm.html";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        // createBook()을 쓰는게 더 나은 설계임
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList.html";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        // 캐스팅 하는 것은 안 좋은데 예제를 위해서 캐스팅을 함.
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm.html";
    }

    // 보안상 취약점이 매우 많음
    // 유저가 item에 관한 권한이 존재한지를 체크해주는 로직이 존재해야함.
    // itemId를 조작해버리면 큰일이 날 수 있다.
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, @PathVariable Long itemId) {

        // Book을 어설프게 만들어서 넘겼음
        // 대신에 다음과 같이 쓰자.
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        // 이제 이거를 더 하려면 DTO로 만들면 된다.

        // Controller 에서 어설프게 객체를 만들어서 쓰지 말자.
//        Book book = new Book();
//        book.setId(form.getId());
//        book.setIsbn(form.getIsbn());
//        book.setPrice(form.getPrice());
//        book.setName(form.getName());
//        book.setAuthor(form.getAuthor());
//        book.setStockQuantity(form.getStockQuantity());
//
//        itemService.saveItem(book); // book Entity가 넘어감/
        // saveItem을 호출하면 Transactional이 걸린 채로 itemRepository의 save를 호출
        // save 메서드를 보면 null일 때 persist, null이 아니면 merge함.
        return "redirect:/items";
    }

    // 변경 감지와 병합
    // 준영속 엔티티
    // 영속 상태로 관리가 되면 값만 바꾸면 JPA가 트랜잭션 커밋 시점에 변경된 내용을 알아가지고 DB에 반영

}
