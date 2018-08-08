package com.example.e4.rcp.todo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

public class Todo {

	private PropertyChangeSupport	changes				= new PropertyChangeSupport(this);

	public static final String		FIELD_ID			= "id";
	public static final String		FIELD_SUMMARY		= "summary";
	public static final String		FIELD_DESCRIPTION	= "description";
	public static final String		FIELD_DONE			= "done";
	public static final String		FIELD_DUEDATE		= "dueDate";

	private final long				id;
	private String					summary				= "";
	private String					description			= "";
	private boolean					done				= false;
	private Date					dueDate;

	public Todo(long id) {
		super();
		this.id = id;
	}

	public Todo(long id, String summary, String description, boolean done, Date dueDate) {
		super();
		this.id = id;
		this.summary = summary;
		this.description = description;
		this.done = done;
		this.dueDate = dueDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String _summary) {
		changes.firePropertyChange(FIELD_SUMMARY, this.summary, this.summary = _summary);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String _description) {
		changes.firePropertyChange(FIELD_DESCRIPTION, this.description, this.description = _description);
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean _done) {
		changes.firePropertyChange(FIELD_DESCRIPTION, this.done, this.done = _done);
	}

	public Date getDueDate() {
		return new Date(dueDate.getTime());
	}

	public void setDueDate(Date _dueDate) {
		changes.firePropertyChange(FIELD_DUEDATE, this.dueDate, this.dueDate = _dueDate);
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", summary=" + summary + ", description=" + description + ", done=" + done + ", dueDate=" + dueDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (done ? 1231 : 1237);
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (done != other.done)
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		}
		else if (!dueDate.equals(other.dueDate))
			return false;
		if (id != other.id)
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		}
		else if (!summary.equals(other.summary))
			return false;
		return true;
	}

	public Todo copy() {
		return new Todo(id, summary, description, done, dueDate);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}

}
