package ro.infloresc.sdt;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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

		mSpeed = init(IO.SPEED);
		mPace = init(IO.PACE);
		mDistance = init(IO.DISTANCE);
		mTime = init(IO.TIME);
	}

	private EditText init(final int input) {
		EditText editTextInput = null;
		switch (input) {
		case IO.SPEED:
			editTextInput = (EditText) findViewById(R.id.editSpeed);
			break;
		case IO.PACE:
			editTextInput = (EditText) findViewById(R.id.editPace);
			break;
		case IO.DISTANCE:
			editTextInput = (EditText) findViewById(R.id.editDistance);
			break;
		case IO.TIME:
			editTextInput = (EditText) findViewById(R.id.editTime);
			break;
		}
		final EditText editText = editTextInput;
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (controller.isUpdatingUI())
					return;
				controller.valueChanged(input, editText.getText().toString());
				controller.updateUI(editText);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (controller.isUpdatingUI())
					return;
				controller.valueChanged(input, editText.getText().toString());
				controller.updateUI(editText);
			}
		});
		return editText;
	}
}
