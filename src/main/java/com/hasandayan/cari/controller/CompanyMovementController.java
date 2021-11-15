package com.hasandayan.cari.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.entity.CompanyMovement;
import com.hasandayan.cari.enums.MovementMode;
import com.hasandayan.cari.enums.MovementType;
import com.hasandayan.cari.pojo.CompanyDTO;
import com.hasandayan.cari.pojo.CompanyMovementDTO;
import com.hasandayan.cari.service.CompanyMovementService;
import com.hasandayan.cari.service.CompanyService;
import com.hasandayan.cari.util.Base64Util;
import com.hasandayan.cari.util.DataUtil;

@Controller
public class CompanyMovementController {

	private static final String REDIRECT_ATTR_NAME = "message";

	@Autowired
	private CompanyMovementService companyMovementService;

	@Autowired
	private CompanyService companyService;

	@GetMapping("/cariHesapHareketleri")
	public String getCompanyMovements(@RequestParam("cariHesapId") String cariHesapId, Model model) {

		model.addAttribute("companyMovements", companyMovementService.getCompanyMovementDTOs(cariHesapId));

		CompanyDTO companyDTO = companyService.getCompanyDTO(Base64Util.decode(cariHesapId));

		model.addAttribute("company", companyDTO);

		return "company-movements";
	}

	@GetMapping("/cariHareketEkle")
	public String getAddCompanyMovements(@RequestParam("cariHesapId") String cariHesapId, Model model) {

		if (Objects.isNull(model.getAttribute("movement"))) {
			CompanyMovementDTO companyMovementDTO = new CompanyMovementDTO();
			companyMovementDTO.setCompanyId(cariHesapId);
			model.addAttribute("movement", companyMovementDTO);
			
			List<String> units = companyMovementService.getUnits();
			model.addAttribute("units", units);
			
			List<String> types = companyMovementService.getProductTypes();
			model.addAttribute("types", types);
		}

		return "add-movement";
	}
	
	@PostMapping("/cariHareketEkle")
	public String postAddCompanyMovements(@ModelAttribute("movement") CompanyMovementDTO movementDTO,
			RedirectAttributes redirectAttributes) {

		try {

			CompanyMovement movement = new CompanyMovement();

			movement.setMovementAmount(movementDTO.getAmount());
			movement.setMovementDescription(movementDTO.getDesc());
			movement.setProductType(movementDTO.getProductType());
			
			if(DataUtil.nonNull(movementDTO.getMode()))
				movement.setMovementMode(MovementMode.valueOf(movementDTO.getMode()));
			
			if(DataUtil.nonNull(movementDTO.getType()))
				movement.setMovementType(MovementType.valueOf(movementDTO.getType()));
			
			movement.setProcessDate(LocalDate.parse(movementDTO.getDate()));
			
			movement.setQuantity(movementDTO.getQuantity());
			movement.setUnit(movementDTO.getUnit());
			movement.setUnitPrice(movementDTO.getUnitPrice());

			Company company = companyService.findById(Base64Util.decode(movementDTO.getCompanyId()));
			movement.setCompany(company);

			companyMovementService.save(movement);

			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Failed");
			redirectAttributes.addFlashAttribute("movement", movementDTO);
			e.printStackTrace();
			
			return "redirect:/cariHareketEkle?cariHesapId=" + movementDTO.getCompanyId();
		}

		return "redirect:/cariHesapHareketleri?cariHesapId=" + movementDTO.getCompanyId();
	}

	@GetMapping("/cariHareketSil")
	public String getDeleteCompanyMovement(@RequestParam("cariHesapId") String cariHesapId,
			@RequestParam("encId") String encId) {

		Long id = Base64Util.decode(encId);

		companyMovementService.delete(id);

		return "redirect:/cariHesapHareketleri?cariHesapId=" + cariHesapId;
	}

	@GetMapping("/cariHareketDuzenle")
	public String getEditCompanyMovement(@RequestParam("cariHesapId") String cariHesapId,
			@RequestParam("encId") String encId, Model model) {

		if (Objects.isNull(model.getAttribute("movement"))) {

			Long id = Base64Util.decode(encId);

			CompanyMovement movement = companyMovementService.findById(id);

			CompanyMovementDTO movementDTO = new CompanyMovementDTO();

			movementDTO.setId(encId);

			if (Objects.nonNull(movement.getMovementMode()))
				movementDTO.setMode(movement.getMovementMode().name());

			if (Objects.nonNull(movement.getMovementType()))
				movementDTO.setType(movement.getMovementType().name());

			movementDTO.setDesc(movement.getMovementDescription());
			movementDTO.setProductType(movement.getProductType());
			movementDTO.setCompanyId(cariHesapId);
			movementDTO.setAmount(movement.getMovementAmount());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			movementDTO.setDate(formatter.format(movement.getProcessDate()));

			movementDTO.setQuantity(movement.getQuantity());
			movementDTO.setUnit(movement.getUnit());
			movementDTO.setUnitPrice(movement.getUnitPrice());

			model.addAttribute("movement", movementDTO);
			
			List<String> units = companyMovementService.getUnits();
			model.addAttribute("units", units);
			
			List<String> types = companyMovementService.getProductTypes();
			model.addAttribute("types", types);
		}

		return "edit-movement";
	}

	@PostMapping("/cariHareketDuzenle")
	public String postEditCompanyMovement(@ModelAttribute("movement") CompanyMovementDTO movementDTO,
			RedirectAttributes redirectAttributes) {

		try {
			CompanyMovement movement = new CompanyMovement();

			Long id = Base64Util.decode(movementDTO.getId());
			movement.setId(id);

			if (DataUtil.nonNull(movementDTO.getMode()))
				movement.setMovementMode(MovementMode.valueOf(movementDTO.getMode()));

			if (DataUtil.nonNull(movementDTO.getType()))
				movement.setMovementType(MovementType.valueOf(movementDTO.getType()));

			movement.setMovementDescription(movementDTO.getDesc());
			movement.setProductType(movementDTO.getProductType());
			
			Long companyId = Base64Util.decode(movementDTO.getCompanyId());
			movement.setCompany(companyService.findById(companyId));
			
			movement.setMovementAmount(movementDTO.getAmount());

			movement.setProcessDate(LocalDate.parse(movementDTO.getDate()));
			
			movement.setQuantity(movementDTO.getQuantity());
			movement.setUnit(movementDTO.getUnit());
			movement.setUnitPrice(movementDTO.getUnitPrice());
			
			companyMovementService.save(movement);

			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(REDIRECT_ATTR_NAME, "Failed");
			redirectAttributes.addFlashAttribute("movements", movementDTO);
			e.printStackTrace();
			
			return "redirect:/cariHareketDuzenle?cariHesapId=" + movementDTO.getCompanyId() + "&encId=" + movementDTO.getId();
		}

		return "redirect:/cariHesapHareketleri?cariHesapId=" + movementDTO.getCompanyId();
	}
	
	@GetMapping("/cariHareketler")
	public String getAllMovements(Model model) {

		model.addAttribute("movementsActive", true);
		model.addAttribute("movements", companyMovementService.getCompanyMovementDTOs(LocalDate.now()));

		return "all-movements";
	}

}
