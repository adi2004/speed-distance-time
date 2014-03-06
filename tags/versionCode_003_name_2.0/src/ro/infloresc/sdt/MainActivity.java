package ro.infloresc.sdt;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
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

		// create input filters
		InputFilter filterPositiveReals = new InputFilter() {
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				CharSequence filtered = "";
				for (int i = start; i < end; i++) {
					// 1. all signs are ignored
					// 2. ',' are converted to '.'
					// 3. digits and '.' is used as it is
					if (source.charAt(i) == ',') {
						filtered = filtered + ".";
					} else if (Character.isDigit(source.charAt(i)) || source.charAt(i) == '.') {
						filtered = filtered + (source.charAt(i) + "");
					}
				}
				return filtered;
			}
		};
		InputFilter filterTime = new InputFilter() {
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				CharSequence filtered = "";
				for (int i = start; i < end; i++) {
					// 1. all signs are converted to ':'
					// 2. ',' are converted to '.'
					// 3. digits and '.' is used as it is
					// 4. whitespaces are ignored
					if (source.charAt(i) == ',') {
						filtered = filtered + ".";
					} else if (Character.isDigit(source.charAt(i)) || source.charAt(i) == '.') {
						filtered = filtered + (source.charAt(i) + "");
					} else if (!Character.isWhitespace(source.charAt(i)))
						filtered = filtered + ":";
				}
				return filtered;
			}
		};

		// initialize EditText members
		mSpeed = init(IO.SPEED);
		mSpeed.setFilters(new InputFilter[] { filterPositiveReals });
		mPace = init(IO.PACE);
		mPace.setFilters(new InputFilter[] { filterTime });
		mDistance = init(IO.DISTANCE);
		mDistance.setFilters(new InputFilter[] { filterPositiveReals });
		mTime = init(IO.TIME);
		mTime.setFilters(new InputFilter[] { filterTime });
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
				// InputMethodManager imm = (InputMethodManager)
				// getSystemService(Context.INPUT_METHOD_SERVICE);
				// imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
				// imm.
				controller.valueChanged(input, editText.getText().toString());
				controller.updateUI(editText);
			}
		});
		return editText;
	}

	public void showSoftKeyboard(View view) {
		if (view.requestFocus()) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
	}
}
