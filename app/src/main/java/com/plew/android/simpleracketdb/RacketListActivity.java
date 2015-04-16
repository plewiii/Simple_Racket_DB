package com.plew.android.simpleracketdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class RacketListActivity extends ActionBarActivity {

    private static final String TAG = "RacketListActivity";


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
        //Log.d(TAG, "onCreate(): ");
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new RacketListFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }


    // Peter: keep or delete????: @Override
    // Peter: keep or delete????: public boolean onCreateOptionsMenu(Menu menu) {
    // Peter: keep or delete????:     // Inflate the menu; this adds items to the action bar if it is present.
    // Peter: keep or delete????:     getMenuInflater().inflate(R.menu.menu_racket, menu);
    // Peter: keep or delete????:     return true;
    // Peter: keep or delete????: }

    // Peter: keep or delete????: @Override
    // Peter: keep or delete????: public boolean onOptionsItemSelected(MenuItem item) {
    // Peter: keep or delete????:     // Handle action bar item clicks here. The action bar will
    // Peter: keep or delete????:     // automatically handle clicks on the Home/Up button, so long
    // Peter: keep or delete????:     // as you specify a parent activity in AndroidManifest.xml.
    // Peter: keep or delete????:     int id = item.getItemId();

    // Peter: keep or delete????:     //noinspection SimplifiableIfStatement
    // Peter: keep or delete????:     if (id == R.id.action_settings) {
    // Peter: keep or delete????:         return true;
    // Peter: keep or delete????:     }

    // Peter: keep or delete????:     return super.onOptionsItemSelected(item);
    // Peter: keep or delete????: }

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
