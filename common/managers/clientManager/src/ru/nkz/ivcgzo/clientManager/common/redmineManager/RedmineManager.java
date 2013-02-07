package ru.nkz.ivcgzo.clientManager.common.redmineManager;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

public class RedmineManager {
	private Transport conn;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	public RedmineManager(String serverUrl, String apiKey) {
		conn = new Transport(serverUrl, apiKey);
	}
	
	public static SimpleDateFormat getDateFormatter() {
		return RedmineManager.sdf;
	}

	public List<Tracker> getTrackerList() throws Exception {
		JSONObject obj = new JSONObject(conn.get("trackers.json"));
		JSONArray arr = obj.getJSONArray("trackers");
		List<Tracker> lst = new ArrayList<>(arr.length());
		
		for (int i = 0; i < arr.length(); i++) {
			JSONObject o = arr.getJSONObject(i);
			lst.add(new Tracker(o.getInt("id"), o.getString("name")));
		}
		
		return lst;
	}
	
	public List<IssueStatus> getIssueStatusList() throws Exception {
		JSONObject obj = new JSONObject(conn.get("issue_statuses.json"));
		JSONArray arr = obj.getJSONArray("issue_statuses");
		List<IssueStatus> lst = new ArrayList<>(arr.length());
		
		for (int i = 0; i < arr.length(); i++) {
			JSONObject o = arr.getJSONObject(i);
			lst.add(new IssueStatus(o.getInt("id"), o.getString("name")));
		}
		
		return lst;
	}
	
	public List<Issue> getIssues(String projectId) throws JSONException, Exception {
		JSONObject obj = new JSONObject(conn.get(String.format("projects/%s/issues.json", projectId), "author_id", "me", "status_id", "*", "sort", "status,updated_on:desc"));
		JSONArray arr = obj.getJSONArray("issues");
		List<Issue> lst = new ArrayList<>();
		
		for (int i = 0; i < arr.length(); i++)
			lst.add(Issue.fromJsonObject(arr.getJSONObject(i)));
		
		return lst;
	}
	
	public Issue addIssue(String projectId, Tracker tracker, String subject, String description) throws Exception {
		try (StringWriter sw = new StringWriter();) {
			JSONWriter jw = new JSONWriter(sw);
			
			jw.object();
				jw.key("issue");
				jw.object();
					jw.key("project_id");
					jw.value(projectId);
					jw.key("tracker_id");
					jw.value(tracker.getId());
					jw.key("status_id");
					jw.value(1);
					jw.key("priority_id");
					jw.value(4);
					jw.key("subject");
					jw.value(subject);
					jw.key("description");
					jw.value(description);
				jw.endObject();
			jw.endObject();
			
			;
			
			return Issue.fromJsonObject(new JSONObject(conn.post("application/json", sw.toString(), "issues.json")).getJSONObject("issue"));
		}
	}
}
