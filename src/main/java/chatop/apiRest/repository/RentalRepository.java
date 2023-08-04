package chatop.apiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chatop.apiRest.modele.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    
}
