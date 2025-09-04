package config;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jucha.stockchart.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
