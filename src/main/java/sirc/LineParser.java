package sirc;

import scala.Tuple3;

import java.util.Arrays;
import java.util.ArrayList;

// cribbed from: http://me.opengroove.org/2009/10/single-file-irc-server.html
public class LineParser {

  public static Tuple3<String, String[], String> processLine(String line) throws Exception {
    String prefix = "";
    if (line.startsWith(":")) {
      String[] tokens = line.split(" ", 2);
      prefix = tokens[0];
      line = (tokens.length > 1 ? tokens[1] : "");
    }
    String[] tokens1 = line.split(" ", 2);
    String command = tokens1[0];
    line = tokens1.length > 1 ? tokens1[1] : "";
    String[] tokens2 = line.split("(^| )\\:", 2);
    String trailing = null;
    line = tokens2[0];
    if (tokens2.length > 1)
      trailing = tokens2[1];
    ArrayList<String> argumentList = new ArrayList<String>();
    if (!line.equals(""))
      argumentList.addAll(Arrays.asList(line.split(" ")));
    if (trailing != null)
      argumentList.add(trailing);
    String[] arguments = argumentList.toArray(new String[0]);

    if (command.matches("[0-9][0-9][0-9]"))
      command = "n" + command;

    return new Tuple3<>(command.toUpperCase(), arguments, prefix);
  }
}