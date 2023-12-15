package Online.Book.Store.util;

import Online.Book.Store.book.enums.GENRE;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Slf4j
public class GeneralUtil {

    public static boolean stringIsNullOrEmpty(String arg) {
        if ((arg == null)) return true;
        else
            return ("".equals(arg)) || (arg.trim().length() == 0);
    }

    public static String generateISBN() {

        StringBuilder sb = new StringBuilder();

        // Generate the first three digits
        sb.append(String.format("%03d", new Random().nextInt(900) + 100));

        // Add the first hyphen
        sb.append("-");

        // Generate the next five digits
        sb.append(String.format("%05d", new Random().nextInt(90000) + 10000));

        // Add the second hyphen
        sb.append("-");

        // Generate the next three digits
        sb.append(String.format("%03d", new Random().nextInt(900) + 100));

        // Add the third hyphen
        sb.append("-");

        // Generate the last digit
        sb.append(new Random().nextInt(11));

        return sb.toString();
    }

    public static String generateUniqueValue() {
        String ref = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        ref = ref.length() > 12 ? ref.substring(0, 14) : ref;

        ref = getString(ref);
        return ref;
    }

    private static String getString(String ref) {
        if (ref.length() < 14) {

            int checkLength = ref.length();

            StringBuilder refBuilder = new StringBuilder(ref);
            while (checkLength != 13) {
                refBuilder.append("0");
                ++checkLength;
            }
            ref = refBuilder.toString();
        }
        return ref;
    }

    public static String generateUniqueReferenceNumber(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String uniqueValue = dateFormat.format(date) + generateUniqueReferenceNumber();
        return uniqueValue.substring(0, 20);
    }

    public static String generateUniqueReferenceNumber() {
        log.info("Generating unique payment reference");
        String ref = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        ref = ref.length() > 12 ? ref.substring(0, 13) : ref;

        ref = getString(ref);
        return ref;
    }

    public static String generateUniqueISBNNumber() {
        Random random = new Random();

        // Generate random numbers for each segment
        int segment1 = random.nextInt(1000);
        int segment2 = random.nextInt(10);
        int segment3 = random.nextInt(100000);
        int segment4 = random.nextInt(1000);
        int segment5 = random.nextInt(10);

        // Format the segments into the desired pattern
        return String.format("%03d-%d-%05d-%03d-%02d", segment1, segment2, segment3, segment4, segment5);
    }

    public static GENRE getRandomGenre() {
        GENRE[] genres = GENRE.values();
        Random random = new Random();
        return genres[random.nextInt(genres.length)];
    }

    public static BigDecimal generateRandomAmount() {
        double minAmount = 10.0;
        double maxAmount = 50.0;
        return BigDecimal.valueOf(minAmount + Math.random() * (maxAmount - minAmount));
    }

    public static String generateRandomYear() {
        Random random = new Random();
        int minYear = 2000;
        int maxYear = 2023;
        return String.valueOf(minYear + random.nextInt(maxYear - minYear + 1));
    }


}
