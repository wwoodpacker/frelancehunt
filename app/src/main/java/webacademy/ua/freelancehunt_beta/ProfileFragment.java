package webacademy.ua.freelancehunt_beta;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Назар on 25.02.2016.
 */
public class ProfileFragment extends Fragment {
    private static final String IMG = "IMG";
    private static final String LOGIN = "LOGIN";
    private static final String LOCATION = "LOCATION";
    private static final String TYPE_USER = "TYPE_USER";

    public static ProfileFragment newInstance(Profile obj) {

        Bundle args = new Bundle();
        args.putString(IMG, obj.avatar_md);
        args.putString(LOGIN, obj.login);
        args.putString(LOCATION, obj.city_name_ru+" , "+obj.country_name_ru);
        if (obj.is_freelancer) args.putString(TYPE_USER, "Фрилансер");
         else args.putString(TYPE_USER, "Заказчик");

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView img=(ImageView) view.findViewById(R.id.profile_img);;
        Picasso.with(view.getContext()).load(getArguments().getString(IMG)).into(img);

       ((TextView)view.findViewById(R.id.textfrag1)).setText(getArguments().getString(LOGIN));
        ((TextView)view.findViewById(R.id.textfrag2)).setText(getArguments().getString(TYPE_USER));
        ((TextView)view.findViewById(R.id.textfrag3)).setText(getArguments().getString(LOCATION));

        return view;
    }
}
