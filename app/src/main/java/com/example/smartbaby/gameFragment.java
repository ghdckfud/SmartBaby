package com.example.smartbaby;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public gameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gameFragment newInstance(String param1, String param2) {
        gameFragment fragment = new gameFragment();
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
    private ListView gameListView;
    private gameAdapter gameAdapter;
    private List<String> gameList;

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
        TextView gameTitle = (TextView)getView().findViewById(R.id.gameTitle);
        ImageView gameImage = (ImageView)getActivity().findViewById(R.id.gameImage);
        TextView gameText = (TextView)getActivity().findViewById(R.id.gameTitle);
        LinearLayout linearLayout = (LinearLayout)getActivity().findViewById((R.id.notice));
        gameListView = (ListView)getView().findViewById(R.id.gameList);
        gameList = new ArrayList<String>();
        gameList.add("사물 찾기");
        gameList.add("동물 맞추기");
        gameAdapter = new gameAdapter(getContext().getApplicationContext(), gameList ,gameTitle ,linearLayout, gameImage, gameText);
        gameListView.setAdapter(gameAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
