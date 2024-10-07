package org.project.bilryozo.domain.rent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bilryozo.domain.books.domain.Books;
import org.project.bilryozo.domain.books.exception.BookNotFoundException;
import org.project.bilryozo.domain.books.repository.BooksRepository;
import org.project.bilryozo.domain.rent.domain.Rent;
import org.project.bilryozo.domain.rent.exception.BookAlreadyBorrowException;
import org.project.bilryozo.domain.rent.exception.InvalidRentAvailableDateException;
import org.project.bilryozo.domain.rent.repository.RentRepository;
import org.project.bilryozo.domain.users.domain.Users;
import org.project.bilryozo.domain.users.dto.response.MessageResponseDto;
import org.project.bilryozo.domain.users.repository.UsersRepository;
import org.project.bilryozo.domain.users.service.UsersService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentService {
    private final RentRepository rentRepository;
    private final UsersService usersService;
    private final BooksRepository booksRepository;
    private final UsersRepository usersRepository;

    public MessageResponseDto createRent(Long bookId) {
        // 대출 가능일이 오늘보다 이전인지 확인
        LocalDate rentAvailableDate = usersService.getAuthenticateduser().getRentAvailableDate();
        if (!rentAvailableDate.isBefore(LocalDate.now().plusDays(1)))
            throw new InvalidRentAvailableDateException();

        // 전달받은 bookId 존재 여부 확인
        Books book = booksRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        // 책 대출 가능 여부 확인
        if (!book.getStatus())
            throw new BookAlreadyBorrowException();

        // rentDate 오늘 날짜로 설정
        rentRepository.save(Rent.builder()
                .userId(usersService.getAuthenticateduser())
                .bookId(book)
                .rentDate(LocalDate.now())
                .status(false)
                .build());

        // book.status false로 설정하여 대출 안되도록 설정
        book.updateStatus(false);
        booksRepository.save(book);

        // user의 rentAvailableDate을 오늘 날짜 +7일로 설정
        Users user = usersRepository.findById(usersService.getAuthenticateduser().getId()).get();
        user.updateRentAvailableDate(LocalDate.now().plusDays(7));
        usersRepository.save(user);

        return new MessageResponseDto("대출이 완료되었습니다.");
    }
}