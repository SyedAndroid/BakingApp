package com.example.syed.bakingapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.syed.bakingapp.Data.RecipeContract;
import com.example.syed.bakingapp.Data.RecipeProvider;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class stepsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private int position;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public stepsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static stepsFragment newInstance(int columnCount) {
        stepsFragment fragment = new stepsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps_list, container, false);
        String selection = RecipeContract.stepsColumns.RECIPE_ID + "= " + position;
        ArrayList<Step> steps = new ArrayList<>();
        steps.clear();
        String[] args = new String[]{String.valueOf(position)};
        Cursor cursor = getActivity().getContentResolver().query(RecipeProvider.Steps.CONTENT_URI, null, selection, null, null);

        int shortdescIndex = cursor.getColumnIndex(RecipeContract.stepsColumns.SHORT_DESCRIPTION);
        int idIndex = cursor.getColumnIndex(RecipeContract.stepsColumns._ID);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String desc = cursor.getString(shortdescIndex);
            int id = cursor.getInt(idIndex);
            Step step = new Step(id, desc);
            steps.add(step);
            cursor.moveToNext();
        }
        cursor.close();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MystepsRecyclerViewAdapter(steps, mListener));

        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int id);
    }


}
