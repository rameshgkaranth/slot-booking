package com.redmart.slot.booking.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 
 * @author rkaranth
 *
 */
@Configuration
@PropertySource("classpath:slot-booking.properties")
public class SlotBookingConfig {

	@Value("${no.of.cartons.per.van}")
	private int noOfCartons;
	
	@Value("${no.of.vans}")
	private int noOfVans;
	
	@Value("${slot.start.hour.of.day}")
	private int startHour;
	
	@Value("${slot.end.hour.of.day}")
	private int endHour;
	
	@Value("${slot.inactive.start.hour.of.day}")
	private int inactiveStartHour;
	
	@Value("${slot.inactive.end.hour.of.day}")
	private int inactiveEndHour;
	
	@Value("${slot.duration}")
	private int slotDuration;
	
	@Value("${carton.height}")
	private double height;
	
	@Value("${carton.breadth}")
	private double breadth;
	
	@Value("${carton.width}")
	private double width;

	@Bean
	public List<Slot> slots() {
		List<Slot> slotList = new ArrayList<>();
		int slotStart = 0;
		int slotEnd = 0;
		int slotCounter = 1;
		
		while (slotEnd < endHour) {
			if (slotEnd == inactiveStartHour) {
				slotStart = inactiveEndHour;
			} else {
				slotStart = (slotStart == 0) ? startHour : slotEnd;
			}
			
			slotEnd = slotStart + slotDuration;
			List<Van> vans = vans();
			Slot slot = new Slot(slotCounter++, slotStart, slotEnd, vans);
			slotList.add(slot);
		}
		
		return slotList;
	}
	
	public List<Van> vans() {
		List<Van> vanList = new ArrayList<>();
		
		for (int i = 1; i <= noOfVans; i++) {
			List<Carton> cartons = cartons();
			Van van = new Van(i, cartons);
			vanList.add(van);
		}
		
		return vanList;
	}
	
	public List<Carton> cartons() {
		List<Carton> cartonList = new ArrayList<>();
		
		for (int i = 1; i <= noOfCartons; i++) {
			cartonList.add(new Carton(i, height, breadth, width));
		}
		
		return cartonList;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * @return the startHour
	 */
	public int getStartHour() {
		return startHour;
	}

	/**
	 * @return the endHour
	 */
	public int getEndHour() {
		return endHour;
	}

	/**
	 * @return the inactiveStartHour
	 */
	public int getInactiveStartHour() {
		return inactiveStartHour;
	}

	/**
	 * @return the inactiveEndHour
	 */
	public int getInactiveEndHour() {
		return inactiveEndHour;
	}

	/**
	 * @return the slotDuration
	 */
	public int getSlotDuration() {
		return slotDuration;
	}
}
