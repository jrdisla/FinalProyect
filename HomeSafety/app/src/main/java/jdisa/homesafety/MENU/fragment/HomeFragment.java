package jdisa.homesafety.Menu.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jdisa.homesafety.Data_Form.Data;
import jdisa.homesafety.Data_Form.ImageUpload;
import jdisa.homesafety.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference Data;
    private DatabaseReference Data2;
    private FirebaseAuth auth;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
 private ImageUpload fire = new ImageUpload();
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final String valor = getActivity().getIntent().getStringExtra("getData");
        final View myInflatedView = inflater.inflate(R.layout.fragment_home, container,false);
        auth = FirebaseAuth.getInstance();
        Data2 = FirebaseDatabase.getInstance().getReference("Data");
        Data2.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<jdisa.homesafety.Data_Form.Data> datas = new ArrayList<Data>();


                for (DataSnapshot snap: dataSnapshot.child(valor).getChildren()
                        ) {

                    Data data = new Data();
                    data = snap.getValue(Data.class);
                    if(data != null) {
                        datas.add(data);
                    }
                }
                Long co2= 0L;
                Long hume= 0L;
                Long pg= 0L ;
                Long temp= 0L ;
                for (Data item : datas)
                {
                    co2 = item.getCo2() ;
                    hume = item.getHume();
                    pg = item.getPg();
                    temp = item.getTemp();

                    TextView tempe =  (TextView) myInflatedView.findViewById(R.id.temp2);
                    TextView hum = (TextView) myInflatedView.findViewById(R.id.hum2);
                    TextView gas = (TextView) myInflatedView.findViewById(R.id.gas2);
                    TextView co2e = (TextView) myInflatedView.findViewById(R.id.co22);


                    SpannableString miTexto = new SpannableString("   Co2: "+ String.valueOf(co2)+" ppm");
                    StyleSpan boldSpan1 = new StyleSpan(Typeface.BOLD);
                    miTexto.setSpan(boldSpan1, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    SpannableString miTexto2 = new SpannableString("   Temp: " + String.valueOf(temp) + " °C");
                    StyleSpan boldSpan12 = new StyleSpan(Typeface.BOLD);
                    miTexto2.setSpan(boldSpan12, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);


                    SpannableString miTexto3 = new SpannableString("   Humedad: " + String.valueOf(hume) + "%");
                    StyleSpan boldSpan13 = new StyleSpan(Typeface.BOLD);
                    miTexto3.setSpan(boldSpan13, 0, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);


                    SpannableString miTexto4 = new SpannableString("   Gas: " + String.valueOf(pg) + " ppm");
                    StyleSpan boldSpan14 = new StyleSpan(Typeface.BOLD);
                    miTexto4.setSpan(boldSpan14, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    co2e.setText(miTexto);
                    tempe.setText(miTexto2);
                    hum.setText(miTexto3);
                    gas.setText(miTexto4);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Data = FirebaseDatabase.getInstance().getReference("image2");
        Data.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {
                    ImageUpload img = snapshot.getValue(ImageUpload.class);

                    if(valor.equalsIgnoreCase(img.getDevice())) {
                        String name = img.getName();
                        String utl = img.getUrl();
                        fire.setName(name);
                        fire.setUrl(utl);
                        fire.setDevice(img.getDevice());

                        TextView tvName = (TextView) myInflatedView.findViewById(R.id.tvImageName2);
                        ImageView img2 = (ImageView) myInflatedView.findViewById(R.id.imgView2);
                        tvName.setText("  " + fire.getName());
                        Glide.with(getActivity()).load(fire.getUrl()).into(img2);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return myInflatedView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
