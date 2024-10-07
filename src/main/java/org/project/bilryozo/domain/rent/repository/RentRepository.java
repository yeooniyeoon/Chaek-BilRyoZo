package org.project.bilryozo.domain.rent.repository;

import org.project.bilryozo.domain.rent.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

}
