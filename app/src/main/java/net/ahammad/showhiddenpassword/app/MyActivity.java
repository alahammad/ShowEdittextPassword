package net.ahammad.showhiddenpassword.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.ahammad.showhiddenpassword.ShownEdittext;
import net.ahammad.showhiddenpassword.ShownEdittext.OnPasswordDisplayListener;


public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final ShownEdittext view = (ShownEdittext) findViewById(R.id.view);
        final ShownEdittext child = (ShownEdittext) findViewById(R.id.child_view);
        view.setOnPasswordDisplayListener(new OnPasswordDisplayListener() {
            public void onPasswordShow() {
                child.showPassword();
            }

            public void onPasswordHide() {
                child.hidePassword();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
