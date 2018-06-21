package com.sogeti.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.google.gson.Gson;
import com.sogeti.adapter.MessageAdapter;
import com.sogeti.command.Command;

@CrossOrigin(origins = "*", maxAge = 3600)
public class PrimaryController {

	protected MessageAdapter client = MessageAdapter.getInstance();
	protected Gson gson = new Gson();
	protected String stringifiedCommand;
	protected Command command;
	Map<String,String> values;
}
