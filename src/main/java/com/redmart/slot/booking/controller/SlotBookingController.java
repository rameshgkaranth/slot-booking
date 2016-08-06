/**
 * 
 */
package com.redmart.slot.booking.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redmart.slot.booking.exception.InvalidRequestException;
import com.redmart.slot.booking.exception.SlotCapacityOutOfBoundsException;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.OrderShipmentInfo;
import com.redmart.slot.booking.model.Slot;
import com.redmart.slot.booking.service.ISlotBookingService;
import com.redmart.slot.booking.validation.AvailabilityRequestValidator;
import com.redmart.slot.booking.validation.SlotBookingRequestValidator;
import com.redmart.slot.booking.vo.SlotAvailabilityResponse;
import com.redmart.slot.booking.vo.SlotBookingRequest;
import com.redmart.slot.booking.vo.SlotBookingResponse;
import com.redmart.slot.booking.vo.SlotVO;

/**
 * @author rkaranth
 *
 */
@Controller
public class SlotBookingController {
	
	@Autowired
	private ISlotBookingService slotBookingService;
	
	@Autowired
	private SlotBookingRequestValidator slotBookingRequestValidator;
	
	@Autowired
	private AvailabilityRequestValidator availabilityRequestValidator;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SlotBookingController.class);

	/**
	 * 
	 * @param slotBookingRequest
	 * @return
	 * 		SlotBookingResponse
	 */
	@RequestMapping(value = "/slot/book",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SlotBookingResponse bookSlotForOrder(@RequestBody SlotBookingRequest slotBookingRequest) {
		SlotBookingResponse slotBookingResponse = null;
		try {
			slotBookingRequestValidator.validateSlotBookingRequest(slotBookingRequest);
			Order order = slotBookingRequest.getOrder();
			Slot requiredSlot = slotBookingService.getSlotWithStartAndEndTime(slotBookingRequest.getSlot());
			OrderShipmentInfo orderShipmentInfo = slotBookingService.bookSlotForOrder(requiredSlot, order);
			
			LOGGER.debug("Order shipment info : "+orderShipmentInfo);
			
			StringBuilder sb = new StringBuilder();
			sb.append("Order #:").append(order.getOrderId()).append(" with ").append(order.getItems().size()).append(" items has been added to slot");
			slotBookingResponse = new SlotBookingResponse(sb.toString(), HttpStatus.CREATED);
			slotBookingResponse.setPayload(orderShipmentInfo);
		} catch (InvalidRequestException | SlotCapacityOutOfBoundsException e) {
			slotBookingResponse = new SlotBookingResponse(e.getMessage(), HttpStatus.OK);
		} catch (Exception e) {
			slotBookingResponse = new SlotBookingResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return slotBookingResponse;
	}
	
	
	/**
	 * 
	 * @param order
	 * @return
	 * 		SlotAvailabilityResponse
	 */
	@RequestMapping(value = "slot/avaialable",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SlotAvailabilityResponse getAvailableSlotForOrder(@RequestBody Order order) {
		SlotAvailabilityResponse availabilityResponse = null;
		List<Slot> availableSlots = null;
		List<SlotVO> availableSlotVOs = new ArrayList<>();
		try {
			availabilityRequestValidator.validateAvailabilityRequest(order);
			availableSlots = slotBookingService.getAvaialableSlotsForOrder(order);
			for (Slot slot : availableSlots) {
				SlotVO slotVO = new SlotVO(slot.getStartHour(), slot.getEndHour());
				availableSlotVOs.add(slotVO);
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("Order #:").append(order.getOrderId()).append(" has ").append(availableSlotVOs.size()).append(" available slots");
			availabilityResponse = new SlotAvailabilityResponse(sb.toString(), HttpStatus.OK);
			availabilityResponse.setPayload(availableSlotVOs);
		} catch (InvalidRequestException | SlotCapacityOutOfBoundsException e) {
			availabilityResponse = new SlotAvailabilityResponse(e.getMessage(), HttpStatus.OK);
		} catch (Exception e) {
			availabilityResponse = new SlotAvailabilityResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return availabilityResponse;
	}
}
