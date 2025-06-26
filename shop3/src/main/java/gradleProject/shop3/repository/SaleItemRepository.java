package gradleProject.shop3.repository;

import gradleProject.shop3.domain.SaleItem;
import gradleProject.shop3.domain.SaleItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, SaleItemId> {

}
