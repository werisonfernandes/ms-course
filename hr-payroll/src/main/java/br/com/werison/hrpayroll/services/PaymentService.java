package br.com.werison.hrpayroll.services;

import org.springframework.stereotype.Service;

import br.com.werison.hrpayroll.entities.Payment;

@Service
public class PaymentService {

	public Payment getPayment(long workerId, int days) {
		return new Payment("Bob", 200.00, days);
	}
}
