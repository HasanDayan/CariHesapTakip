package com.hasandayan.cari.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.enums.CompanyGroup;
import com.hasandayan.cari.pojo.CompanyDTO;
import com.hasandayan.cari.service.CompanyService;
import com.hasandayan.cari.util.Base64Util;
import com.hasandayan.cari.util.DataUtil;

@Controller
public class CompanyController {

	private static final String REDIRECT_ATTR_NAME = "message";
	
	@Autowired
	private CompanyService companyService;

	@GetMapping("/cariHesaplar")
	public String companyList(Model model) {

		model.addAttribute("companies", companyService.getCompanyDTOs());
		model.addAttribute("companiesActive", true);

		return "companies";
	}

	@GetMapping("/cariHesapEkle")
	public String getAddCompany(Model model) {

		if(model.getAttribute("company") == null)
			model.addAttribute("company", new CompanyDTO());

		return "add-company";
	}

	@PostMapping("/cariHesapEkle")
	public String postAddCompany(@ModelAttribute("company") CompanyDTO companyDTO,
			RedirectAttributes redirectAttributes) {

		try {
			Company company = new Company();
			
			company.setCompanyName(companyDTO.getName());
			company.setCompanyOfficial(companyDTO.getOfficial());
			company.setOtherInfo(companyDTO.getOtherInfo());
			
			if (DataUtil.nonNull(companyDTO.getGroup()))
				company.setCompanyGroup(CompanyGroup.valueOf(companyDTO.getGroup()));
			
			companyService.save(company);
			
			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Failed");
			redirectAttributes.addFlashAttribute("company", companyDTO);
			e.printStackTrace();
			
			return "redirect:/cariHesapEkle";
		}
		
		return "redirect:/cariHesaplar";
	}

	@GetMapping("/cariHesapSil/{encid}")
	public String getDeleteCompany(@PathVariable("encid") String encid) {
		
		Long id = Base64Util.decode(encid);
		
		companyService.delete(id);
		
		return "redirect:/cariHesaplar";
	}
	
	@GetMapping("/cariHesapDuzenle/{encid}")
	public String getEditCompany(@PathVariable("encid") String encid, Model model) {
		
		if(Objects.isNull(model.getAttribute("company"))) {
			
			Long id = Base64Util.decode(encid);
			
			Company company = companyService.findById(id);
			
			CompanyDTO companyDTO = new CompanyDTO();
			
			companyDTO.setId(Base64Util.encode(id));
			companyDTO.setName(company.getCompanyName());
			companyDTO.setOfficial(company.getCompanyOfficial());
			companyDTO.setOtherInfo(company.getOtherInfo());
			
			if(Objects.nonNull(company.getCompanyGroup()))
				companyDTO.setGroup(company.getCompanyGroup().name());
			
			model.addAttribute("company", companyDTO);
		}

		return "edit-company";
	}
	
	@PostMapping("/cariHesapDuzenle")
	public String postEditCompany(@ModelAttribute("company") CompanyDTO companyDTO,
			RedirectAttributes redirectAttributes) {

		try {
			Company company = new Company();
			
			Long id = Base64Util.decode(companyDTO.getId());
			
			company.setId(id);
			company.setCompanyName(companyDTO.getName());
			company.setCompanyOfficial(companyDTO.getOfficial());
			company.setOtherInfo(companyDTO.getOtherInfo());
			
			if (DataUtil.nonNull(companyDTO.getGroup()))
				company.setCompanyGroup(CompanyGroup.valueOf(companyDTO.getGroup()));
			
			companyService.save(company);
			
			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Failed");
			redirectAttributes.addFlashAttribute("company", companyDTO);
			e.printStackTrace();
			
			return "redirect:/cariHesapDuzenle/" + companyDTO.getId();
		}
		
		return "redirect:/cariHesaplar";
	}
}
