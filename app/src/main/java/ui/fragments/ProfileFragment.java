package ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.InsertMapsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ui.activities.LoginActivity;
import ui.activities.MainActivity;


/**
 * A simple {@link } subclass.
 */
public class ProfileFragment extends Fragment {

    private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    //get firebase auth instance
    //auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        toolbar.setTitle("Profile");
//        toolbar.getResources().setTitleTextColor(R.color.colorAccent);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        // click button back pada title bar
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        signOut = (Button) view.findViewById(R.id.sign_out);
//
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    startActivity(new Intent(getActivity(), MainActivity.class));
//
//            }
//        });

//        authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    // user auth state is changed - user is null
//                    // launch login activity
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//            }
//        };

//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logout();
//            }
//        });

        return view;

    }

    private void Logout() {
        auth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

//    public void openSignOut(View view) {
//        auth.signOut();
//        Intent i = new Intent(getActivity(), LoginActivityBackUp3.class);
//        startActivity(i);
//        //startActivity(new Intent(this, LoginActivityBackUp3.class));
//
//    }


}
