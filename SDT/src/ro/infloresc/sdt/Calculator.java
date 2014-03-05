package ro.infloresc.sdt;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Calculator {
	private static double ZERO = 0.0001;
	public int firstInput;
	public int secondInput;
	public int output;

	double speed;
	double distance;
	double time;
	double pace;

	public void onChange(int input, double nr) {
		int adjustedInput = input;
		if (input == IO.PACE)
			adjustedInput = IO.SPEED;
		if (firstInput != adjustedInput) {
			secondInput = firstInput;
			firstInput = adjustedInput;
		}
		switch (input) {
		case IO.SPEED:
			speed = nr;
			pace = 0;
			if (speed > 0)
				pace = 1 / speed;
			if (secondInput == IO.TIME) {
				output = IO.DISTANCE;
				distance = speed * time;
			} else if (secondInput == IO.DISTANCE) {
				output = IO.TIME;
				if (speed > 0)
					time = distance / speed;
			}
			break;
		case IO.PACE:
			pace = nr;
			speed = 0;
			if (pace > 0)
				speed = 1 / pace;
			if (secondInput == IO.TIME) {
				output = IO.DISTANCE;
				distance = speed * time;
			} else if (secondInput == IO.DISTANCE) {
				output = IO.TIME;
				if (speed > 0)
					time = distance / speed;
			}
			break;
		case IO.DISTANCE:
			distance = nr;
			if (secondInput == IO.SPEED) {
				output = IO.TIME;
				if (speed > 0)
					time = distance / speed;
			} else if (secondInput == IO.TIME) {
				output = IO.SPEED;
				if (time > 0) {
					speed = distance / time;
					if (speed > 0)
						pace = 1 / speed;
				}
			}
			break;
		case IO.TIME:
			time = nr;
			if (secondInput == IO.SPEED) {
				output = IO.DISTANCE;
				distance = speed * time;
			} else if (secondInput == IO.DISTANCE) {
				output = IO.SPEED;
				if (time > 0) {
					speed = distance / time;
					if (speed > 0)
						pace = 1 / speed;
				}
			}
			break;
		}
	}

	public void onChange(int input, String stringNr) {
		double nr = 0;
		switch (input) {
		case IO.SPEED:
			nr = parseNumber(stringNr);
			break;
		case IO.PACE:
			nr = parseTime(stringNr);
			break;
		case IO.DISTANCE:
			nr = parseNumber(stringNr);
			break;
		case IO.TIME:
			nr = parseTime(stringNr);
			break;
		}
		onChange(input, nr);
	}

	private float parseNumber(String stringNr) {
		float nr = 0;
		try {
			nr = Float.parseFloat(stringNr);
		} catch (Exception e) {
		}
		return nr;
	}

	private double parseTime(String stringNr) {
		String regularExpression = ":";
		String[] pace = stringNr.split(regularExpression);
		int length = pace.length;
		if (length > 3)
			return 0;
		int seconds = 0;
		for (int idx = 0; idx < length; idx++) {
			try {
				seconds += Integer.parseInt(pace[idx]) * exp(60, length - idx - 1);
			} catch (Exception e) {
			}
		}
		return seconds / 3600.;
	}

	private int exp(int base, int exp) {
		if (exp == 0)
			return 1;
		return base * exp(base, exp - 1);
	}

	public CharSequence getSpeed() {
		if (speed > 0)
			return String.format("%.2f", speed);
		return "";
	}

	public CharSequence getPace() {
		if (pace > 0) {
			String paceString = setTimeString(0, (int) (pace * 60), (int) (pace * 3600) % 60);
			return paceString;
		}
		return "";
	}

	public CharSequence getDistance() {
		if (distance > 0)
			return String.format("%.2f", distance);
		return "";
	}

	public CharSequence getTime() {
		if (time > 0) {
			String timeString = setTimeString((int) time, (int) (time * 60) % 60, (int) (time * 3600) % 60);
			return timeString;
		}
		return "";
	}

	public String setTimeString(int hours, int minutes, int seconds) {
		String hour = "" + hours;
		String min = "" + minutes;
		String sec = "" + seconds;

		if (hours < 10)
			hour = "0" + hours;
		if (minutes < 10)
			min = "0" + minutes;
		if (seconds < 10)
			sec = "0" + seconds;

		if (hours == 0) {
			if (minutes == 0)
				return "00:" + sec;
			return min + ":" + sec;
		}

		return hour + ":" + min + ":" + sec;
	}
}
