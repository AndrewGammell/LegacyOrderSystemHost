package com.sogeti.command;

import java.util.Map;

/**
 * This is a standard java bean that is used to encapsulate the request data
 * being passed through the queue
 * 
 * @author agammell
 *
 */
public class Command {

	private queryQuantity quantity;
	private queryType type;
	private queryTable table;
	private String body;
	private Map<String, String> values;

	public enum queryQuantity {
		SINGLE, MULTIPLE
	}

	public enum queryType {
		GET, PUT, POST, DELETE
	}

	public enum queryTable {
		ORDERS, DETAILS, USERS
	}

	public void setTable(queryTable table) {
		this.table = table;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setType(queryType type) {
		this.type = type;
	}

	public void setQuantity(queryQuantity quantity) {
		this.quantity = quantity;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Command other = (Command) obj;
		if (quantity != other.quantity) {
			return false;
		}
		if (table != other.table) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Command [quantity=" + quantity + ", type=" + type + ", table=" + table + ", value=" + values + ", body="
				+ body + "]";
	}

}
