/**
 * 
 */
package com.redmart.slot.booking.model;

/**
 * @author rkaranth
 *
 */
public class Item {

	private int itemId;
	private double height;
	private double breadth;
	private double width;
	
	public Item() {
		
	}
	
	public Item (int itemId, double height, double breadth, double width) {
		this.itemId = itemId;
		this.height = height;
		this.breadth = breadth;
		this.width = width;
	}
	
	/**
	 * Method to get volume of Item
	 * @return
	 * 		Volume of Item as double
	 */
	public double getVoulmeOfItem() {
		return this.breadth * this.height * this.width;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the breadth
	 */
	public double getBreadth() {
		return breadth;
	}

	/**
	 * @param breadth the breadth to set
	 */
	public void setBreadth(double breadth) {
		this.breadth = breadth;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemId;
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
		Item other = (Item) obj;
		if (itemId != other.itemId)
			return false;
		return true;
	}
}
