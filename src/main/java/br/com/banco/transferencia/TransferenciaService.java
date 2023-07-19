package br.com.banco.transferencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {

    private final TransferenciaRepository repository;
    private final TransferenciaRepositoryCustom repositoryCustom;

    public TransferenciaService(TransferenciaRepository repository, TransferenciaRepositoryCustom repositoryCustom) {
        this.repository = repository;
        this.repositoryCustom = repositoryCustom;
    }

    public List<Transferencia> getTransferencias(Transferencia filtroTransferencia) {
        List<Transferencia> lista = repository.findAll(Example.of(filtroTransferencia, ExampleMatcher.matchingAny()),
                Sort.by(Direction.DESC, "id"));
        return (lista.isEmpty() ? null : lista);
    }

    public List<Transferencia> getTransferenciasComFiltro(Transferencia filtro, String dataInicioString,
            String dataFimString) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return repositoryCustom.getTransferenciasComFiltro(filtro,
                ((null == dataInicioString || "".equals(dataInicioString)) ? null : LocalDate.from(dtf.parse(dataInicioString)).atStartOfDay()),
                ((null == dataFimString || "".equals(dataFimString)) ? null : LocalDate.from(dtf.parse(dataFimString)).atStartOfDay()));
    }
}
