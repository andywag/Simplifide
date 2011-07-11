package com.simplifide.core.scalaext;

import java.util.List;

import com.simplifide.scala2.command.CommandSection;

public interface ScalaProvider {

	public List<CommandSection> getCommands();
}
