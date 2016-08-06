/**
 * 
 */
package com.redmart.slot.booking.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author rkaranth
 *
 */
@Component
@Scope(value = "prototype")
public class Van implements Cloneable {

	private int vanId;
	private List<Carton> cartons;
	
	/**
	 * @param noOfCartons
	 * @param cartons
	 */
	public Van(int vanId, List<Carton> cartons) {
		this.vanId = vanId;
		this.cartons = cartons;
	}

	/**
	 * @return the vanId
	 */
	public int getVanId() {
		return vanId;
	}

	/**
	 * @return the cartons
	 */
	public List<Carton> getCartons() {
		return cartons;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vanId;
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
		Van other = (Van) obj;
		if (vanId != other.vanId)
			return false;
		return true;
	}
	
	/**
	 * Method to get available or un-used capacity of Van
	 * 
	 * @return
	 * 		available or un-used capacity of Van as double
	 */
	public double getAvailableCapacityOfVan() {
		double availableCapacityOfVan = 0.0;
		
		for (Carton carton : this.cartons) {
			availableCapacityOfVan += carton.getAvailableCapacityOfCarton();
		}
		
		return availableCapacityOfVan;
	}
	
	/**
	 * Method to get maximum capacity of Van
	 * 
	 * @return
	 * 		maximum capacity of Van as double
	 */
	public double getMaximumCapacityOfVan() {
		double maximumCapacityOfVan = 0.0;
		
		for (Carton carton : this.cartons) {
			maximumCapacityOfVan += carton.getMaxCapacityOfCarton();
		}
		
		return maximumCapacityOfVan;
	}
	
	/**
	 * Method to get used capacity of Van
	 * 
	 * @return
	 * 		used capacity of Van as double
	 */
	public double getUsedCapacityOfVan() {
		return getMaximumCapacityOfVan() - getAvailableCapacityOfVan();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Van [vanId=" + vanId + "]");
		sb.append("Van capacity : ").append(getAvailableCapacityOfVan());
		
		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Van clonedVan = (Van) super.clone();
		List<Carton> clonedCartons = new ArrayList<>();
		for (Carton carton : clonedVan.cartons) {
			clonedCartons.add((Carton) carton.clone());
		}
		clonedVan.cartons = clonedCartons;
		return clonedVan;
	}
}
