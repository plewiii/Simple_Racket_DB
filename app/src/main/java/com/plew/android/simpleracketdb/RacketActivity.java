package com.plew.android.simpleracketdb;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;


public class RacketActivity extends ActionBarActivity {

    private static final String TAG = "RacketActivity";


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
            fragment = new RacketFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }


    // Implemented in RacketFragment: @Override
    // Implemented in RacketFragment: public boolean onCreateOptionsMenu(Menu menu) {
    // Implemented in RacketFragment:     // Inflate the menu; this adds items to the action bar if it is present.
    // Implemented in RacketFragment:     getMenuInflater().inflate(R.menu.menu_racket, menu);
    // Implemented in RacketFragment:     return true;
    // Implemented in RacketFragment: }

    // Implemented in RacketFragment: @Override
    // Implemented in RacketFragment: public boolean onOptionsItemSelected(MenuItem item) {
    // Implemented in RacketFragment:     // Handle action bar item clicks here. The action bar will
    // Implemented in RacketFragment:     // automatically handle clicks on the Home/Up button, so long
    // Implemented in RacketFragment:     // as you specify a parent activity in AndroidManifest.xml.
    // Implemented in RacketFragment:     int id = item.getItemId();

    // Implemented in RacketFragment:     //noinspection SimplifiableIfStatement
    // Implemented in RacketFragment:     if (id == R.id.action_settings) {
    // Implemented in RacketFragment:         return true;
    // Implemented in RacketFragment:     }

    // Implemented in RacketFragment:     return super.onOptionsItemSelected(item);
    // Implemented in RacketFragment: }

    // not needed: @Override
    //protected Fragment createFragment() {
    protected Fragment createFragment() {
        //Log.d(TAG, "createFragment(): ");
        UUID racketId = (UUID)getIntent()
                .getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);
        return RacketFragment.newInstance(racketId);
    }

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
