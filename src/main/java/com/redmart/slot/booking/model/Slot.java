/**
 * 
 */
package com.redmart.slot.booking.model;

import java.util.List;


/**
 * @author rkaranth
 *
 */
public class Slot {

	private int slotId;
	private int startHour;
	private int endHour;
	
	private List<Van> vans;
	
	/**
	 * @param startHour
	 * @param endHour
	 */
	public Slot(int slotId, int startHour, int endHour, List<Van> vans) {
		this.slotId = slotId;
		this.startHour = startHour;
		this.endHour = endHour;
		this.vans = vans;
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
	 * @return the vans
	 */
	public List<Van> getVans() {
		return vans;
	}
	
	/**
	 * @return the slotId
	 */
	public int getSlotId() {
		return slotId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + slotId;
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
		Slot other = (Slot) obj;
		if (slotId != other.slotId)
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Slot [slotId=" + slotId + ", startHour=" + startHour + ", endHour=" + endHour + "]");
		sb.append("Vans : ");
		for (Van van : vans) {
			sb.append(van);
		}
		
		return sb.toString();
	}
	
	
}
