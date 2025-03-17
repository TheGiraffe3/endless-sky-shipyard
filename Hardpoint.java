import java.util.Arrays;
import java.util.List;

public class Hardpoint {
	static int gun_count = 0;
	static int turret_count = 0;
	static int engine_count = 0;
	static int bay_count = 0;
	static int generic_count = 0;
	static ControlPanel controlPanel;

	public String name;
	public String data_string = null;
	public HardpointType type;
	public double x, y;
	public double zoom; //for engines
	public boolean auto_angle; //steering engine
	public double angle; //engine, gun, turret, bay
	public boolean over = false; //engine, gun, bay
	public boolean under = false; //engine, turret, bay
	public boolean parallel = false; //gun
	public boolean have_arc = false; //turret
	public double arc_min, arc_max;	//turret
	public boolean have_turn_mult; //turret
	public double turn_mult;	//turret
	public double gimble;	//engine
	public Facing facing;	//steering engine (l, r), bay (l, r, b)
	public String bay_type = "Fighter"; //bay
	public String launch_effect; //bay
	public int launch_effect_count = 1; //bay

	static public final List<String> hp_types = Arrays.asList(
			"gun",
			"turret",
			"engine",
			"reverse engine",
			"steering engine",
			"bay"
		);
	static private List<String> bay_types = Arrays.asList(
		"Fighter",
		"Drone"
	);
	static private List<String> launch_effects = Arrays.asList(
		"basic launch",
		"human external",
		"human internal",
		"hai launch",
		"remnant external",
		"remnant internal",
		"korath external",
		"korath internal",
		"coalition launch",
		"sheragi launch",
		"scin launch",
		"avgi launch"
	);
	public enum HardpointType {
		GUN,
		TURRET,
		ENGINE,
		REVERSE_ENGINE,
		STEERING_ENGINE,
		BAY
	}
	public enum Facing {
		LEFT,
		RIGHT,
		BACK,
		AUTO,
		NONE
	}

	static List<String> getBayTypes() {
		return bay_types;
	}
	static List<String> getLaunchEffects() {
		return launch_effects;
	}
	static void addBayType(String new_bay) {
		bay_types.add(new_bay);
	}
	static void addLaunchEffect(String new_effect) {
		launch_effects.add(new_effect);
	}
	static boolean removeBayType(String to_remove) {
		if (to_remove == "Fighter" || to_remove == "Drone")
			return false;
		return bay_types.remove(to_remove);
	}
	static boolean removeLaunchEffect(String to_remove) {
		if (to_remove == "basic launch")
			return false;
		return launch_effects.remove(to_remove);
	}
	static String removeBayType(int index) {
		if (index < 2)
			return "";
		return bay_types.remove(index);
	}
	static String removeLaunchEffect(int index) {
		if (index == 0)
			return "";
		return launch_effects.remove(index);
	}

	static void setControlPanel(ControlPanel setcontrolPanel) {
		controlPanel = setcontrolPanel;
	}

	private void copyAngle( Hardpoint other, boolean mirrored) {
		this.angle = other.angle;
		if (mirrored) {
			this.angle = -other.angle;
		}
	
	}
	private void copyGimble( Hardpoint other, boolean mirrored) {
		this.gimble = other.gimble;
		if (mirrored) {
			this.gimble = -other.gimble;
		}
	}

	static Hardpoint createHardpoint(HardpointType type, double x, double y, boolean mirrored) {
		return (createHardpoint(type, x, y, mirrored, "Fighter"));
	}

	static Hardpoint createHardpoint(HardpointType type, double x, double y) {
		return (createHardpoint(type, x, y, false, "Fighter"));
	}

