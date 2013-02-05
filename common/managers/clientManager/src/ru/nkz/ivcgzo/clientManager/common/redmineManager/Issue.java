package ru.nkz.ivcgzo.clientManager.common.redmineManager;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Issue {
	private int id;
	private Tracker tracker;
	private IssueStatus status;
	private Priority priority;
	private String subject;
	private String description;
	private int doneRatio;
	private Date createdOn;
	private Date updatedOn;
	
	public static Issue fromJsonObject(JSONObject obj) throws JSONException, ParseException {
		JSONObject io;
		Issue iss = new Issue();
		
		iss.setId(obj.getInt("id"));
		io = obj.getJSONObject("tracker");
		iss.setTracker(new Tracker(io.getInt("id"), io.getString("name")));
		io = obj.getJSONObject("status");
		iss.setStatus(new IssueStatus(io.getInt("id"), io.getString("name")));
		io = obj.getJSONObject("priority");
		iss.setPriority(new Priority(io.getInt("id"), io.getString("name")));
		iss.setSubject(obj.getString("subject"));
		iss.setDescription(obj.getString("description"));
		iss.setDoneRatio(obj.getInt("done_ratio"));
		iss.setCreatedOn(RedmineManager.getDateFormatter().parse(obj.getString("created_on")));
		iss.setUpdatedOn(RedmineManager.getDateFormatter().parse(obj.getString("updated_on")));
		
		return iss;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Tracker getTracker() {
		return tracker;
	}

	public void setTracker(Tracker tracker) {
		this.tracker = tracker;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public void setStatus(IssueStatus status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDoneRatio() {
		return doneRatio;
	}

	public void setDoneRatio(int doneRatio) {
		this.doneRatio = doneRatio;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
}
