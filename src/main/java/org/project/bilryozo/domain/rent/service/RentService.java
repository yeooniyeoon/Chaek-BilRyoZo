package org.project.bilryozo.domain.rent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bilryozo.domain.books.domain.Books;
import org.project.bilryozo.domain.books.exception.BookNotFoundException;
import org.project.bilryozo.domain.books.repository.BooksRepository;
import org.project.bilryozo.domain.rent.domain.Rent;
import org.project.bilryozo.domain.rent.exception.AlreadyReturnException;
import org.project.bilryozo.domain.rent.exception.BookAlreadyBorrowException;
import org.project.bilryozo.domain.rent.exception.InvalidRentAvailableDateException;
import org.project.bilryozo.domain.rent.exception.RentNotFoundException;
import org.project.bilryozo.domain.rent.repository.RentRepository;
import org.project.bilryozo.domain.users.domain.Users;
import org.project.bilryozo.domain.users.dto.response.MessageResponseDto;
import org.project.bilryozo.domain.users.exception.AccessForbiddenException;
import org.project.bilryozo.domain.users.repository.UsersRepository;
import org.project.bilryozo.domain.users.service.UsersService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
        book.updateRentCount();
        booksRepository.save(book);

        // user의 rentAvailableDate을 오늘 날짜 +7일로 설정
        Users user = usersRepository.findById(usersService.getAuthenticateduser().getId()).get();
        user.updateRentAvailableDate(LocalDate.now().plusDays(7));
        usersRepository.save(user);

        return new MessageResponseDto("대출이 완료되었습니다.");
    }

    public MessageResponseDto updateRent(Long id) {
        // id에 해당하는 대출건 존재 여부 확인
        Rent rent = rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);

        // 대출자와 반납 요청자가 동일한지 확인
        if (rent.getUserId().getId() != usersService.getAuthenticateduser().getId())
            throw new AccessForbiddenException();

        // 이미 반납이 된 경우 예외처리
        if (rent.getReturnDate() != null)
            throw new AlreadyReturnException();

        // 도서 존재 여부 확인
        Books book = booksRepository.findById(rent.getBookId().getId())
                .orElseThrow(BookNotFoundException::new);

        // returnDate를 현재 날짜로 변경하고, status를 true로 변경
        rent.updateRent();
        rentRepository.save(rent);

        // bookId에 해당하는 도서의 status를 true로 변경
        book.updateStatus(true);
        booksRepository.save(book);

        // 대출가능일을 반납 기간 보다 반납일이 이전인 경우 오늘로, 이후인 경우 연체일 이후로 설정
        Users user = usersRepository.findById(usersService.getAuthenticateduser().getId()).get();
        LocalDate dueReturnDate = rent.getRentDate().plusDays(7);
        LocalDate returnDate = rent.getReturnDate();

        if (returnDate.isAfter(dueReturnDate)) {
            long overdue = ChronoUnit.DAYS.between(returnDate, dueReturnDate);
            user.updateRentAvailableDate(returnDate.plusDays(overdue));
        }
        else {
            user.updateRentAvailableDate(LocalDate.now());
        }
        usersRepository.save(user);

        return new MessageResponseDto("반납이 완료되었습니다.");
    }
}