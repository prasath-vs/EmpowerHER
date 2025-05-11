package com.example.javabiometric;

import static android.content.Context.VIBRATOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOne extends Fragment {

    private Button Start, Stop;
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOne newInstance(String param1, String param2) {
        FragmentOne fragment = new FragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Start = (Button) view.findViewById(R.id.button_start);
        Stop = (Button) view.findViewById(R.id.button_stop);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.alert);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!vibrator.hasVibrator()) {
                    return; //Check if device has vibrator hardware or not, if not then return from this method
                    //don't execute futher code below
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    //if API = 26(Oreo) or higher
                    vibrator.vibrate(
                            VibrationEffect.createOneShot(1000000,VibrationEffect.DEFAULT_AMPLITUDE)
                    );

                } else {
                    //vibrate for 1 second
                    vibrator.vibrate(1000000);

                    //Vibration Pattern - you can create yours
//                    long[] pattern = {0, 200, 10, 500};
//                    vibrator.vibrate(pattern, 1);
                }

                mediaPlayer.setLooping(true);
                mediaPlayer.start();


            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To stop the vibartion
                vibrator.cancel();
                mediaPlayer.pause();
            }
        });
    }

    private Object getSystemService(String vibratorService) {
        return null;
    }
}