	static Hardpoint createHardpoint(HardpointType type, double x, double y, boolean mirrored, String baytype) {
		Hardpoint hp = new Hardpoint();
		String hp_name = null;

		// hp = (Hardpoint)controlPanel.gun_data.clone();

		switch (type) {
			case GUN:	hp_name = "Gun" + gun_count++;
				hp.data_string = toDataString("gun");
				if (controlPanel != null) {
					hp.copyAngle(controlPanel.getGunData(), mirrored);
					hp.parallel = controlPanel.getGunData().parallel;
					hp.over = controlPanel.getGunData().over;
				}
				break ;
			case TURRET:	hp_name = "Turret" + turret_count++;
				hp.data_string = toDataString("turret");
				if (controlPanel != null) {
					hp.under = controlPanel.getTurretData().under;
					hp.copyAngle(controlPanel.getTurretData(), mirrored);
					hp.have_arc = controlPanel.getTurretData().have_arc;
					hp.arc_min = controlPanel.getTurretData().arc_min;
					hp.arc_max = controlPanel.getTurretData().arc_max;
					if (mirrored) {
						hp.arc_min = -controlPanel.getTurretData().arc_max;
						hp.arc_max = -controlPanel.getTurretData().arc_min;
					}
					hp.turn_mult = controlPanel.getTurretData().turn_mult;
					hp.have_turn_mult = controlPanel.getTurretData().have_turn_mult;
				}
				break ;
			case STEERING_ENGINE: hp_name = "S_Engine" + engine_count++;
			//Was planning to use EngineData for shared engine stuffs but might lead to bug with values being used from other engine if not changed in the current one.
				hp.data_string = toDataString("steering engine");
				if (controlPanel != null) {
					hp.copyAngle(controlPanel.getSteerEngineData(), mirrored);
					hp.facing = controlPanel.getSteerEngineData().facing;
					hp.zoom = controlPanel.getSteerEngineData().zoom;
					hp.copyGimble(controlPanel.getSteerEngineData(), mirrored);
					hp.over = controlPanel.getSteerEngineData().over;
					hp.auto_angle = controlPanel.getSteerEngineData().auto_angle;
				}
				if (hp.auto_angle && hp.facing == Facing.AUTO) {
					if (x < 0 && y < 0) {
						hp.angle = -90;
						hp.facing = Facing.RIGHT;
					}
					else if (x < 0 && y > 0) {
						hp.angle = -90;
						hp.facing = Facing.LEFT;
					}
					else if (x > 0 && y > 0) {
						hp.angle = 90;
						hp.facing = Facing.RIGHT;
					}
					else if (x > 0 && y < 0) {
						hp.angle = 90;
						hp.facing = Facing.LEFT;
					}
				}
				else if (hp.auto_angle) {
					if (x < 0 && y < 0) {
						hp.angle = -90;
					}
					else if (x < 0 && y > 0) {
						hp.angle = -90;
					}
					else if (x > 0 && y > 0) {
						hp.angle = 90;
					}
					else if (x > 0 && y < 0) {
						hp.angle = 90;
					}
				}
				else if (hp.facing == Facing.AUTO) {
					if (x < 0 && y < 0) {
						hp.facing = Facing.RIGHT;
					}
					else if (x < 0 && y > 0) {
						hp.facing = Facing.LEFT;
					}
					else if (x > 0 && y > 0) {
						hp.facing = Facing.RIGHT;
					}
					else if (x > 0 && y < 0) {
						hp.facing = Facing.LEFT;
					}
				}
				break ;
			case REVERSE_ENGINE: 
				// if (hp_name == null) {
					hp_name = "R_Engine" + engine_count++;
				// }
				// if (hp.data_string == null) {
					hp.data_string = toDataString("reverse engine");
				// }
				if (controlPanel != null) {
					hp.copyAngle(controlPanel.getRevEngineData(), mirrored);
					hp.zoom = controlPanel.getRevEngineData().zoom;
					hp.copyGimble(controlPanel.getRevEngineData(), mirrored);
					hp.over = controlPanel.getRevEngineData().over;
				}
				break ;
			case ENGINE: 
				// if (hp_name == null) {
					hp_name = "Engine" + engine_count++;
				// }
				// if (hp.data_string == null) {
					hp.data_string = toDataString("engine");
				// }
				if (controlPanel != null) {
					hp.copyAngle(controlPanel.getEngineData(), mirrored);
					hp.zoom = controlPanel.getEngineData().zoom;
					hp.copyGimble(controlPanel.getEngineData(), mirrored);
					hp.over = controlPanel.getEngineData().over;
				}
				break ;
			case BAY: hp_name = "Bay" + bay_count++;
				hp.data_string = toDataString("bay") + " " + toDataString(baytype);
				if (controlPanel != null) {
					hp.copyAngle(controlPanel.getBayData(), mirrored);
					hp.over = controlPanel.getBayData().over;
					hp.under = controlPanel.getBayData().under;
					hp.bay_type = controlPanel.getBayData().bay_type;
					hp.launch_effect = toDataString(controlPanel.getBayData().launch_effect);
				}
				break ;
			default : hp_name = "Unknown" + generic_count++;
		}
		hp.name = hp_name;
		hp.type = type;
		hp.x = x;
		hp.y = y;
		return hp;
	}

	// if String contains space or tab, quote it.
	static String toDataString(String str) {
		if (str == null) return str;
		if ((str.indexOf(' ') != -1 || str.indexOf('\t') != -1) &&
			!(str.startsWith("\"") && str.endsWith("\""))) {
			str = "\"" + str + "\"";
		}
		return str;
	}

}



