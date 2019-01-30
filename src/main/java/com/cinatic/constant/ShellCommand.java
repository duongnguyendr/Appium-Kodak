package com.cinatic.constant;

public class ShellCommand {
	public static String getMelodyStatus = "clients talkback 3";
	public static String stopMelody = "clients talkback 2";
	public static String getRotateStatus = "clients cmd_server 66";
	public static String rotateToLeft = "ftest motor l";
	public static String rotateToRight = "ftest motor r";
	public static String rotateToUp = "ftest motor u";
	public static String rotateToDown = "ftest motor d";
	public static String pairCamera = "dbus-send --system --dest=com.cvisionhk.HubbleParty --type=\"method_call\" /com/cvisionhk/HubbleParty com.cvisionhk.HubbleParty.StartSetup";
	public static String pairCamera0066 = "touch /tmp/pairing_state";
	public static String pairCamera877 = "dbus-send --system --print-reply --dest=com.cvisionhk.setup --type=\"signal\" /com/cvisionhk/setup com.cvisionhk.setup.StartSetup";
}
