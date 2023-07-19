package br.com.banco.conta;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
    
    @Query(value = "SELECT SUM (valor) FROM transferencia WHERE conta_id = :conta_id", nativeQuery = true)
    BigDecimal getSaldoConta(@Param("conta_id") Integer idConta);
}
