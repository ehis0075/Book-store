package Online.Book.Store;

import Online.Book.Store.util.GeneralUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookStoreApplication.class, args);

//		String randomNumber = GeneralUtil.generateISBN();
//		System.out.println(randomNumber);
	}

}
