package com.example.restservice;

// import Calculator;
import com.example.restservice.Calculator;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	//private static final String template = "Result, %s!";
	private final AtomicLong counter = new AtomicLong();

	/*
	 * If you call this endpoint with the URL "/example?param1=2+2", 
	 * the value of param1 will be "2+2", and you can use it as is in your code. 
	 * If you call the endpoint with the URL "/example?param1=2%2B2", 
	 * the value of param1 will be "2+2", because the plus sign is URL-encoded as "%2B".
	 */
	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "2+2") String name) {
		
		Calculator calc = new Calculator();
		try {
			name = Double.toString(calc.evaluate(name));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Greeting(counter.incrementAndGet(), String.format(name));
	}
}
