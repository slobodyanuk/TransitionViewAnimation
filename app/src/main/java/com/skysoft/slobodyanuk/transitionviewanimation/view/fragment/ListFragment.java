package com.skysoft.slobodyanuk.transitionviewanimation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skysoft.slobodyanuk.transitionviewanimation.R;
import com.skysoft.slobodyanuk.transitionviewanimation.util.RecyclerManager;
import com.skysoft.slobodyanuk.transitionviewanimation.view.adapter.ListAdapter;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class ListFragment extends Fragment implements ListAdapter.ItemListener {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerManager manager = new RecyclerManager(getActivity());
        ListAdapter adapter = new ListAdapter(manager, 11, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null, false,false));
        mRecyclerView.setLayoutManager(manager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
