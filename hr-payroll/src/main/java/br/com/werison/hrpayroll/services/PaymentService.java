package br.com.werison.hrpayroll.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.werison.hrpayroll.entities.Payment;
import br.com.werison.hrpayroll.entities.Worker;
import br.com.werison.hrpayroll.feignclients.WorkerFeignClient;

@Service
public class PaymentService {

	/*
	 * @Value("${hr-worker.host}") private String workerHost;
	 * 
	 * @Autowired private RestTemplate restTemplate;
	 */

	@Autowired
	private WorkerFeignClient workerFeignClient;

	public Payment getPayment(long workerId, int days) {

		/*
		 * Map<String, Object> uriVariables = new HashMap<>(); uriVariables.put("id",
		 * workerId);
		 * 
		 * Worker worker = restTemplate.getForObject(workerHost + "/workers/{id}",
		 * Worker.class, uriVariables);
		 */

		Optional<Worker> worker = workerFeignClient.findById(workerId).getBody();

		return new Payment(worker.get().getName(), worker.get().getDailyIncome(), days);
	}
}
