package br.com.banco.conta;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ContaService {

    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }

    public Optional<Conta> getConta(Integer id) {
        return repository.findById(id);
    }

    public BigDecimal getSaldo(Integer idConta) {
        return repository.getSaldoConta(idConta);
    }
}
