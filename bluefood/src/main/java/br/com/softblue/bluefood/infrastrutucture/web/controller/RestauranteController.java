package br.com.softblue.bluefood.infrastrutucture.web.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.softblue.bluefood.application.service.ValidationException;
import br.com.softblue.bluefood.domain.pedido.ItemPedidoRepository;
import br.com.softblue.bluefood.domain.pedido.Pedido;
import br.com.softblue.bluefood.domain.pedido.PedidoRepository;
import br.com.softblue.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.softblue.bluefood.domain.restaurante.ItemCardapio;
import br.com.softblue.bluefood.domain.restaurante.ItemCardapioRepository;
import br.com.softblue.bluefood.domain.restaurante.Restaurante;
import br.com.softblue.bluefood.domain.restaurante.RestauranteRepository;
import br.com.softblue.bluefood.domain.restaurante.RestauranteService;
import br.com.softblue.bluefood.util.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private  ItemCardapioRepository itemCardapioRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("/home")
	public String home(Model model) {
		
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		List<Pedido> pedidos = pedidoRepository.findByRestauranteId_OrderByDataDesc(restauranteId);
		model.addAttribute(pedidos);
		
		return "restaurante-home";
	}
	
	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping("/edit")
	public String edit(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);
		model.addAttribute("restaurante", restaurante);
		
		ControllerHelper.setEditModel(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);

		return "restaurante-cadastro";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("restaurante") @Valid Restaurante restaurante, 
			Errors errors, 
			Model model) {

		if (!errors.hasErrors()) {
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante gravado com sucesso!");

			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}

		ControllerHelper.setEditModel(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@GetMapping(path = "/comidas")
	public String viewComidas(Model model){
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);
		model.addAttribute("restaurante", restaurante);
		
		List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		model.addAttribute("itensCardapio",itensCardapio);
		model.addAttribute("itemCardapio",new ItemCardapio());
		
		return "restaurante-comidas";
	}
	
	
	@GetMapping(path = "comidas/remover")
	public String remover(@RequestParam("itemId") Integer itemId,Model model){
		itemCardapioRepository.deleteById(itemId);
		
		return "redirect:/restaurante/comidas";
		
	}
	
	
	
	@PostMapping(path = "/comidas/cadastrar")
	public String cadastrar1(
			@Valid @ModelAttribute("itemCardapio") ItemCardapio itemCardapio,
			Errors errors,
			Model model) {
		
		if (errors.hasErrors()) {
			Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
			Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);
			model.addAttribute("restaurante", restaurante);
			
			List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
			model.addAttribute("itensCardapio", itensCardapio);
			
			return "restaurante-comidas";
		}
		
		restauranteService.saveItemItemCardapio(itemCardapio);
		return "redirect:/restaurante/comidas";
	}
	
}
