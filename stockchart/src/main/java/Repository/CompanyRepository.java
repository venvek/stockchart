package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jucha.stockchart.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
    Company findByTicker(String ticker);
}
