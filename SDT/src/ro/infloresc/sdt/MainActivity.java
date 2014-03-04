package ro.infloresc.sdt;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
	// private static final String TAG = "SDT";
	public static boolean DISABLE_CHANGE_EVENT = false;
	public EditText mDistance, mPace, mSpeed, mTime;
	public Controller controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		controller = new Controller(this);

		mSpeed = (EditText) findViewById(R.id.editSpeed);
		mSpeed.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (controller.isUpdatingUI()) return;
				controller.valueChanged(IO.SPEED, mSpeed.getText().toString());
				controller.updateUI(mSpeed);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mPace = (EditText) findViewById(R.id.editPace);
		mPace.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (controller.isUpdatingUI()) return;
				controller.valueChanged(IO.PACE, mPace.getText().toString());
				controller.updateUI(mPace);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mDistance = (EditText) findViewById(R.id.editDistance);
		mDistance.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (controller.isUpdatingUI()) return;
				controller.valueChanged(IO.DISTANCE, mDistance.getText().toString());
				controller.updateUI(mDistance);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mTime = (EditText) findViewById(R.id.editTime);
		mTime.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (controller.isUpdatingUI()) return;
				controller.valueChanged(IO.TIME, mTime.getText().toString());
				controller.updateUI(mTime);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
}
