/**
 * 
 */
package com.develop.rest.devops.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.develop.rest.devops.model.Request;
import com.develop.rest.devops.model.Response;

/**
 * @author dvasq
 *
 */
@RestController
@RequestMapping("/message")
public class SendController {

	/**
	 * Metodo para recibir un request mediante metodos rest con objeto de parameros
	 * @param request
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH}, 
			path = "/send", headers = "Accept=application/json", 
			consumes = {"application/JSON"})
	public @ResponseBody ResponseEntity<Object> getBodyRequestMessage(@RequestBody Request request, HttpServletRequest httpRequest) {
		if (RequestMethod.POST.name().equals(httpRequest.getMethod())) {
			Response response = new Response();
			response.setMessage("Hello "+request.getTo()+" your message will be send ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("ERROR", HttpStatus.OK);
		}
	}
	
	/**
	 * Metodo para recibir un request mediante metodos rest parametros simples
	 * @param message
	 * @param to
	 * @param from
	 * @param timeToLifeSec
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD}, 
			path = "/send", 
			produces = {"application/JSON"})
	public @ResponseBody ResponseEntity<String> getSimpleRequestMessage(@RequestParam("message") String message, @RequestParam("to")  String to, 
			@RequestParam("from") String from, @RequestParam("timeToLifeSec") Long timeToLifeSec ) {
		return new ResponseEntity<>("ERROR", HttpStatus.OK);
	}
	
	
}
