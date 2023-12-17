package Online.Book.Store.startup;


import Online.Book.Store.book.model.Book;
import Online.Book.Store.book.repository.BookRepository;
import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.repository.CustomerRepository;
import Online.Book.Store.shoppingCart.model.ShoppingCart;
import Online.Book.Store.shoppingCart.repository.ShoppingCartRepository;
import Online.Book.Store.util.GeneralUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public SetupDataLoader(BookRepository bookRepository, CustomerRepository customerRepository, ShoppingCartRepository shoppingCartRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        //create books
        createMultipleBooks();

        // create customers
        createMultipleCustomers();

        alreadySetup = true;
    }

    @Transactional
    public void createMultipleBooks() {
        log.info("Request to create books");

        List<Book> bookList = generateBookList();  // Change the number based on how many books you want to create

        bookRepository.saveAll(bookList);

        log.info("Successfully created books");
    }

    private List<Book> generateBookList() {
        List<Book> bookList = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Book book = new Book();
            book.setTitle("Book " + i);
            book.setAuthor("Author " + i);
            book.setGenre(GeneralUtil.getRandomGenre());  // Use a method to get a random genre
            book.setPrice(GeneralUtil.generateRandomAmount());  // Use a method to generate a random amount
            book.setIsbnCode(GeneralUtil.generateUniqueISBNNumber());
            book.setPublicationYear(GeneralUtil.generateRandomYear());  // Use a method to generate a random year

            bookList.add(book);
        }

        return bookList;
    }

    @Transactional
    public void createMultipleCustomers() {
        log.info("Request to create customers");

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCartRepository.save(shoppingCart);

        Customer customer = new Customer();
        customer.setFirstName("Ola");
        customer.setLastName("Paul");
        customer.setEmail("olapaul@gmail.com");
        customer.setShoppingCart(shoppingCart);

        // Save the customers to the database
        customerRepository.save(customer);
        log.info("successfully created customers");
    }

}