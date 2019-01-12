package com.test.controllers;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.entities.UserState;
import com.test.services.RecentURLService;
import com.test.utils.JsonUtils;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class InitTestController {
	@Autowired
	RecentURLService urlService;
	JsonUtils jUtil = new JsonUtils();

	@GetMapping("/{id}/recentURL")
	public ResponseEntity<?> getRecentURL(@PathVariable int id) throws JsonProcessingException {
		List<UserState> uState = urlService.getUrls(id);
		if (uState == null || uState.size() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(uState, HttpStatus.OK);
		}
	}

	@PostMapping("/recentURL")
	public ResponseEntity<?> saveRecentURLs(@RequestBody UserState u) throws JsonProcessingException {
		if (urlService.setURL(u) != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/latest")
	public ResponseEntity<?> getLatest() throws JSONException, IOException {
		JSONObject jObject = jUtil.readJsonFromUrl("http://api.pathofexile.com/public-stash-tabs");
		JSONArray jArray = jObject.getJSONArray("stashes");
		JSONObject temp;
		for (int i = 0; i < jArray.length(); i++) {
			temp = jArray.getJSONObject(i);
			if(null !=temp.get("accountName") && !"null".equals(temp.get("accountName").toString()))
				System.out.println(temp.toString(2));
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
