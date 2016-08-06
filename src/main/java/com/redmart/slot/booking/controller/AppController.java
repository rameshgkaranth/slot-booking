/**
 * 
 */
package com.redmart.slot.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rkaranth
 *
 */
@Controller
public class AppController {

	@RequestMapping(value = {"/", "/isWorking"}, method = RequestMethod.GET)
	public @ResponseBody String appDetails() {
		return "OK";
	}
}
