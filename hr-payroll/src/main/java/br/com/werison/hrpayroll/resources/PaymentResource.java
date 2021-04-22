package br.com.werison.hrpayroll.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.werison.hrpayroll.entities.Payment;
import br.com.werison.hrpayroll.services.PaymentService;

@RestController
@RequestMapping(value="payments")
public class PaymentResource {

	@Autowired
	private PaymentService service;
	
	@GetMapping("/{workerId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable("workerId") long workerId, @PathVariable("workerId") int days) {
		Payment payment = service.getPayment(workerId, days);
		
		return ResponseEntity.ok(payment);
	}
}
