package coursera.android101.w5app2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import coursera.app2.sdt.R;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {
	WebView calculator;
	MediaPlayer iloveyou;

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.start:
			if (iloveyou == null) {
				iloveyou = MediaPlayer.create(this, R.raw.iloveyou);
				iloveyou.start();
			}
			return false;
		case R.id.loop:
			if (iloveyou == null)
				return false;
			if (item.isChecked()) {
				item = item.setChecked(false);
				iloveyou.setLooping(false);
			} else {
				item = item.setChecked(true);
				iloveyou.setLooping(true);
			}
			return true;
		case R.id.stop:
			if (iloveyou == null)
				return false;
			iloveyou.stop();
			iloveyou.release();
			iloveyou = null;
			return false;
		case R.id.pause:
			if (iloveyou == null)
				return false;
			if (iloveyou.isPlaying()) {
				iloveyou.pause();
			} else if (!iloveyou.isPlaying()) {
				iloveyou.start();
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		if (iloveyou != null)
			iloveyou.start();
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (iloveyou != null)
			iloveyou.pause();
		super.onPause();
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
