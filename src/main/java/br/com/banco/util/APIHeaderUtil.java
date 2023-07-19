package br.com.banco.util;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public class APIHeaderUtil {
    public static Integer getIntHeaderConta(HttpServletRequest request) throws Exception {
        Optional<String> headerConta = Optional.ofNullable(request.getHeader("conta"));
        if (headerConta.isPresent()) {
            return Integer.parseInt(headerConta.get());
        }
        throw new NullPointerException("Cabeçalho \"conta\" não encontrado");
    }
}
