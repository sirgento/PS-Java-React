package br.com.banco.conta;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.util.APIHeaderUtil;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Conta> getConta(HttpServletRequest request) throws Exception {
        return ResponseEntity.of(service.getConta(APIHeaderUtil.getIntHeaderConta(request)));
    }

    @GetMapping("/saldo/")
    public ResponseEntity<String> getSaldo(HttpServletRequest request) throws Exception {
        return ResponseEntity.of(Optional.of(
                "{\"saldo\": " + service.getSaldo(APIHeaderUtil.getIntHeaderConta(request)).setScale(2).toString()
                        + "}"));
    }
}
