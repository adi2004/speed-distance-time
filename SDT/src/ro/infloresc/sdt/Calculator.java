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

	public static void main(String[] args) {
		Calculator c = new Calculator();
		c.print();
		c.onChange(IO.SPEED, 10.5f);
		c.print();
		c.onChange(IO.TIME, 10.5f);
		c.print();
		c.onChange(IO.SPEED, 10.5f);
		c.print();
		c.onChange(IO.DISTANCE, 10.5f);
		c.print();
		c.onChange(IO.TIME, .5f);
		c.print();
	}

	private void print() {
		switch (firstInput) {
		case IO.SPEED:
			System.out.print("Speed: " + speed);
			switch (secondInput) {
			case IO.DISTANCE:
				System.out.println("; Distance: " + distance + "; Time: " + time);
				break;
			case IO.TIME:
				System.out.println("; Time: " + time + "; Distance: " + distance);
				break;
			default:
				System.out.println();
			}
			break;
		case IO.DISTANCE:
			System.out.print("Distance: " + distance);
			switch (secondInput) {
			case IO.SPEED:
				System.out.println("; Speed: " + speed + "; Time: " + time);
				break;
			case IO.TIME:
				System.out.println("; Time: " + time + "; Speed: " + speed);
				break;
			default:
				System.out.println();
			}
			break;

		case IO.TIME:
			System.out.print("Time: " + time);
			switch (secondInput) {
			case IO.SPEED:
				System.out.println("; Speed: " + speed + "; Distance: " + distance);
				break;
			case IO.DISTANCE:
				System.out.println("; Distance: " + distance + "; Speed: " + speed);
				break;
			default:
				System.out.println();
			}
			break;

		case IO.NA:
			System.out.println("No value entered yet;");
			break;
		}
	}

	public void onChange(int input, double nr) {
		if (firstInput != input) {
			secondInput = firstInput;
			firstInput = input;
		}
		switch (input) {
		case IO.SPEED:
			speed = nr;
			pace = 0;
			if (speed != 0)
				pace = 1 / speed;
			if (secondInput == IO.TIME) {
				output = IO.DISTANCE;
				distance = speed * time;
			} else if (secondInput == IO.DISTANCE && speed != 0) {
				output = IO.TIME;
				time = distance / speed;
			}
			break;
		case IO.PACE:
			pace = nr;
			speed = 0;
			if (pace != 0)
				speed = 1 / pace;
			if (secondInput == IO.TIME) {
				output = IO.DISTANCE;
				distance = speed * time;
			} else if (secondInput == IO.DISTANCE && speed != 0) {
				output = IO.TIME;
				time = distance / speed;
			}
			break;
		case IO.DISTANCE:
			distance = nr;
			if (secondInput == IO.SPEED && speed != 0) {
				output = IO.TIME;
				time = distance / speed;
			} else if (secondInput == IO.TIME && time != 0) {
				output = IO.SPEED;
				speed = distance / time;
				pace = 1 / speed;
			}
			break;
		case IO.TIME:
			time = nr;
			if (secondInput == IO.SPEED) {
				output = IO.DISTANCE;
				distance = speed * time;
			} else if (secondInput == IO.DISTANCE && time != 0) {
				output = IO.SPEED;
				speed = distance / time;
				pace = 1 / speed;
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
		double parsedTime = 0;
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
		parsedTime = seconds / 3600.;
		return parsedTime;
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
