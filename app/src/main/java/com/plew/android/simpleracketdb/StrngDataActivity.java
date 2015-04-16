package com.plew.android.simpleracketdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class StrngDataActivity extends ActionBarActivity {

    private static final String TAG = "RacketStrngDataActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        // Orig: if (savedInstanceState == null) {
        // Orig:     getSupportFragmentManager().beginTransaction()
        // Orig:             .add(R.id.container, new PlaceholderFragment())
        // Orig:             .commit();
        // Orig: }

        // Peter: code above did not work.  Following from CrimeActivity.java, chapter 8
        Log.d(TAG, "onCreate(): ");
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new StrngDataFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }


    // Implemented in StrngDataFragment: @Override
    // Implemented in StrngDataFragment: public boolean onCreateOptionsMenu(Menu menu) {
    // Implemented in StrngDataFragment:     // Inflate the menu; this adds items to the action bar if it is present.
    // Implemented in StrngDataFragment:     getMenuInflater().inflate(R.menu.menu_racket, menu);
    // Implemented in StrngDataFragment:     return true;
    // Implemented in StrngDataFragment: }

    // Implemented in StrngDataFragment: @Override
    // Implemented in StrngDataFragment: public boolean onOptionsItemSelected(MenuItem item) {
    // Implemented in StrngDataFragment:     // Handle action bar item clicks here. The action bar will
    // Implemented in StrngDataFragment:     // automatically handle clicks on the Home/Up button, so long
    // Implemented in StrngDataFragment:     // as you specify a parent activity in AndroidManifest.xml.
    // Implemented in StrngDataFragment:     int id = item.getItemId();

    // Implemented in StrngDataFragment:     //noinspection SimplifiableIfStatement
    // Implemented in StrngDataFragment:     if (id == R.id.action_settings) {
    // Implemented in StrngDataFragment:         return true;
    // Implemented in StrngDataFragment:     }

    // Implemented in StrngDataFragment:     return super.onOptionsItemSelected(item);
    // Implemented in StrngDataFragment: }

    /**
     * A placeholder fragment containing a simple view.
     */
 /* Orig:
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_racket, container, false);
            return rootView;
        }
    }
 */
}
