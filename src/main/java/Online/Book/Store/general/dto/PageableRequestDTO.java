package Online.Book.Store.general.dto;

import lombok.Data;

@Data
public class PageableRequestDTO {
    private int size;
    private int page;
}
