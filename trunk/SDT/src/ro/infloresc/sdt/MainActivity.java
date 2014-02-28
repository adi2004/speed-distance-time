package ro.infloresc.sdt;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
	private static final String TAG = "SDT";
	public static boolean DISABLE_CHANGE_EVENT = false;
	public EditText mDistance, mPace, mSpeed, mTime;

	enum io {
		SPEED, DISTANCE, TIME
	};

	io[] orderOfInput;
	int inputQueue[] = { 0, 0, 0 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// in = new

		orderOfInput = new io[3];

		mSpeed = (EditText) findViewById(R.id.editSpeed);
		mSpeed.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				//speed
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					if (DISABLE_CHANGE_EVENT) {
						DISABLE_CHANGE_EVENT = false;
						return;
					}
					DISABLE_CHANGE_EVENT = true;
					float speed = Float.parseFloat(mSpeed.getText().toString());
					String paceString = setTimeString(0, (int) (1 / speed * 60), (int) (1 / speed * 3600) % 60);
					mPace.setText(paceString);
				} catch (Exception e) {
					mPace.setText("");
					Log.e(TAG, "Error processing number");
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mPace = (EditText) findViewById(R.id.editPace);
		mPace.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if (setInputSuccess(1))
					updateInterface();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (DISABLE_CHANGE_EVENT) {
					DISABLE_CHANGE_EVENT = false;
					return;
				}
				DISABLE_CHANGE_EVENT = true;
				String regularExpression = ":";
				String[] pace = mPace.getText().toString().split(regularExpression);
				if (pace.length > 1) {
					double speed;
					try {
						speed = 1 / (Integer.parseInt(pace[0]) / 60. + Integer.parseInt(pace[1]) / 3600.);
						mSpeed.setText(String.format("%.2f", speed));
					} catch (Exception e) {
						Log.e(TAG, "Error processing pace");
						e.printStackTrace();
					}
				} else {
					mSpeed.setText("");
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mDistance = (EditText) findViewById(R.id.editDistance);
		mDistance.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if (setInputSuccess(2))
					updateInterface();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mTime = (EditText) findViewById(R.id.editTime);
		mTime.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if (setInputSuccess(3))
					updateInterface();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	protected boolean setInputSuccess(int input) {
		if (inputQueue[2] != input) {
			// if the input queue is full
			if (inputQueue[2] != 0) {
				// shift the queue and add the new element
				inputQueue[0] = inputQueue[1];
				inputQueue[1] = inputQueue[2];
				inputQueue[2] = input;
				return true;
			}

			// the input queue is not full so add the element on the last place
			if (inputQueue[0] == 0)
				inputQueue[0] = input;
			else if (inputQueue[1] == 0) {
				inputQueue[1] = inputQueue[0];
				inputQueue[2] = input;
				if (1 != inputQueue[1] && 1 != inputQueue[2])
					inputQueue[0] = 1;
				else if (2 != inputQueue[1] && 2 != inputQueue[2])
					inputQueue[0] = 2;
				else if (3 != inputQueue[1] && 3 != inputQueue[2])
					inputQueue[0] = 3;
			}
			return true;
		}
		// the queue is already full and the last element is our input
		return false;
	}

	protected void updateInterface() {
		// check the speed
		updateElement(1, R.id.ioSpeedLabel);
		// check the distance
		updateElement(2, R.id.ioDistanceLabel);
		// check the time
		updateElement(3, R.id.ioTimeLabel);
	}

	void updateElement(int value, int id) {
		if (inputQueue[1] == value || inputQueue[2] == value || (inputQueue[0] == value && inputQueue[1] == 0)
				|| (inputQueue[1] == value && inputQueue[2] == 0))
			((TextView) findViewById(id)).setText("Input");
		else if (inputQueue[0] == value && inputQueue[1] != 0)
			((TextView) findViewById(id)).setText("Output");
	}

	protected boolean isOutputChanged(io speed) {
		// TODO Auto-generated method stub
		return false;
	}

	private void calculate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void help(View v) {
		Builder helpMsg = new AlertDialog.Builder(this);
		helpMsg.setTitle(R.string.help);
		helpMsg.setMessage(R.string.help_message);
		helpMsg.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		helpMsg.show();
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

		if (hours == 0)
			return min + ":" + sec;
		return hour + ":" + min + ":" + sec;
	}

	// DialogFragment used to pick a ToDoItem deadline time
	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return
			return new TimePickerDialog(getActivity(), this, hour, minute, true);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// setTimeString(hourOfDay, minute, 0);

			// timeView.setText(timeString);
		}
	}

	class Stack {
		private final int MAX_ELEMS = 3;
		io stack[];

		Stack() {
			stack = new io[MAX_ELEMS];
		}

		// returns false if the push was unsuccessful (the element already
		// exists)
		boolean push(io elem) {
			for (int index = 0; index < stack.length; index++) {
				if (stack[index] == elem)
					return false;
			}
			stack[stack.length] = elem;
			return true;
		}
	}
}
