package br.com.banco.transferencia;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.conta.Conta;
import br.com.banco.util.APIHeaderUtil;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {

    private final TransferenciaService service;

    public TransferenciaController(TransferenciaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll(HttpServletRequest request,
            @RequestParam(name = "nomeOperador", required = false) String nomeOperador,
            @RequestParam(name = "dataInicio", required = false) String dataInicioString,
            @RequestParam(name = "dataFim", required = false) String dataFimString) throws Exception {
        Transferencia filtro = new Transferencia(nomeOperador, new Conta(APIHeaderUtil.getIntHeaderConta(request)));

        // se todos for nulo ou "", puxa todos
        if ((null == nomeOperador || "".equals(nomeOperador))
                && (null == dataFimString || "".equals(dataFimString))
                && (null == dataInicioString || "".equals(dataInicioString))) {
            return ResponseEntity.of(
                    Optional.ofNullable(service.getTransferencias(filtro)));
        }
        return ResponseEntity
                .of(Optional.ofNullable(service.getTransferenciasComFiltro(filtro, dataInicioString, dataFimString)));
    }
}
