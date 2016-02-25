package webacademy.ua.freelancehunt_beta;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
//android.support.design.widget;

import java.util.concurrent.ExecutionException;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.fragments.NavigationFragment;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.SingleStackNavigationManagerFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String api_token = "3467863492160680";
    public static String api_secret = "e60011a4e7884e111945";
    public static String url= "https://api.freelancehunt.com/profiles/me";
    public static String method = "GET";
    public static String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Gallery"), "Gallery1");
    }
   /* public void click(View v){

        switch (v.getId()){
            case R.id.btn_get:
                GETTask getTask=new GETTask(MainActivity.this);
                getTask.execute(api_token, api_secret, url, method);
                try {
                    result=getTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                break;
        }

    }
*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Gallery"), "Gallery1");
        }
        else if (id == R.id.nav_gallery) {
            initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Gallery"), "Gallery2");
        }
        else if (id == R.id.nav_slideshow) {
            initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Gallery"), "Gallery3");        }
        else if (id == R.id.nav_manage) {
            initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Manage"), "Gallery4");
        }
        else if (id == R.id.nav_manage) {
            //initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Manage"), "Gallery4");
            //android.os.Process.killProcess(android.os.Process.myPid());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void initialNavigationFragmentManager(Fragment firstFragment, @Nullable String title) {
        if (title != null) {
            setTitle(title);
        }
        if (firstFragment instanceof NavigationFragment) {
            SingleStackNavigationManagerFragment navManager = SingleStackNavigationManagerFragment.newInstance((INavigationFragment)firstFragment);
            setFragment(navManager, title, mVisibleFragment, false);
        }
        else if (firstFragment instanceof Fragment) {
            setFragment(firstFragment, title, mVisibleFragment, false);
        }
    }

    private Fragment mVisibleFragment;
    public void setFragment(Fragment fragment, String title, @Nullable Fragment oldFragment, boolean closeDrawer) {
        mVisibleFragment = fragment;
        setTitle(title);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (oldFragment != null && oldFragment != fragment)
            ft.remove(oldFragment);

        ft.replace(targetHomeFrame(), fragment).commit();
    }
    private int targetHomeFrame() {
        return R.id.frag_container;
    }
}
