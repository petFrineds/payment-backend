package petfriends.payment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petfriends.payment.model.Payment;
import petfriends.payment.model.Point;
import petfriends.payment.service.PaymentService;
import petfriends.payment.service.PointService;

 @RestController
 @RequestMapping("/")
 public class PaymentController {

	 @Autowired
	 PaymentService paymentService;
	 
	 @Autowired
	 PointService pointService;
	 
	 @GetMapping("/payments/users/{userId}")
	 public List<Payment> findPaymentByUserId(@PathVariable("userId") String userId) {
		 return paymentService.findAllByUserId(userId);
	 }
	 
	 @PostMapping("/payments")
	 public Payment pay(@Valid @RequestBody Payment payment) {
		 return paymentService.pay(payment);
	 }
	 
	 @PutMapping("/payments/{id}")
	 	 public Payment refund(@PathVariable Long id) {
		 return paymentService.refund(id);
	 }
	 
	 @GetMapping("/points/users/{userId}")
	 public List<Point> findPointsByUserId(@PathVariable("userId") String userId) {
		 return pointService.findAllByUserId(userId);
	 }
	 
	 
 }

 