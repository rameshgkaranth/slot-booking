/**
 * 
 */
package com.redmart.slot.booking.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author rkaranth
 *
 */
@Component
@Scope(value = "prototype")
@PropertySource("classpath:slot-booking.properties")
public class Carton implements Cloneable {

	private int cartonId;
	
	@Value("${carton.height}")
	private double height;
	
	@Value("${carton.breadth}")
	private double breadth;
	
	@Value("${carton.width}")
	private double width;
	
	private double maxCapacityOfCarton;
	private double availableCapacityOfCarton;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Carton.class);
	
	/**
	 * @param cartonId
	 * @param height
	 * @param breadth
	 * @param width
	 */
	public Carton(int cartonId, double height, double breadth, double width) {
		this.height = height;
		this.breadth = breadth;
		this.width = width;
		this.cartonId = cartonId;
		this.maxCapacityOfCarton = height * breadth * width;
		this.availableCapacityOfCarton = height * breadth * width;
	}

	/**
	 * @return the cartonId
	 */
	public int getCartonId() {
		return cartonId;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the breadth
	 */
	public double getBreadth() {
		return breadth;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the availableCapacityOfCarton
	 */
	public double getAvailableCapacityOfCarton() {
		return availableCapacityOfCarton;
	}

	/**
	 * @return the maxCapacityOfCarton
	 */
	public double getMaxCapacityOfCarton() {
		return maxCapacityOfCarton;
	}
	
	/**
	 * @return the usedCapacityOfCarton
	 */
	public double getUsedCapacityOfCarton() {
		return this.maxCapacityOfCarton - this.availableCapacityOfCarton;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cartonId;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carton other = (Carton) obj;
		if (cartonId != other.cartonId)
			return false;
		return true;
	}
	
	/**
	 * Method that updated the available capacity of Carton after
	 * an Item has been added
	 * 
	 * @param volumeOfItem
	 */
	public void updateCartonCapacityAfterAddingItem(double volumeOfItem) {
		this.availableCapacityOfCarton -= volumeOfItem;
		LOGGER.info("Available capacity of Carton #:"+this.getCartonId()+" after adding item(s) is : "+getAvailableCapacityOfCarton());
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("Carton [cartonId=" + cartonId +", availableCapacityOfCarton="
				+ availableCapacityOfCarton + "]");
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
