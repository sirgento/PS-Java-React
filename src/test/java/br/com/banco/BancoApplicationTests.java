package br.com.banco;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.banco.conta.Conta;
import br.com.banco.conta.ContaService;
import br.com.banco.transferencia.Transferencia;
import br.com.banco.transferencia.TransferenciaService;

@SpringBootTest
@AutoConfigureMockMvc
class BancoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContaService contaService;

	@MockBean
	private TransferenciaService transferenciaService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetConta() throws Exception {

		int headerConta = 1;
		Conta conta = new Conta(1, "Sicrano");

		Mockito.when(contaService.getConta(headerConta)).thenReturn(Optional.of(conta));

		mockMvc.perform(MockMvcRequestBuilders.get("/conta/")
				.header("conta", String.valueOf(headerConta)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idConta").value(conta.getIdConta()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nomeResponsavel")
						.value(conta.getNomeResponsavel()));
	}

	@Test
	public void testGetSaldo() throws Exception {

		int headerConta = 1;
		BigDecimal saldo = new BigDecimal("33636.19").setScale(2);

		Mockito.when(contaService.getSaldo(headerConta)).thenReturn(saldo);

		mockMvc.perform(MockMvcRequestBuilders.get("/conta/saldo/")
				.header("conta", String.valueOf(headerConta)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("{\"saldo\": 33636.19}"));
	}

	@Test
	public void testGetTransferencias() throws Exception {
		int headerConta = 1;
		String nomeConta = "Fulano";
		Conta conta = new Conta(headerConta, nomeConta);
		List<Transferencia> transferencias = getListaTransferencias(conta);
		Mockito.when(transferenciaService.getTransferencias(Mockito.any(Transferencia.class)))
				.thenReturn(transferencias);

		mockMvc.perform(MockMvcRequestBuilders.get("/transferencia/")
				.header("conta", String.valueOf(headerConta)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(jsonString));
	}

	private List<Transferencia> getListaTransferencias(Conta conta) {
		List<Transferencia> listaRetorno = new ArrayList<Transferencia>();
		listaRetorno.add(new Transferencia(1, LocalDateTime.of(2020, 6, 8, 4, 15, 1, 0),
				BigDecimal.valueOf(3241.23).setScale(2), "SAQUE", "Beltrano", conta));
		listaRetorno.add(new Transferencia(3, LocalDateTime.of(2019, 5, 4, 2, 12, 45, 0),
				BigDecimal.valueOf(-500.50).setScale(2), "SAQUE", null, conta));
		listaRetorno.add(new Transferencia(5, LocalDateTime.of(2019, 1, 1, 8, 0, 0, 0),
				BigDecimal.valueOf(30895.46).setScale(2), "DEPOSITO", null, conta));
		return listaRetorno;
	}

	private String jsonString = "[{\"id\":1,\"dataTransferencia\":\"2020-06-08T04:15:01\",\"valor\":3241.23,\"tipo\":\"SAQUE\",\"nomeOperadorTransacao\":\"Beltrano\",\"conta\":{\"idConta\":1,\"nomeResponsavel\":\"Fulano\"}},{\"id\":3,\"dataTransferencia\":\"2019-05-04T02:12:45\",\"valor\":-500.50,\"tipo\":\"SAQUE\",\"nomeOperadorTransacao\":null,\"conta\":{\"idConta\":1,\"nomeResponsavel\":\"Fulano\"}},{\"id\":5,\"dataTransferencia\":\"2019-01-01T08:00:00\",\"valor\":30895.46,\"tipo\":\"DEPOSITO\",\"nomeOperadorTransacao\":null,\"conta\":{\"idConta\":1,\"nomeResponsavel\":\"Fulano\"}}]";
}
