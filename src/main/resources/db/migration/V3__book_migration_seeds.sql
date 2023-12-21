-- INSERT INTO public.book(
--     id, genre, isbn_code, price, publication_year, stock_count, title, author_id)
-- VALUES (3, 'POETRY', '1237-05', '20.00','2020', '60', 'Book 2', 2);
-- VALUES (4, 'POETRY', '1237-05', '20.00','2020', '60', 'Book 3', 3);
-- VALUES (5, 'POETRY', '1237-05', '20.00','2020', '60', 'Book 4', 4);
-- VALUES (6, 'POETRY', '1237-05', '20.00','2020', '60', 'Book 5', 5);



INSERT INTO public.book(
    price, stock_count, author_id, id, genre, isbn_code, publication_year, title)
VALUES ('20.00', '60', 2, 2, 'POETRY', '1267-05', '2001', 'Book 2');