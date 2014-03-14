package ro.infloresc.sdt;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Calculator {
	public int firstInput;
	public int secondInput;
	public int output;

	double speed;
	double distance;
	double time;
	double pace;

	public void onChange(int input, double nr) {
		if (firstInput != input) {
			secondInput = firstInput;
			firstInput = (input == IO.PACE)?IO.SPEED:input;
		}
		switch (input) {
		case IO.SPEED:
			speed = nr;
			setPace();
			if (secondInput == IO.TIME) {
				calculateDistance();
			} else if (secondInput == IO.DISTANCE) {
				calculateTime();
			}
			break;
		case IO.PACE:
			pace = nr;
			setSpeed();
			if (secondInput == IO.TIME) {
				calculateDistance();
			} else if (secondInput == IO.DISTANCE) {
				calculateTime();
			}
			break;
		case IO.DISTANCE:
			distance = nr;
			if (secondInput == IO.SPEED) {
				calculateTime();
			} else if (secondInput == IO.TIME) {
				calculateSpeed();
			}
			break;
		case IO.TIME:
			time = nr;
			if (secondInput == IO.SPEED) {
				calculateDistance();
			} else if (secondInput == IO.DISTANCE) {
				calculateSpeed();
			}
			break;
		}
	}

	private void calculateSpeed() {
		output = IO.SPEED;
		speed = (time > 0) ? distance / time : 0;
		setPace();
	}

	private void calculateTime() {
		output = IO.TIME;
		time = (speed > 0) ? distance / speed : 0;
	}

	private void calculateDistance() {
		output = IO.DISTANCE;
		distance = speed * time;
	}

	private void setSpeed() {
		speed = (pace > 0) ? 1 / pace : 0;
	}

	private void setPace() {
		pace = (speed > 0) ? 1 / speed : 0;
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
		double seconds = 0;
		for (int idx = 0; idx < length; idx++) {
			try {
				seconds += Double.parseDouble(pace[idx]) * exp(60, length - idx - 1);
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
