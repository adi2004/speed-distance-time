package ro.infloresc.sdt;

import android.widget.EditText;
import android.widget.TextView;

public class Controller {
	private boolean isUpdatingUI;
	MainActivity view;
	Calculator calc = new Calculator();

	Controller(MainActivity view) {
		this.view = view;
		isUpdatingUI = false;
	}

	void updateUI(EditText ignore) {
		// disable triggering the change events
		isUpdatingUI = true;

		// update EditText values
		if (ignore != view.mSpeed) {
			view.mSpeed.setText(calc.getSpeed());
		}
		if (ignore != view.mPace) {
			view.mPace.setText(calc.getPace());
		}
		if (ignore != view.mDistance) {
			view.mDistance.setText(calc.getDistance());
		}
		if (ignore != view.mTime) {
			view.mTime.setText(calc.getTime());
		}

		// update vertical labels
		// first the inputs
		if (calc.firstInput == IO.SPEED || calc.secondInput == IO.SPEED) {
			((TextView) view.findViewById(R.id.ioSpeedLabel)).setText("Input");
			((TextView) view.findViewById(R.id.paceLabel)).setTextAppearance(view, R.style.InputLabel);
			((TextView) view.findViewById(R.id.speedLabel)).setTextAppearance(view, R.style.InputLabel);
		}
		if (calc.firstInput == IO.DISTANCE || calc.secondInput == IO.DISTANCE) {
			((TextView) view.findViewById(R.id.ioDistanceLabel)).setText("Input");
			((TextView) view.findViewById(R.id.distanceLabel)).setTextAppearance(view, R.style.InputLabel);
		}
		if (calc.firstInput == IO.TIME || calc.secondInput == IO.TIME) {
			((TextView) view.findViewById(R.id.ioTimeLabel)).setText("Input");
			((TextView) view.findViewById(R.id.timeLabel)).setTextAppearance(view, R.style.InputLabel);
		}
		// now the outputs
		if (calc.output == IO.SPEED) {
			((TextView) view.findViewById(R.id.ioSpeedLabel)).setText("Output");
			((TextView) view.findViewById(R.id.paceLabel)).setTextAppearance(view, R.style.OutputLabel);
			((TextView) view.findViewById(R.id.speedLabel)).setTextAppearance(view, R.style.OutputLabel);
		} else if (calc.output == IO.DISTANCE) {
			((TextView) view.findViewById(R.id.ioDistanceLabel)).setText("Output");
			((TextView) view.findViewById(R.id.distanceLabel)).setTextAppearance(view, R.style.OutputLabel);
		} else if (calc.output == IO.TIME) {
			((TextView) view.findViewById(R.id.ioTimeLabel)).setText("Output");
			((TextView) view.findViewById(R.id.timeLabel)).setTextAppearance(view, R.style.OutputLabel);
		}

		// enable triggering events
		isUpdatingUI = false;
	}

	public void valueChanged(int input, String string) {
		calc.onChange(input, string);
	}

	public boolean isUpdatingUI() {
		return isUpdatingUI;
	}
}
