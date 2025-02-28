package petfriends.payment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petfriends.payment.model.Payment;
import petfriends.payment.model.Point;
import petfriends.payment.model.PointPayKind;
import petfriends.payment.repository.PaymentRepository;
import petfriends.payment.service.PaymentService;


 @RestController
 @RequestMapping("/")
 public class PaymentController {

	 @Autowired
	 PaymentService paymentService;


	 @GetMapping("/payments/pays/{userId}")
	 public List<Payment> findPaymentByUserId(@PathVariable("userId") String userId) {
	 	 List<Payment> list = paymentService.findAllByUserId(userId);
	 	 System.out.println("11================================================>");
	 	 System.out.println(list);
	 	 System.out.println("11================================================>");
	 	 return list;
		 //return paymentService.findAllByUserId(userId);
	 }
	 
	 @GetMapping("/payments/{id}")
	 public Payment findPaymentById(@PathVariable("id") Long id) {
		 return paymentService.findById(id);
	 }
	 
	 @PostMapping("/payments")
	 public Payment pay(@Valid @RequestBody Payment payment) {
		 return paymentService.pay(payment);
	 }
	 
	 @PutMapping("/payments/{reservedId}")
	 	 public Payment refund(@PathVariable Long reservedId) {
		 return paymentService.refund(reservedId);
	 }
	 
	 @GetMapping("/payments/points/{userId}")
	 public List<Point> findPointAllByUserId(@PathVariable("userId") String userId) {
		 return paymentService.findPointAllByUserId(userId);
	 }

	/*circuit breaker 테스트용*/
	 @GetMapping("/payments/sleep/{param}")
	 public String testSleep(@PathVariable("param") String param) throws InterruptedException {

		 Thread.sleep(10000000);

		 if(param.equals("fail"))
			 throw new RuntimeException("failed");

		 return "Payment - testSleep() ==> Success";
	 }


	 /*circuit breaker 테스트용*/
	 @GetMapping("/payments/message/{param}")
	 public String testException(@PathVariable("param") String param) throws InterruptedException {

		 if(param.equals("fail"))
			 throw new RuntimeException("failed");

		 return "Payment - testException() ==> Success";
	 }
	 /** 포인트 현금전환 : 
	   필수para : pointGubun : "ENCASH"
	           : bankName 
	           : accountNumber
	           : currentPoint //현재포인트
	           : point //현금전환할포인트
	  처리      : currentPoint = currentPoint - point 하여 INSERT      
     */        
	 @PostMapping("/points")
	 public Point changePoint(@Valid @RequestBody Point point) {
		 
		 if(point.getPointGubun() != null & PointPayKind.ENCASH.equals(point.getPointGubun())) {
			 if(point.getBankName() == null ) {
				 new RuntimeException( "은행명을 입력해주세요");
			 }
			 if(point.getAccountNumber() == null ) {
				 new RuntimeException( "계좌번호를 입력해주세요");
			 }
			 if(point.getPoint() > point.getCurrentPoint()) {
				 new RuntimeException( "남은 포인트가 부족합니다");
			 }
		 }
		 
		 return paymentService.changePoint(point);
	 }
	 
	 
 }

 
