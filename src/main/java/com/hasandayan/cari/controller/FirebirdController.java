package com.hasandayan.cari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hasandayan.cari.service.FirebirdService;

@Controller
public class FirebirdController {
	
	@Autowired
	private FirebirdService firebirdService;
	
	@GetMapping("/companyTransfer")
	public String companyTransfer(@RequestParam("path") String path) {
		
		firebirdService.companyTransfer(path);
		
		return "company-transfer";
	}
	
	@GetMapping("/movementTransfer")
	public String movementTransfer(@RequestParam("path") String path) {
		
		firebirdService.movementTransfer(path);
		
		return "movement-transfer";
	}

}
