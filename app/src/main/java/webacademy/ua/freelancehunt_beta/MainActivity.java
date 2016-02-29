package webacademy.ua.freelancehunt_beta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ImageView;
import android.widget.Toast;
//android.support.design.widget;

import java.util.concurrent.ExecutionException;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.fragments.NavigationFragment;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.SingleStackNavigationManagerFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String api_token = "3467863492160680";
    public static String api_secret = "e60011a4e7884e111945";
    public static String url= "https://api.freelancehunt.com/profiles/me";
    public static String method = "GET";
    public static String result="";
    public static String jsontext="";
    public static Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_example);
        AnalyticsApp.initialize(this);
        AnalyticsApp.getInstance().get(AnalyticsApp.Target.APP);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.se
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //ImageView imageView =(ImageView)findViewById(R.id.nav_heeader_image);
            if(isOnline(this)) {
                getProfile(MainActivity.this);
                initialNavigationFragmentManager(ProfileFragment.newInstance(profile), profile.fname + " " + profile.sname);
            }
        else Toast.makeText(this,"Отсутствует интернет!",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            if(isOnline(this)) {
                if (profile==null) {getProfile(MainActivity.this);}
                    initialNavigationFragmentManager(ProfileFragment.newInstance(profile), profile.fname + " " + profile.sname);
            }
            else Toast.makeText(this,"Отсутствует интернет!",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_masseges) {
            if(isOnline(this)) {
                if (profile==null) {getProfile(MainActivity.this);}
                initialNavigationFragmentManager(ProfileFragment.newInstance(profile),"Сообщения");
            }
            else Toast.makeText(this,"Отсутствует интернет!",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_project) {
            if(isOnline(this)) {
                if (profile==null) {getProfile(MainActivity.this);}
                initialNavigationFragmentManager(ProfileFragment.newInstance(profile),"Проекты");
            }
            else Toast.makeText(this,"Отсутствует интернет!",Toast.LENGTH_SHORT).show();
            }
        else if (id == R.id.nav_users) {
            if(isOnline(this)) {
                if (profile==null) {getProfile(MainActivity.this);}
                initialNavigationFragmentManager(ProfileFragment.newInstance(profile),"Пользователи");
            }
            else Toast.makeText(this,"Отсутствует интернет!",Toast.LENGTH_SHORT).show();
            }

        else if (id == R.id.nav_exit) {
            if(isOnline(this)) {
                if (profile==null) {getProfile(MainActivity.this);}
                initialNavigationFragmentManager(ProfileFragment.newInstance(profile),"Выход");
            }
            else Toast.makeText(this,"Отсутствует интернет!",Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static void getProfile(MainActivity activity)
    {
        GETTask getTask = new GETTask(activity);
        getTask.execute(api_token, api_secret, url, method);
        try {
            jsontext = getTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        profile = gson.fromJson(jsontext, Profile.class);
    }
    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
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
