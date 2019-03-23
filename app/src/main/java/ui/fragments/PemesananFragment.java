package ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anonymous.cikgood.R;

import ui.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class PemesananFragment extends Fragment {

    public static PemesananFragment newInstance() {
        return new PemesananFragment();
    }

    public PemesananFragment() {
        // Required empty public constructor
    }

    Button btnMeainMaps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pemesanan, container, false);
        btnMeainMaps = view.findViewById(R.id.btn_main_maps);

        btnMeainMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
//                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return view;
    }

}
