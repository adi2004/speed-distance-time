package ro.infloresc.sdt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {
	WebView calculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		calculator = (WebView) findViewById(R.id.webView1);
		calculator.getSettings().setJavaScriptEnabled(true);

		// calculator.setBackground(R.drawable.pracetrack);
		calculator.loadUrl("file:///android_asset/calculator.htm");
		calculator.setBackgroundColor(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && calculator.canGoBack()) {
			calculator.goBack();
			return true;
		}
		// If it wasn't the Back key or there's no web page history, bubble up
		// to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}

	public void loadWR(View v) {
		calculator.loadUrl("file:///android_asset/wr.htm");
	}

	public void showHelp(View v) {
		Builder helpMsg = new AlertDialog.Builder(this);
		helpMsg.setTitle(R.string.help);
		helpMsg.setMessage(R.string.help_message);
		helpMsg.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		helpMsg.show();
	}
}
